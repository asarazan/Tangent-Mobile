package social.tangent.mobile.data.ng.repos.posts

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import social.tangent.mobile.TangentDatabase
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.timelineMapperBasic
import social.tangent.mobile.data.tweets.timelines.TimelineKind

/**
 * This has morphed into a tightly coupled hybrid memory/db implementation.
 * It needs to be performant so here we are.
 */
class DbPostsRepo(
    private val kind: TimelineKind,
    private val db: TangentDatabase,
    override val scope: CoroutineScope
): PostRepo {

    override val posts: StateFlow<List<Status>>
        get() = _posts

    private val query = db.timelineQueries.getTimeline(kind(), ::timelineMapperBasic)
    private val _posts = MutableStateFlow(listOf<Status>())
    private var map = linkedMapOf<String, Status>()

    init { reload() }

    override fun requery(): List<Status> {
        return kind.process(query.executeAsList())
    }

    override fun has(status: String): Boolean {
        return map.containsKey(status)
    }

    override fun update(status: Status) {
        db.timelineQueries.update(
            id = status.id,
            json = status,
            reblogs = status.reblog?.id
        )
        map[status.id] = status
        refresh()
    }

    override fun insert(statuses: List<Status>) {
        if (statuses.isEmpty()) return
        val addGap = kind.supportsGaps && !has(statuses.last().id)
        db.timelineQueries.transaction {
            statuses.forEachIndexed { index, status ->
                map[status.id] = status
                db.timelineQueries.insert(
                    statusId = status.id,
                    json = status,
                    accound_id = status.id,
                    date = Json.encodeToString(status.createdAt),
                    reblogs = status.reblog?.id
                )
                // The gap logic is incredibly gross, but I couldn't figure out
                // a better way to do it transactionally.
                db.timelineQueries.addToTimeline(kind(), status.id, addGap && index == statuses.lastIndex)
            }
        }
        refresh()
    }

    override fun delete(statuses: List<String>) {
        db.timelineQueries.deleteIds(statuses)
        statuses.forEach {
            map.remove(it)
        }
        refresh()
    }

    override fun reblogsOf(status: String): List<Status> {
        return map.values.filter { it.reblog?.id == status }
    }

    private fun refresh(content: List<Status> = map.values.toList()) {
        scope.launch {
            _posts.emit(kind.process(content))
        }
    }

    private fun reload() {
        val content = requery()
        map = LinkedHashMap(content.associateBy { it.id })
        refresh(content)
    }
}