package social.tangent.mobile.data.ng.repos.posts

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import social.tangent.mobile.TangentDatabase
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.timelines.TimelineKind

// TODO - see if there's a more efficient way to thread/do this
class CompositeRepo(
    timeline: TimelineKind,
    db: TangentDatabase,
    override val scope: CoroutineScope
) : PostRepo {
    override val posts: StateFlow<List<Status>>
        get() = memory.posts

    private val backup = DbPostsRepo(timeline, db, scope)
    private val memory = MemoryRepo(timeline, scope)

    init {
        memory.insert(backup.requery())
    }

    override fun requery(): List<Status> {
        return memory.requery()
    }

    override fun update(status: Status) {
        memory.update(status)
        backup.update(status)
    }

    override fun insert(statuses: List<Status>) {
        memory.insert(statuses)
        backup.insert(statuses)
    }

    override fun delete(statuses: List<String>) {
        memory.delete(statuses)
        backup.delete(statuses)
    }

    override fun has(status: String): Boolean {
        return memory.has(status)
    }

    override fun reblogsOf(status: String): List<Status> {
        return memory.reblogsOf(status)
    }
}