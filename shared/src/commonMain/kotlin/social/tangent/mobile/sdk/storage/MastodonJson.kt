package social.tangent.mobile.sdk.storage

import kotlinx.serialization.Serializable
import social.tangent.mobile.api.entities.Token

@Serializable
data class MastodonJson(
    val id: String,
    val token: Token,
    val domain: String
)