package social.tangent.mobile.data.tweets

import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import social.tangent.mobile.TangentDatabase
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.DbFactory
import social.tangent.mobile.data.tweets.TimelineId.HomeTimeline
import social.tangent.mobile.data.tweets.TimelineId.HomeTimeline.id
import social.tangent.mobile.sdk.Mastodon

sealed class TimelineId(val id: String) {
    object HomeTimeline : TimelineId("home")
    class AccountTimeline(val account: String) : TimelineId("account:$id")
    // class ThreadTimeline(val status: String) : TimelineKind("thread:$id")

    operator fun invoke(): String = id
}

class TimelineStorage(
    val id: TimelineId,
    val db: TangentDatabase,
    val mastodon: Mastodon,
    val scope: CoroutineScope
) {
    val timeline: StateFlow<Timeline>
        get() = raw

    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    val api get() = mastodon.api

    private val _isLoading = MutableStateFlow(false)

    private val raw = db.timelineQueries.getTimeline(id(), ::timelineMapper)
        .asFlow()
        .map { Timeline(it.executeAsList()) }
        .stateIn(scope, SharingStarted.Eagerly, Timeline(listOf()))

    suspend fun getStatusById(id: String): Status {
        return mastodon.timeline.fetchById(id)
    }

    suspend fun fetchFrom(from: Status) {
        val timeline = mastodon.timeline.fetchFrom(fromId = from.id)
        insert(timeline, from.id)
    }

    suspend fun fetch() {
        if (_isLoading.value) return
        _isLoading.emit(true)
        val timeline = mastodon.timeline.fetchFrom()
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
        if (faved) api.favourite(mastodon.bearer(), status.id) else api.unfavourite(mastodon.bearer(), status.id)
        // Refetch because the server is stupid and doesn't properly update the count.
        return updateWithReblogs(getStatusById(status.id))
    }

    suspend fun reblog(status: Status, reblogged: Boolean): Status {
        val inc = if (reblogged) 1 else -1
        val preview = status.copy(
            reblogged = reblogged,
            reblogsCount = status.reblogsCount + inc
        )
        updateWithReblogs(preview)
        if (reblogged) api.reblog(mastodon.bearer(), status.id) else api.unreblog(mastodon.bearer(), status.id)
        // Refetch because the server is stupid and doesn't properly update the count.
        return updateWithReblogs(getStatusById(status.id))
    }

    private fun updateWithReblogs(status: Status): Status {
        val lookup = db.timelineQueries.lookupReblogsOf(status.id)
        val list = lookup.executeAsList()
        insert(list.map {
            it.copy(reblog = status)
        } + status)
        return status
    }

    private fun insert(statuses: List<Status>, clearLoadMore: String? = null) {
        if (statuses.isEmpty()) return
        val needsPlaceholder = !hasSeenStatus(statuses.last().id)
        db.transaction {
            statuses.forEachIndexed { index, it ->
                val loadMore = index == statuses.lastIndex && needsPlaceholder
                db.timelineQueries.insert(
                    it.id,
                    it,
                    it.account.id,
                    Json.encodeToString(it.createdAt),
                    it.reblog?.id
                )
                db.timelineQueries.addToTimeline(id(), it.id, loadMore)
            }
            clearLoadMore?.let { clearLoadMore(it) }
        }
    }

    private fun hasSeenStatus(status: String): Boolean {
        return db.timelineQueries.checkExists(id(), status).executeAsOneOrNull() != null
    }

    private fun clearLoadMore(status: String) {
        db.timelineQueries.clearLoadMore(id(), status)
    }

    companion object : KoinComponent {
        fun create(mastodon: Mastodon, scope: CoroutineScope): TimelineStorage {
            val db = get<DbFactory>()[mastodon.id]
            return TimelineStorage(HomeTimeline, db, mastodon, scope)
        }
    }
}