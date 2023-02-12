package social.tangent.mobile.data.ng.repos

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import social.tangent.mobile.TangentDatabase
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.TimelineId
import social.tangent.mobile.data.tweets.timelineMapperBasic
import social.tangent.mobile.sdk.extensions.replaceStatus

class DbRepo(
    private val timeline: TimelineId,
    private val db: TangentDatabase,
    override val scope: CoroutineScope
): PostRepo {

    override val posts: StateFlow<List<Status>>
        get() = _posts

    private val query = db.timelineQueries.getTimeline(timeline(), ::timelineMapperBasic)
    private val _posts = MutableStateFlow(listOf<Status>())

    init { reload() }

    override fun requery(): List<Status> {
        return timeline.process(query.executeAsList())
    }

    override fun has(status: String): Boolean {
        return db.timelineQueries.checkExists(timeline(), status).executeAsOneOrNull() != null
    }

    override fun update(status: Status) {
        db.timelineQueries.update(
            id = status.id,
            json = status,
            reblogs = status.reblog?.id
        )
        notify(posts.value.replaceStatus(status))
    }

    override fun insert(statuses: List<Status>) {
        if (statuses.isEmpty()) return
        val finalStatuses = statuses.toMutableList()
        db.timelineQueries.transaction {
            finalStatuses.forEach {
                status ->
                db.timelineQueries.insert(
                    statusId = status.id,
                    json = status,
                    accound_id = status.id,
                    date = Json.encodeToString(status.createdAt),
                    reblogs = status.reblog?.id
                )
            }
        }
        val ids = statuses.map { it.id }.toSet()
        val content = posts.value.filter { !ids.contains(it.id) }.toMutableList()
        content.addAll(finalStatuses)
        notify(timeline.process(content))
    }

    override fun delete(statuses: List<String>) {
        db.timelineQueries.deleteIds(statuses)
        val set = statuses.toSet()
        notify(posts.value.filter { !set.contains(it.id) })
    }

    override fun reblogsOf(status: String): List<Status> {
        return db.timelineQueries.lookupReblogsOf(status).executeAsList()
    }

    private fun notify(content: List<Status>) {
        scope.launch {
            _posts.emit(content)
        }
    }

    private fun reload() {
        notify(requery())
    }
}