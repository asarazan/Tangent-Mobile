package social.tangent.mobile.data.ng.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.ng.PostResult
import social.tangent.mobile.data.ng.storage.PostStorage

interface PostRepo {
    val scope: CoroutineScope
    val accountId: String
    val storage: PostStorage
    val loading: StateFlow<Boolean>
    val results: StateFlow<PostResult>
        get() = storage.posts.map {
            PostResult(it)
        }.stateIn(scope, SharingStarted.Eagerly, PostResult(listOf()))
    suspend fun loadMore(from: Status? = null)
}