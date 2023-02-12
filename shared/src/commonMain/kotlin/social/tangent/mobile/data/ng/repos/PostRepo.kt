package social.tangent.mobile.data.ng.repos

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import social.tangent.mobile.api.entities.Status

interface PostRepo {

    val posts: StateFlow<List<Status>>
    val scope: CoroutineScope

    fun requery(): List<Status>
    fun update(status: Status)
    fun insert(statuses: List<Status>)
    fun delete(statuses: List<String>)
    fun has(status: String): Boolean
    fun reblogsOf(status: String): List<Status>
}