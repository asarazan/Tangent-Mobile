package social.tangent.mobile.data.ng.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import social.tangent.mobile.data.tweets.StatusContent

class MemoryStorage(
    override val scope: CoroutineScope,
    initial: List<StatusContent> = listOf()
) : PostStorage
{
    override val posts: StateFlow<List<StatusContent>> get() = _posts
    private val _posts = MutableStateFlow(initial)

    private var ids = setOf<String>()

    override suspend fun add(posts: List<StatusContent>) {
        this.ids = ids + posts.map { it.id }
        this._posts.emit((this.posts.value + posts).distinctBy(StatusContent::id))
    }

    override suspend fun remove(posts: List<String>) {
        val set = posts.toSet()
        this.ids = ids - set
        this._posts.emit(this.posts.value.filter { !set.contains(it.id) })
    }

    override suspend fun has(post: String): Boolean {
        return this.posts.value.firstOrNull { it.id == post } != null
    }
}