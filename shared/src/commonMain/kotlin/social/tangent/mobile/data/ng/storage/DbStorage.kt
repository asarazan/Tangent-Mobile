package social.tangent.mobile.data.ng.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import social.tangent.mobile.TangentDatabase
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.StatusContent
import social.tangent.mobile.data.tweets.TimelineId
import social.tangent.mobile.data.tweets.timelineMapper
import social.tangent.mobile.sdk.extensions.flagLoadMore
import social.tangent.mobile.sdk.extensions.replace

class DbStorage(
    private val timeline: TimelineId,
    private val db: TangentDatabase,
    override val scope: CoroutineScope
): PostStorage {

    override val posts: StateFlow<List<StatusContent>>
        get() = _posts

    private val query = db.timelineQueries.getTimeline(timeline(), ::timelineMapper)
    private val _posts = MutableStateFlow(listOf<StatusContent>())

    init { reload() }

    override fun has(status: String): Boolean {
        return db.timelineQueries.checkExists(timeline(), status).executeAsOneOrNull() != null
    }

    override fun update(status: Status) {
        db.timelineQueries.update(
            id = status.id,
            json = status,
            reblogs = status.reblog?.id
        )
        notify(posts.value.replace(status))
    }

    override fun insert(statuses: List<StatusContent>) {
        if (statuses.isEmpty()) return
        val needsLoadMore = timeline.canLoadMore && !has(statuses.last().id)
        val finalStatuses = statuses.toMutableList()
        if (needsLoadMore) finalStatuses.flagLoadMore()
        db.timelineQueries.transaction {
            finalStatuses.forEach {
                status ->
                db.timelineQueries.insert(
                    statusId = status.status.id,
                    json = status.status,
                    accound_id = status.status.id,
                    date = Json.encodeToString(status.status.createdAt),
                    reblogs = status.status.reblog?.id
                )
                db.timelineQueries.addToTimeline(timeline(), status.id, status.loadMore)
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

    override fun clearLoadMore(id: String) {
        db.timelineQueries.clearLoadMore(timeline(), id)
    }

    override fun reblogsOf(status: String): List<Status> {
        return db.timelineQueries.lookupReblogsOf(status).executeAsList()
    }

    private fun notify(content: List<StatusContent>) {
        scope.launch {
            _posts.emit(content)
        }
    }

    private fun reload() {
        notify(query.executeAsList())
    }
}