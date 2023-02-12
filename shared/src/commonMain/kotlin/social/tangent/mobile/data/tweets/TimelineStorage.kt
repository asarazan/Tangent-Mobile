package social.tangent.mobile.data.tweets

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.DbFactory
import social.tangent.mobile.data.extensions.toContent
import social.tangent.mobile.data.extensions.toList
import social.tangent.mobile.data.ng.repos.CompositeRepo
import social.tangent.mobile.data.ng.repos.PostRepo
import social.tangent.mobile.sdk.Mastodon

sealed class TimelineId(
    val id: String,
    val canLoadMore: Boolean = true
) {
    object HomeTimelineId : TimelineId("home")

    class AccountTimelineId(val account: String) : TimelineId("account:$account")

    class ThreadTimelineId(val status: Status) : TimelineId(
        "thread:$status",
        false
    ) {
        override fun process(list: List<Status>) = list.asReversed()//.threaded(status)
    }

    operator fun invoke(): String = id
    open fun process(list: List<Status>) = list
}

class TimelineStorage(
    private val id: TimelineId,
    private val storage: PostRepo,
    private val mastodon: Mastodon,
) {
    val timeline
        get() = storage.posts.map {
            Timeline(it.map(Status::toContent))
        }

    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    val api get() = mastodon.api

    private val _isLoading = MutableStateFlow(false)

    suspend fun fetchFrom(from: Status) {
        val timeline = when (id) {
            TimelineId.HomeTimelineId -> mastodon.timeline.fetchFrom(from.id)
            is TimelineId.AccountTimelineId -> mastodon.accounts.fetchFrom(id.account, from.id)
            is TimelineId.ThreadTimelineId -> mastodon.timeline.fetchThread(id.status.id).toList(id.status)
        }
        insert(timeline, from.id)
    }

    suspend fun fetch() {
        if (_isLoading.value) return
        _isLoading.emit(true)
        val timeline = when (id) {
            TimelineId.HomeTimelineId -> mastodon.timeline.fetchFrom()
            is TimelineId.AccountTimelineId -> mastodon.accounts.fetchFrom(id.account)
            is TimelineId.ThreadTimelineId -> mastodon.timeline.fetchThread(id.status.id).toList(id.status)
        }
        insert(timeline, null)
        _isLoading.emit(false)
    }

    suspend fun fave(status: Status, faved: Boolean): Status {
        val inc = if (faved) 1 else -1
        val preview = status.copy(
            favourited = faved,
            favouritesCount = status.favouritesCount + inc
        )
        updateWithReblogs(preview)
        mastodon.statuses.fave(status.id, faved)
        // Refetch because the server is stupid and doesn't properly update the count.
        return updateWithReblogs(mastodon.timeline.fetchById(status.id))
    }

    suspend fun reblog(status: Status, reblogged: Boolean): Status {
        val inc = if (reblogged) 1 else -1
        val preview = status.copy(
            reblogged = reblogged,
            reblogsCount = status.reblogsCount + inc
        )
        updateWithReblogs(preview)
        mastodon.statuses.reblog(status.id, reblogged)
        // Refetch because the server is stupid and doesn't properly update the count.
        return updateWithReblogs(mastodon.timeline.fetchById(status.id))
    }

    private fun updateWithReblogs(status: Status): Status {
        val lookup = storage.reblogsOf(status.id)
        val updated = lookup.map {
            it.copy(reblog = status)
        } + status
        storage.insert(updated)
        return status
    }

    private fun insert(statuses: List<Status>, clearLoadMore: String? = null) {
        storage.insert(statuses)
        // clearLoadMore?.let { storage.clearLoadMore(it) }
    }

    companion object : KoinComponent {
        fun create(
            timelineId: TimelineId,
            mastodon: Mastodon,
            scope: CoroutineScope
        ): TimelineStorage {
            val db = get<DbFactory>()[mastodon.id]
            // val storage = DbRepo(timelineId, db, scope)
            // val storage = MemoryRepo(timelineId, scope)
            val storage = CompositeRepo(timelineId, db, scope)
            return TimelineStorage(timelineId, storage, mastodon)
        }
    }
}