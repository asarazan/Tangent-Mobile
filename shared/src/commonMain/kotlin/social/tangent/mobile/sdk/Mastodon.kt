package social.tangent.mobile.sdk

import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.Api
import social.tangent.mobile.api.create
import social.tangent.mobile.api.entities.Application
import social.tangent.mobile.api.entities.Token

class Mastodon(val api: Api, val domain: String, val app: Application, val token: Token) {

    companion object : KoinComponent {

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
}
