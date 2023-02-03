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
import social.tangent.mobile.data.extensions.threaded
import social.tangent.mobile.data.extensions.toList
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
        override fun process(list: List<StatusContent>) = list.asReversed().threaded(status)
    }

    operator fun invoke(): String = id
    open fun process(list: List<StatusContent>) = list
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
        .map { Timeline(it.executeAsList().let {
            id.process(it)
        }) }
        .stateIn(scope, SharingStarted.Eagerly, Timeline(listOf()))

    private suspend fun getStatusById(id: String): Status {
        return mastodon.timeline.fetchById(id)
    }

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

    private fun insert(statuses: List<Status>, clearLoadMore: String? = null) {
        if (statuses.isEmpty()) return
        val needsPlaceholder = !hasSeenStatus(statuses.last().id)
        db.transaction {
            statuses.forEachIndexed { index, it ->
                val loadMore = id.canLoadMore && index == statuses.lastIndex && needsPlaceholder
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
        fun create(
            timelineId: TimelineId,
            mastodon: Mastodon,
            scope: CoroutineScope
        ): TimelineStorage {
            val db = get<DbFactory>()[mastodon.id]
            return TimelineStorage(timelineId, db, mastodon, scope)
        }
    }
}