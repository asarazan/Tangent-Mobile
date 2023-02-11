package social.tangent.mobile.data.ng.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.tweets.StatusContent

interface PostStorage {

    val posts: StateFlow<List<StatusContent>>
    val scope: CoroutineScope

    fun update(status: Status)
    fun insert(statuses: List<StatusContent>)
    fun delete(statuses: List<String>)
    fun has(status: String): Boolean
    fun reblogsOf(status: String): List<Status>

    fun clearLoadMore(id: String)

    suspend fun insert(posts: List<Status>, from: Status?) {
        val content = posts.mapIndexed { index, it ->
            StatusContent(
                id = it.id,
                status = it,
                loadMore = index == posts.lastIndex && !has(it.id)
            )
        }.toMutableList()
        // Clear the loadMore bit.
        if (from != null) content += StatusContent(from.id, from, false)
        insert(content)
    }
}