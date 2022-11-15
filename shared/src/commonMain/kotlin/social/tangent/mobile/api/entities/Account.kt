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

import social.tangent.mobile.api.entities.Emoji
import social.tangent.mobile.api.entities.Field
import social.tangent.mobile.api.entities.Source
import social.tangent.mobile.api.entities.Account

/**
 * Represents a user of Mastodon and their associated profile.
 * @see https://docs.joinmastodon.org/entities/account/
 */
@Serializable
data class Account(

  /**
   * Description: The account idheader.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history:
   * 0.1.0 - added
   */
  val id: String,

  /**
   * Description: The username of the account, not including domain.
   * Type: String
   * Version history:
   * 0.1.0 - added
   */
  val username: String,

  /**
   * Description: The Webfinger account URI.
   * Equal to username for local users, or username@domain for remote users.
   * Type: String
   * Version history:
   * 0.1.0 - added
   */
  val acct: String,

  /**
   * Description: The location of the user's profile page.
   * Type: String (HTTPS URL)
   * Version history:
   * 0.1.0 - added
   */
  val url: String,

  /**
   * Description: The profile's display name.
   * Type: String
   * Version history:
   * 0.1.0 - added
   */
  @SerialName("display_name")
  val displayName: String,

  /**
   * Description: The profile's bio / description.
   * Type: String (HTML)
   * Version history:
   * 0.1.0 - added
   */
  val note: String,

  /**
   * Description: An image icon that is shown next to statuses and in the profile.
   * Type: String (URL)
   * Version history:
   * 0.1.0 - added
   */
  val avatar: String,

  /**
   * Description: A static version of the avatar.
   * Equal to avatar if its value is a static image; different if avatar is an animated GIF.
   * Type: String (URL)
   * Version history:
   * 1.1.2 - added
   */
  @SerialName("avatar_static")
  val avatarStatic: String,

  /**
   * Description: An image banner that is shown above the profile and in profile cards.
   * Type: String (URL)
   * Version history:
   * 0.1.0 - added
   */
  val header: String,

  /**
   * Description: A static version of the header.
   * Equal to header if its value is a static image; different if header is an animated GIF.
   * Type: String (URL)
   * Version history:
   * 1.1.2 - added
   */
  @SerialName("header_static")
  val headerStatic: String,

  /**
   * Description: Whether the account manually approves follow requests.
   * Type: Boolean
   * Version history:
   * 0.1.0 - added
   */
  val locked: Boolean,

  /**
   * Description: Custom emoji entities to be used when rendering the profile. If none, an empty array will be returned.
   * Type: Array of Emoji
   * Version history:
   * 2.4.0 - added
   */
  val emojis: List<Emoji>,

  /**
   * Description: Whether the account has opted into discovery features such as the profile directory.
   * Type: Boolean
   * Version history:
   * 3.1.0 - added
   */
  val discoverable: Boolean,

  /**
   * Description: When the account was created.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 0.1.0 - added
   */
  @SerialName("created_at")
  val createdAt: String,

  /**
   * Description: When the most recent status was posted.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.0.0 - added
   * 3.1.0 - now returns date only, no time
   */
  @SerialName("last_status_at")
  val lastStatusAt: String,

  /**
   * Description: How many statuses are attached to this account.
   * Type: Number
   * Version history:
   * 0.1.0 - added
   * @precision long
   */
  @SerialName("statuses_count")
  val statusesCount: Long,

  /**
   * Description: The reported followers of this profile.
   * Type: Number
   * Version history:
   * 0.1.0 - added
   * @precision long
   */
  @SerialName("followers_count")
  val followersCount: Long,

  /**
   * Description: The reported follows of this profile.
   * Type: Number
   * Version history:
   * 0.1.0 - added
   * @precision long
   */
  @SerialName("following_count")
  val followingCount: Long,

  /**
   * Description: Indicates that the profile is currently inactive and that its user has moved to a new account.
   * Type: Account
   * Version history:
   * 2.1.0 - added
   */
  val moved: Account? = null,

  /**
   * Description: Additional metadata attached to a profile as name-value pairs.
   * Type: Array of Field
   * Version history:
   * 2.4.0 - added
   */
  val fields: List<Field>? = null,

  /**
   * Description: A presentational flag.
   * Indicates that the account may perform automated actions, may not be monitored, or identifies as a robot.
   * Type: Boolean
   * Version history:
   * 2.4.0 - added
   */
  val bot: Boolean? = null,

  /**
   * Description: An extra entity to be used with API methods to verify credentials and update credentials.
   * Type: Source
   * Version history:
   * 2.4.0 - added
   * @link https://docs.joinmastodon.org/methods/accounts/#verify-account-credentials
   *    * 
   * @link https://docs.joinmastodon.org/methods/accounts/#update-account-credentials
   */
  val source: Source? = null,

  /**
   * Description: An extra entity returned when an account is suspended.
   * Type: Boolean
   * Version history:
   * 3.3.0 - added
   */
  val suspended: Boolean? = null,

  /**
   * Description: When a timed mute will expire, if applicable.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.3.0 - added
   */
  @SerialName("mute_expires_at")
  val muteExpiresAt: Boolean? = null
)
