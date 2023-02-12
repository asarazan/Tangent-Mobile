package social.tangent.mobile.data.ng.repos.posts

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.timelines.TimelineKind

class MemoryRepo(
    private val timeline: TimelineKind,
    override val scope: CoroutineScope
) : PostRepo {

    override val posts: StateFlow<List<Status>>
        get() = _posts

    private val _posts = MutableStateFlow(listOf<Status>())
    private val map = linkedMapOf<String, Status>()

    override fun requery(): List<Status> {
        return timeline.process(map.values.toList())
    }

    override fun update(status: Status) {
        map[status.id] = status
        refresh()
    }

    override fun insert(statuses: List<Status>) {
        statuses.forEach {
            map[it.id] = it
        }
        refresh()
    }

    override fun delete(statuses: List<String>) {
        statuses.forEach {
            map.remove(it)
        }
        refresh()
    }

    override fun has(status: String): Boolean {
        return map.containsKey(status)
    }

    // Pretty slow. Could maintain a reblog map.
    override fun reblogsOf(status: String): List<Status> {
        return map.values.filter { it.reblog?.id == status }
    }

    private fun refresh() {
        scope.launch {
            _posts.emit(requery())
        }
    }
}