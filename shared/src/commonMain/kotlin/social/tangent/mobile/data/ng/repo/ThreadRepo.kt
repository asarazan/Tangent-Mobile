package social.tangent.mobile.data.ng.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.extensions.toContent
import social.tangent.mobile.data.ng.PostRepo
import social.tangent.mobile.data.ng.storage.MemoryStorage
import social.tangent.mobile.data.ng.storage.PostStorage
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.util.loadOn

class ThreadRepo(
    override val scope: CoroutineScope,
    override val accountId: String,
    val status: Status,
    val mastodon: Mastodon
) : PostRepo {

    override val loading: StateFlow<Boolean> get() = _loading
        .map { it > 0 }
        .stateIn(scope, SharingStarted.Eagerly, false)
    private val _loading = MutableStateFlow(0)

    override val storage: PostStorage = MemoryStorage(scope, listOf(status.toContent()))
    override suspend fun loadMore(from: Status?) {
        _loading.loadOn(1) {
            val context = this.mastodon.timeline.fetchThread(status.id)
            val list = context.ancestors + status + context.descendants
            storage.add(list.map(Status::toContent))
        }
    }
}