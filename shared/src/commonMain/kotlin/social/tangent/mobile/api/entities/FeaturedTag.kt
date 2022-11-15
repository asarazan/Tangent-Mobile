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
 * Represents a hashtag that is featured on a profile.
 * @see https://docs.joinmastodon.org/entities/featuredtag/
 */
@Serializable
data class FeaturedTag(

  /**
   * Description: The internal ID of the featured tag in the database.
   * Type: String (cast from integer but not guaranteed to be a number)
   * Version history: Added in 3.0.0
   */
  val id: String,

  /**
   * Description: The name of the hashtag being featured.
   * Type: String
   * Version history: Added in 3.0.0
   */
  val name: String,

  /**
   * Description: A link to all statuses by a user that contain this hashtag.
   * Type: String (URL)
   * Version history: Added in 3.3.0
   */
  val url: String,

  /**
   * Description: The number of authored statuses containing this hashtag.
   * Type: Number
   * Version history: Added in 3.0.0
   * @precision long
   */
  @SerialName("statuses_count")
  val statusesCount: Long,

  /**
   * Description: The timestamp of the last authored status containing this hashtag.
   * Type: String (ISO 8601 Datetime)
   * Version history: Added in 3.0.0
   * @DateTime
   */
  @SerialName("last_status_at")
  @Serializable(with = kotlinx.datetime.serializers.InstantIso8601Serializer::class)
  val lastStatusAt: kotlinx.datetime.Instant
)
