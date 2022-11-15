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
 * Represents a status that will be published at a future scheduled date.
 * @see https://docs.joinmastodon.org/entities/scheduledstatus/
 */
@Serializable
data class ScheduledStatus(

  /**
   * Description: ID of the scheduled status in the database.
   * Type: String (cast from an integer but not guaranteed to be a number)
   * Version history: Added in 2.7.0
   */
  val id: String,

  /**
   * Description: ID of the status in the database.
   * Type: String (ISO 8601 Datetime)
   * Version history: Added in 2.7.0
   */
  @SerialName("scheduled_at")
  val scheduledAt: String
)
