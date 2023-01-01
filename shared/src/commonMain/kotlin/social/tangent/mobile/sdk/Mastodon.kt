package social.tangent.mobile.sdk

import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.entities.Token
import social.tangent.mobile.sdk.timeline.MastodonTimeline

class Mastodon(
    val id: String,
    val server: MastodonServer,
    val token: Token
) : KoinComponent {

    val api get() = server.api

    val timeline = MastodonTimeline(this)

    internal fun bearer(): String = "Bearer ${token.accessToken}"

    suspend fun fave(status: Status, faved: Boolean): Status {
        return if (faved) api.favourite(bearer(), status.id) else api.unfavourite(bearer(), id)
    }

    suspend fun reblog(status: Status, reblogged: Boolean): Status {
        return if (reblogged) api.reblog(bearer(), status.id) else api.unreblog(bearer(), id)
    }

    companion object {}
}
