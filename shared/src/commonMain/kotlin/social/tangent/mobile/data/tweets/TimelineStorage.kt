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
import social.tangent.mobile.data.createDatabase
import social.tangent.mobile.sdk.Mastodon

class TimelineStorage(
    val id: String,
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

    private val raw = db.statusQueries.selectAll(::timelineMapper)
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
        val lookup = db.statusQueries.lookupReblogsOf(status.id)
        val list = lookup.executeAsList()
        insert(list.map {
            it.copy(reblog = status)
        } + status)
        return status
    }

    private fun insert(statuses: List<Status>, clearLoadMore: String? = null) {
        if (statuses.isEmpty()) return
        val needsPlaceholder = !hasSeenId(statuses.last().id)
        db.transaction {
            statuses.forEachIndexed {
                    index, it ->
                db.statusQueries.insert(
                    it.id,
                    it,
                    it.account.id,
                    Json.encodeToString(it.createdAt),
                    it.reblog?.id,
                    index == statuses.lastIndex && needsPlaceholder
                )
            }
            clearLoadMore?.let { clearLoadMore(it) }
        }
    }

    private fun hasSeenId(id: String): Boolean {
        return db.statusQueries.checkExists(id).executeAsOneOrNull() != null
    }

    private fun clearLoadMore(id: String) {
        db.statusQueries.clearLoadMore(id)
    }

    companion object : KoinComponent {
        fun create(mastodon: Mastodon, scope: CoroutineScope): TimelineStorage {
            val db = createDatabase(mastodon.id, get())
            return TimelineStorage(mastodon.id, db, mastodon, scope)
        }
    }
}