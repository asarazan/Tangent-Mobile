package social.tangent.mobile.sdk

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.Api
import social.tangent.mobile.api.create
import social.tangent.mobile.api.entities.Application
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.entities.Token

class Mastodon(val api: Api, val domain: String, val app: Application? = null, val token: Token? = null) {

    companion object : KoinComponent {
        val publicDefault = GlobalScope.async { create("https://mastodon.social") }

        const val client = "Tangent"
        const val redirect = "tangentsocial://tangent.social/redirect"
        const val scopes = "read write follow push"

        suspend fun create(domain: String): Mastodon {
            val api = Api.create(domain)
            val app = api.authenticateApp(
                domain = domain,
                clientName = client,
                redirectUris = redirect,
                scopes = scopes
            )
            val token = api.fetchOAuthToken(
                domain,
                app.clientId!!,
                app.clientSecret!!,
                redirect,
                "client_credentials"
            )
            return Mastodon(api, domain, app, token)
        }
    }

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
}
