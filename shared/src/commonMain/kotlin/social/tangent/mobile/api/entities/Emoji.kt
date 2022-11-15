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
 * Represents a custom emoji.
 * @see https://docs.joinmastodon.org/entities/emoji/
 */
@Serializable
data class Emoji(

  /**
   * Description: The name of the custom emoji.
   * Type: String
   * Version history: Added in 2.0.0
   */
  val shortcode: String,

  /**
   * Description: A link to the custom emoji.
   * Type: String (URL)
   * Version history: Added in 2.0.0
   */
  val url: String,

  /**
   * Description: A link to a static copy of the custom emoji.
   * Type: String (URL)
   * Version history: Added in 2.0.0
   */
  @SerialName("static_url") val staticUrl: String,

  /**
   * Description: Whether this Emoji should be visible in the picker or unlisted.
   * Type: Boolean
   * Version history: Added in 2.0.0
   */
  @SerialName("visible_in_picker") val visibleInPicker: Boolean,

  /**
   * Description: Used for sorting custom emoji in the picker.
   * Type: String
   * Version history: Added in 3.0.0
   */
  val category: String? = null
)
