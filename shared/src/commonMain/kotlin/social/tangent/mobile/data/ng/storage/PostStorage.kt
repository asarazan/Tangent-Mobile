package social.tangent.mobile.data.ng.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.StatusContent

interface PostStorage {
    val posts: StateFlow<List<StatusContent>>
    val scope: CoroutineScope
    suspend fun add(posts: List<StatusContent>)
    suspend fun remove(posts: List<String>)
    suspend fun has(post: String): Boolean

    suspend fun add(posts: List<Status>, from: Status?) {
        val content = posts.mapIndexed { index, it ->
            StatusContent(
                it.id,
                it,
                index == posts.lastIndex && !has(it.id)
            )
        }.toMutableList()
        // Clear the loadMore bit.
        if (from != null) content += StatusContent(from.id, from, false)
        add(content)
    }
}