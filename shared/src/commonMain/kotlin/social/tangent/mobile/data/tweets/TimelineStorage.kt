package social.tangent.mobile.data.tweets

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
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
import org.koin.core.component.inject
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

    // TODO use this.
    private val settings: Settings by inject()
    private var latestIdSeen: String
        get() = settings.getString("__latest_id_${id}", "")
        set(value) = settings.set("__latest_id_${id}", value)

    suspend fun fetch(from: Status) {
        val timeline = mastodon.timeline.fetch(fromId = from.id)
        insert(timeline, from.id)
    }

    suspend fun fetch() {
        if (_isLoading.value) return
        _isLoading.emit(true)
        val timeline = mastodon.timeline.fetch()
        insert(timeline, null)
        _isLoading.emit(false)
    }

    suspend fun fave(status: Status, faved: Boolean) {
        val preview = status.copy(favourited = faved)
        insert(preview)
        val result = if (faved) api.favourite(mastodon.bearer(), status.id) else api.unfavourite(mastodon.bearer(), status.id)
        insert(result)
    }

    suspend fun reblog(status: Status, reblogged: Boolean) {
        val preview = status.copy(reblogged = reblogged)
        insert(preview)
        if (reblogged) api.reblog(mastodon.bearer(), status.id) else api.unreblog(mastodon.bearer(), status.id)
        // we don't insert the single result
        // because the broader timeline would not return it
    }

    private fun insert(status: Status) {
        insert(listOf(status))
    }

    private fun insert(statuses: List<Status>, clearLoadMore: String? = null) {
        if (statuses.isEmpty()) return
        statuses.first().let { latestIdSeen = maxOf(latestIdSeen, it.id) }
        val needsPlaceholder = !hasSeenId(statuses.last().id)
        db.transaction {
            statuses.forEachIndexed {
                index, it ->
                db.statusQueries.insert(
                    it.id,
                    it,
                    it.account.id,
                    Json.encodeToString(it.createdAt),
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