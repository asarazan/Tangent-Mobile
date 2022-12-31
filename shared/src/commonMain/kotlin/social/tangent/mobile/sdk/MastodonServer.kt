package social.tangent.mobile.sdk

import org.koin.core.component.KoinComponent
import social.tangent.mobile.api.Api
import social.tangent.mobile.api.server
import social.tangent.mobile.sdk.credentials.ServerCredentials
import social.tangent.mobile.sdk.storage.MastodonStorage

class MastodonServer(
    val api: Api,
    private val creds: ServerCredentials
) : KoinComponent {

    val domain get() = creds.domain
    val app get() = creds.app
    val token get() = creds.token

    suspend fun authenticate(code: String): Mastodon {
        val token = api.fetchOAuthToken(
            domain,
            app.clientId!!,
            app.clientSecret!!,
            redirect,
            "authorization_code",
            code,
            scopes
        )
        val account = api.verifyAccountCredentials("Bearer ${token.accessToken}")
        val result = Mastodon(account.id, this, token)
        MastodonStorage.set(result)
        return result
    }

    companion object {

        const val client = "Tangent"
        const val redirect = "tangentsocial://tangent.social/redirect"
        const val scopes = "read write follow push"

        fun cached(domain: String, provideApi: Api? = null): MastodonServer? {
            val creds = ServerCredentials.get(domain) ?: return null
            val api = provideApi ?: Api.server(domain)
            return MastodonServer(api, creds)
        }

        suspend fun acquire(domain: String, provideApi: Api? = null): MastodonServer {
            val api = provideApi ?: Api.server(domain)
            val cached = cached(domain, api)
            if (cached != null) return cached
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
                "client_credentials",
                scope = scopes
            )
            val creds = ServerCredentials(domain, app, token)
            ServerCredentials.set(creds)
            return MastodonServer(api, creds)
        }
    }
}