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

    private val _isLoading = MutableStateFlow(false)

    private val raw = db.statusQueries.selectAll(::timelineMapper)
        .asFlow()
        .map { Timeline(it.executeAsList()) }
        .stateIn(scope, SharingStarted.Eagerly, Timeline(listOf()))

    suspend fun fetch() {
        if (_isLoading.value) return
        _isLoading.emit(true)
        val timeline = mastodon.timeline.head()
        insert(timeline)
        _isLoading.emit(false)
    }

    private fun insert(statuses: List<Status>) {
        db.transaction {
            statuses.forEach {
                db.statusQueries.insert(
                    it.id,
                    it.account.id,
                    Json.encodeToString(it.createdAt),
                    false, // TODO
                    it
                )
            }
        }
    }

    companion object : KoinComponent {
        fun create(mastodon: Mastodon, scope: CoroutineScope): TimelineStorage {
            val db = createDatabase(mastodon.id, get())
            return TimelineStorage(mastodon.id, db, mastodon, scope)
        }
    }
}