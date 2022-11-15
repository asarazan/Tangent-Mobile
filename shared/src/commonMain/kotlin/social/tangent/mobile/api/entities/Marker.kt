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

import social.tangent.mobile.api.entities.MarkerEntry

/**
 * Represents the last read position within a user's timelines.
 * @see https://docs.joinmastodon.org/entities/marker/
 */
@Serializable
data class Marker(

  /**
   * Description: Information about the user's position in the home timeline.
   * Type: Hash
   * Version history: Added in 3.0.0
   */
  val home: MarkerEntry,

  /**
   * Description: Information about the user's position in their notifications.
   * Type: Hash
   * Version history: Added in 3.0.0
   */
  val notifications: MarkerEntry
)

/**
 * This was supposed to be a hash, but see above.
 */
@Serializable
data class MarkerEntry(

  /**
   * Description: The ID of the most recently viewed entity.
   * Type: String (cast from integer but not guaranteed to be a number)
   * Version history: Added in 3.0.0
   */
  @SerialName("last_read_id")
  val lastReadId: String,

  /**
   * Description: The timestamp of when the marker was set.
   * Type: String (ISO 8601 Datetime)
   * Version history: Added in 3.0.0
   * @DateTime
   */
  @SerialName("updated_at")
  @Serializable(with = kotlinx.datetime.serializers.InstantIso8601Serializer::class)
  val updatedAt: kotlinx.datetime.Instant,

  /**
   * Description: Used for locking to prevent write conflicts.
   * Type: Number
   * Version history: Added in 3.0.0
   */
  val version: String
)
