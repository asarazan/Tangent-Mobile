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
 * Represents an emoji reaction to an Announcement.
 * @see https://docs.joinmastodon.org/entities/announcementreaction/
 */
@Serializable
data class AnnouncementReaction(

  /**
   * Description: The emoji used for the reaction.
   * Either a unicode emoji, or a custom emoji's shortcode.
   * Type: String
   * Version history:
   * 3.1.0 - added
   */
  val name: String,

  /**
   * Description: The total number of users who have added this reaction.
   * Type: Number
   * Version history:
   * 3.1.0 - added
   * @precision long
   */
  val count: Long,

  /**
   * Description: Whether the authorized user has added this reaction to the announcement.
   * Type: Boolean
   * Version history:
   * 3.1.0 - added
   */
  val me: Boolean,

  /**
   * Description: A link to the custom emoji.
   * Type: String (URL)
   * Version history:
   * 3.1.0 - added
   */
  val url: String,

  /**
   * Description: A link to a non-animated version of the custom emoji.
   * Type: String (URL)
   * Version history:
   * 3.1.0 - added
   */
  @SerialName("static_url") val staticUrl: String
)
