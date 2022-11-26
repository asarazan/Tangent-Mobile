package social.tangent.mobile.sdk

import org.koin.core.component.KoinComponent

const val redirect = "oauth2redirect://social.tangent.mobile"
const val scopes = "read write follow push"

interface Mastodon {
    companion object : KoinComponent {
        // fun get(clientName: String): MastodonInstance
    }
    suspend fun current(): MastodonInstance
}

interface MastodonInstance
