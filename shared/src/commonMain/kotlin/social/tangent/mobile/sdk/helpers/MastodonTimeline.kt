package social.tangent.mobile.sdk.helpers

import social.tangent.mobile.api.entities.Context
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.Mastodon

private const val LIMIT = 40
// private const val LIMIT = 1

class MastodonTimeline(val mastodon: Mastodon) {

    val api get() = mastodon.api
    val token get() = mastodon.token

    suspend fun fetchFrom(fromId: String? = null): List<Status> {
        return mastodon.api.getHomeTimeline(
            authentication = mastodon.bearer(),
            maxId = fromId,
            limit = LIMIT
        )
    }

    suspend fun fetchById(id: String): Status {
        return mastodon.api.getStatus(
            authentication = mastodon.bearer(),
            id = id
        )
    }

    suspend fun fetchThread(id: String): Context {
        return mastodon.api.getContext(
            authentication = mastodon.bearer(),
            id = id
        )
    }
}