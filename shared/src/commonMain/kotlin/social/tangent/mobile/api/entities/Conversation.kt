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
 * Represents a conversation with "direct message" visibility.
 * @see https://docs.joinmastodon.org/entities/conversation/
 */
@Serializable
data class Conversation(

  /**
   * Description: Local database ID of the conversation.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history: Added in 2.6.0
   */
  val id: String,

  /**
   * Description: Participants in the conversation.
   * Type: Array of Account
   * Version history: Added in 2.6.0
   */
  val accounts: List<Account>,

  /**
   * Description: Is the conversation currently marked as unread?
   * Type: Boolean
   * Version history: Added in 2.6.0
   */
  val unread: Boolean,

  /**
   * Description: The last status in the conversation, to be used for optional display.
   * Type: Status
   * Version history: Added in 2.6.0
   */
  @SerialName("last_status") val lastStatus: Status? = null
)
