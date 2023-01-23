package social.tangent.mobile.sdk.helpers

import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.sdk.Mastodon

class MastodonStatuses(val mastodon: Mastodon) {
    val api get() = mastodon.api
    val token get() = mastodon.token

    suspend fun fave(statusId: String, faved: Boolean): Status {
        return if (faved) api.favourite(mastodon.bearer(), statusId)
        else api.unfavourite(mastodon.bearer(), statusId)
    }

    suspend fun reblog(statusId: String, reblogged: Boolean): Status {
        return if (reblogged) api.reblog(mastodon.bearer(), statusId)
        else api.unreblog(mastodon.bearer(), statusId)
    }
}