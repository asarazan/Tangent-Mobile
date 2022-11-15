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
 * Represents a mention of a user within the content of a status.
 * @see https://docs.joinmastodon.org/entities/mention/
 */
@Serializable
data class Mention(
  /**
   * Description: The account id of the mentioned user.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history: Added in 0.6.0
   */
  val id: String,
  /**
   * Description: The username of the mentioned user.
   * Type: String
   * Version history: Added in 0.6.0
   */
  val username: String,
  /**
   * Description: The webfinger acct: URI of the mentioned user.
   * Equivalent to username for local users, or username@domain for remote users.
   * Type: String
   * Version history: Added in 0.6.0
   */
  val acct: String,
  /**
   * Description: The location of the mentioned user's profile.
   * Type: String (URL)
   * Version history: Added in 0.6.0
   */
  val url: String
)
