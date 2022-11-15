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

import social.tangent.mobile.api.entities.Account
import social.tangent.mobile.api.entities.Status

/**
 * Represents a notification of an event relevant to the user.
 * @see https://docs.joinmastodon.org/entities/notification/
 */
@Serializable
data class Notification(
  /**
   * Description: The id of the notification in the database.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history:
   * 0.9.9 - added
   */
  val id: String,
  /**
   * Description: The type of event that resulted in the notification.
   * Type: String (Enumerable oneOf)
   * follow = Someone followed you
   * follow_request = Someone requested to follow you
   * mention = Someone mentioned you in their status
   * reblog = Someone boosted one of your statuses
   * favourite = Someone favourited one of your statuses
   * poll = A poll you have voted in or created has ended
   * status = Someone you enabled notifications for has posted a status
   * 
   * Version history:
   * 0.9.9 - added
   * 2.8.0 - added poll
   * 3.1.0 - added follow_request
   * 3.3.0 - added status
   */
  val type: Type,
  /**
   * Description: The timestamp of the notification.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 0.9.9 - added
   */
  @SerialName("created_at") val createdAt: String,
  /**
   * Description: The account that performed the action that generated the notification.
   * Type: Account
   * Version history:
   * 0.9.9 - added
   */
  val account: Account,
  /**
   * Description: Status that was the object of the notification,
   * e.g. in mentions, reblogs, favourites, or polls.
   * Type: Status
   * Version history:
   * 0.9.9 - added
   */
  val status: Status
) {
  @Serializable
  enum class Type(
    val serialName: String
  ) {
    @SerialName("follow") FOLLOW("follow"),
    @SerialName("follow_request") FOLLOW_REQUEST("follow_request"),
    @SerialName("mention") MENTION("mention"),
    @SerialName("reblog") REBLOG("reblog"),
    @SerialName("favourite") FAVOURITE("favourite"),
    @SerialName("poll") POLL("poll"),
    @SerialName("status") STATUS("status");
  }
}
