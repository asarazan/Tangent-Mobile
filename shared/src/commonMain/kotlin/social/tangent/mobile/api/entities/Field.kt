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
 * Represents a profile field as a name-value pair with optional verification.
 * @see https://docs.joinmastodon.org/entities/field/
 */
@Serializable
data class Field(
  /**
   * The key of a given field's key-value pair.
   */
  val name: String,
  /**
   * The value associated with the name key.
   * Type: String (HTML)
   */
  val value: String,
  /**
   * Timestamp of when the server verified a URL value for a rel="me" link.
   * String (ISO 8601 Datetime) if value is a verified URL.
   */
  @SerialName("verified_at") val verifiedAt: String? = null
)
