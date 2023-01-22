package social.tangent.mobile.sdk.helpers

import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.Mastodon

class MastodonAccounts(val mastodon: Mastodon) {
    val api get() = mastodon.api
    val token get() = mastodon.token

    suspend fun fetchFrom(fromId: String? = null): List<Status> {
        return mastodon.api.getAccountStatuses(
            authentication = mastodon.bearer(),
            maxId = fromId,
            limit = 40
        )
    }
}