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
 * Represents a weekly bucket of instance activity.
 * @see https://docs.joinmastodon.org/entities/activity/
 */
@Serializable
data class Activity(

  /**
   * Description: Midnight at the first day of the week.
   * Type: String (UNIX Timestamp)
   * Version history: Added in 2.1.2
   */
  val week: String,

  /**
   * Description: Statuses created since the week began.
   * Type: String (cast from an integer)
   * Version history: Added in 2.1.2
   */
  val statuses: List<String>,

  /**
   * Description: User logins since the week began.
   * Type: String (cast from an integer)
   * Version history: Added in 2.1.2
   */
  val logins: String,

  /**
   * Description: User registrations since the week began.
   * Type: String (cast from an integer)
   * Version history: Added in 2.1.2
   */
  val registration: String
)
