package social.tangent.mobile.data.ng.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.data.ng.storage.PostStorage
import social.tangent.mobile.sdk.Mastodon
import social.tangent.mobile.util.loadOn

class TimelineRepo(
    override val scope: CoroutineScope,
    override val accountId: String,
    override val storage: PostStorage,
    val mastodon: Mastodon
) : PostRepo {
    override val loading: StateFlow<Boolean> get() = _loading
        .map { it > 0 }
        .stateIn(scope, SharingStarted.Eagerly, false)
    private val _loading = MutableStateFlow(0)
    override suspend fun loadMore(from: Status?) {
        _loading.loadOn(if (from == null) 1 else null) {
            val result = mastodon.timeline.fetchFrom(from?.id)
            storage.add(result, from)
        }
    }
}