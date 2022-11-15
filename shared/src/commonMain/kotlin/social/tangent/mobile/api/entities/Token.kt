package social.tangent.mobile.api.entities

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject

/**
 * Represents an OAuth token used for authenticating with the API and performing actions.
 * @see https://docs.joinmastodon.org/entities/token/
 */
@Serializable
data class Token(
  /**
   * Description: An OAuth token to be used for authorization.
   * Type: String
   * Version history: Added in 0.1.0
   */
  @SerialName("access_token") val accessToken: String,
  /**
   * Description: The OAuth token type. Mastodon uses Bearer tokens.
   * Type: String
   * Version history: Added in 0.1.0
   */
  @SerialName("token_type") val tokenType: String,
  /**
   * Description: The OAuth scopes granted by this token, space-separated.
   * Type: String
   * Version history: Added in 0.1.0
   */
  val scope: String,
  /**
   * Description: When the token was generated.
   * Type: Number (UNIX Timestamp)
   * Version history: Added in 0.1.0
   * @precision long
   */
  @SerialName("created_at") val createdAt: Long
)
