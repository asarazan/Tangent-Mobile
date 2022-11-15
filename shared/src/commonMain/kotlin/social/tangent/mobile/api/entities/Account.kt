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
   * The account id header.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   */
  val id: String,
  /**
   * The username of the account, not including domain.
   */
  val username: String,
  /**
   * The Webfinger account URI.
   * Equal to username for local users, or username@domain for remote users.
   */
  val acct: String,
  /**
   * The location of the user's profile page.
   */
  val url: String,
  /**
   * The profile's display name.
   */
  @SerialName("display_name") val displayName: String,
  /**
   * The profile's bio / description.
   * Type: String (HTML)
   */
  val note: String,
  /**
   * An image icon that is shown next to statuses and in the profile.
   * Type: string (URL)
   */
  val avatar: String,
  /**
   * A static version of the avatar.
   * Equal to avatar if its value is a static image; different if avatar is an animated GIF.
   * Type: string (URL)
   */
  @SerialName("avatar_static") val avatarStatic: String,
  /**
   * An image banner that is shown above the profile and in profile cards.
   * Type: string (URL)
   */
  val header: String,
  /**
   * A static version of the header.
   * Equal to header if its value is a static image; different if header is an animated GIF.
   * Type: string (URL)
   */
  @SerialName("header_static") val headerStatic: String,
  /**
   * Whether the account manually approves follow requests.
   */
  val locked: Boolean,
  /**
   * Custom emoji entities to be used when rendering the profile.
   * If none, an empty array will be returned.
   */
  val emojis: List<Emoji>,
  /**
   * Whether the account has opted into discovery features such as the profile directory.
   */
  val discoverable: Boolean,
  /**
   * When the account was created.
   * Type: String (ISO 8601 Datetime)
   */
  @SerialName("created_at") val createdAt: String,
  /**
   * When the most recent status was posted.
   * Type: String (ISO 8601 Datetime)
   */
  @SerialName("last_status_at") val lastStatusAt: String,
  /**
   * How many statuses are attached to this account.
   * @precision int
   */
  @SerialName("statuses_count") val statusesCount: Int,
  /**
   * The reported followers of this profile.
   * @precision int
   */
  @SerialName("followers_count") val followersCount: Int,
  /**
   * The reported follows of this profile.
   * @precision int
   */
  @SerialName("following_count") val followingCount: Int,
  /**
   * Indicates that the profile is currently inactive and that its user has moved to a new account.
   */
  val moved: Account? = null,
  /**
   * Additional metadata attached to a profile as name-value pairs.
   */
  val fields: List<Field>? = null,
  /**
   * A presentational flag.
   * Indicates that the account may perform automated actions, may not be monitored, or identifies as a robot.
   */
  val bot: Boolean? = null,
  /**
   * An extra entity to be used with API methods to verify credentials and update credentials.
   * @link https://docs.joinmastodon.org/methods/accounts/#verify-account-credentials
   *    * 
   * @link https://docs.joinmastodon.org/methods/accounts/#update-account-credentials
   */
  val source: Source? = null,
  /**
   * An extra entity returned when an account is suspended.
   */
  val suspended: Boolean? = null,
  /**
   * When a timed mute will expire, if applicable.
   * Type: String (ISO 8601 Datetime)
   */
  @SerialName("mute_expires_at") val muteExpiresAt: Boolean? = null
)
