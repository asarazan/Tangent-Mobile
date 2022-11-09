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

@Serializable
data class Notification(
  /**
   * The notification ID
   */
  val id: String,
  /**
   * One of: "mention", "reblog", "favourite", "follow"
   */
  val type: Type,
  /**
   * The time the notification was created
   */
  val created_at: String,
  /**
   * The [Account] sending the notification to the user
   */
  val account: Account,
  /**
   * The [Status] associated with the notification, if applicable
   */
  val status: Status
) {
  @Serializable
  enum class Type(
    val serialName: String
  ) {
    @SerialName("mention") MENTION("mention"),
    @SerialName("reblog") REBLOG("reblog"),
    @SerialName("favourite") FAVOURITE("favourite"),
    @SerialName("follow") FOLLOW("follow");
  }
}
