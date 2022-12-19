package social.tangent.mobile.sdk.credentials

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import social.tangent.mobile.api.entities.Application
import social.tangent.mobile.api.entities.Token

@kotlinx.serialization.Serializable
data class CachedCredentials(
    val domain: String,
    val app: Application,
    val token: Token
) {
    companion object : KoinComponent {
        val settings by lazy {
            get<Settings>()
        }

        private fun key(domain: String): String = "__creds_${domain}".lowercase()

        fun get(domain: String): CachedCredentials? {
            val json: String? = settings[key(domain)]
            if (json != null) {
                return Json.decodeFromString(json)
            }
            return null
        }

        fun set(credentials: CachedCredentials) {
            settings[key(credentials.domain)] = Json.encodeToString(credentials)
        }
    }
}
