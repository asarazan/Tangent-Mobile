package social.tangent.mobile.data.ng.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import social.tangent.mobile.data.tweets.StatusContent

class CompositeStorage(
    override val scope: CoroutineScope,
    val memory: PostStorage,
    val persistent: PostStorage
) : PostStorage {

    val all = listOf(memory, persistent)

    init {
        scope.launch { memory.add(persistent.posts.value) }
    }

    override val posts: StateFlow<List<StatusContent>>
        get() = memory.posts

    override suspend fun add(posts: List<StatusContent>) {
        all.forEach { it.add(posts) }
    }

    override suspend fun remove(posts: List<String>) {
        all.forEach { it.remove(posts) }
    }

    override suspend fun has(post: String): Boolean {
        return memory.has(post)
    }
}