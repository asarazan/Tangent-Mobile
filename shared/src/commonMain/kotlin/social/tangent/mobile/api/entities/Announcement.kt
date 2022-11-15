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

import social.tangent.mobile.api.entities.AnnouncementReaction

/**
 * Represents an announcement set by an administrator.
 * @see https://docs.joinmastodon.org/entities/announcement/
 */
@Serializable
data class Announcement(

  /**
   * Description: The announcement id.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history:
   * 3.1.0 - added
   */
  val id: String,

  /**
   * Description: The content of the announcement.
   * Type: String
   * Version history:
   * 3.1.0 - added
   */
  val text: String,

  /**
   * Description: Whether the announcement is currently active.
   * Type: Boolean
   * Version history:
   * 3.1.0 - added
   */
  val published: Boolean,

  /**
   * Description: Whether the announcement has a start/end time.
   * Type: Boolean
   * Version history:
   * 3.1.0 - added
   */
  @SerialName("all_day")
  val allDay: Boolean,

  /**
   * Description: When the announcement was created.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.1.0 - added
   * @DateTime
   */
  @SerialName("created_at")
  @Serializable(with = kotlinx.datetime.serializers.InstantIso8601Serializer::class)
  val createdAt: kotlinx.datetime.Instant,

  /**
   * Description: When the announcement was last updated.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.1.0 - added
   * @DateTime
   */
  @SerialName("updated_at")
  @Serializable(with = kotlinx.datetime.serializers.InstantIso8601Serializer::class)
  val updatedAt: kotlinx.datetime.Instant,

  /**
   * Description: Whether the announcement has been read by the user.
   * Type: Boolean
   * Version history:
   * 3.1.0 - added
   */
  val read: Boolean,

  /**
   * Description: Emoji reactions attached to the announcement.
   * Type: Array of AnnouncementReaction
   * Version history:
   * 3.1.0 - added
   */
  val reactions: List<AnnouncementReaction>,

  /**
   * Description: When the future announcement was scheduled.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.1.0 - added
   * @DateTime
   */
  @SerialName("scheduled_at")
  @Serializable(with = kotlinx.datetime.serializers.InstantIso8601Serializer::class)
  val scheduledAt: kotlinx.datetime.Instant,

  /**
   * Description: When the future announcement will start.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.1.0 - added
   * @DateTime
   */
  @SerialName("starts_at")
  @Serializable(with = kotlinx.datetime.serializers.InstantIso8601Serializer::class)
  val startsAt: kotlinx.datetime.Instant,

  /**
   * Description: When the future announcement will end.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.1.0 - added
   * @DateTime
   */
  @SerialName("ends_at")
  @Serializable(with = kotlinx.datetime.serializers.InstantIso8601Serializer::class)
  val endsAt: kotlinx.datetime.Instant
)
