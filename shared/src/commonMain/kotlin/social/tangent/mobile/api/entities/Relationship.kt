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
 * Represents the relationship between accounts, such as following / blocking / muting / etc.
 * @see https://docs.joinmastodon.org/entities/relationship/
 */
@Serializable
data class Relationship(

  /**
   * Description: The account id.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history:
   * 0.6.0 - added
   */
  val id: String,

  /**
   * Description: Are you following this user?
   * Type: Boolean
   * Version history:
   * 0.6.0 - added
   */
  val following: Boolean,

  /**
   * Description: Do you have a pending follow request for this user?
   * Type: Boolean
   * Version history:
   * 0.9.9 - added
   */
  val requested: Boolean,

  /**
   * Description: Are you featuring this user on your profile?
   * Type: Boolean
   * Version history:
   * 2.5.0 - added
   */
  val endorsed: Boolean,

  /**
   * Description: Are you followed by this user?
   * Type: Boolean
   * Version history:
   * 0.6.0 - added
   */
  @SerialName("followed_by")
  val followedBy: Boolean,

  /**
   * Description: Are you muting this user?
   * Type: Boolean
   * Version history: Added in 1.1.0
   */
  val muting: Boolean,

  /**
   * Description: Are you muting notifications from this user?
   * Type: Boolean
   * Version history:
   * 2.1.0 - added
   */
  @SerialName("muting_notifications")
  val mutingNotifications: Boolean,

  /**
   * Description: Are you receiving this user's boosts in your home timeline?
   * Type: Boolean
   * Version history:
   * 2.1.0 - added
   */
  @SerialName("showing_reblogs")
  val showingReblogs: Boolean,

  /**
   * Description: Have you enabled notifications for this user?
   * Type: Boolean
   * Version history:
   * 3.3.0 - added
   */
  val notifying: Boolean,

  /**
   * Description: Are you blocking this user?
   * Type: Boolean
   * Version history:
   * 0.6.0 - added
   */
  val blocking: Boolean,

  /**
   * Description: Are you blocking this user's domain?
   * Type: Boolean
   * Version history:
   * 1.4.0 - added
   */
  @SerialName("domain_blocking")
  val domainBlocking: Boolean,

  /**
   * Description: Is this user blocking you?
   * Type: Boolean
   * Version history:
   * 2.8.0 - added
   */
  @SerialName("blocked_by")
  val blockedBy: Boolean,

  /**
   * Description: This user's profile bio
   * Type: String
   * Version history:
   * 3.2.0 - added
   */
  val note: String
)
