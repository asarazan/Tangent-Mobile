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
        // TODO api.
        return status.copy(
            favourited = faved,
            favouritesCount = status.favouritesCount + 1
        )
    }

    suspend fun reblog(status: Status, reblogged: Boolean): Status {
        // TODO api.
        return status.copy(
            reblogged = reblogged,
            reblogsCount = status.reblogsCount + 1
        )
    }

    companion object {
        fun all() {

        }
    }
}
