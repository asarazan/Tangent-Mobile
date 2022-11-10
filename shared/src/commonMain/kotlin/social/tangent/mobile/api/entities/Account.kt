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

@Serializable
data class Account(
  /**
   * The ID of the account
   * @precision long
   */
  val id: Long,
  /**
   * The username of the account
   */
  val username: String,
  /**
   * Equals username for local users, includes @domain for remote ones
   */
  val acct: String,
  /**
   * The account's display name
   */
  @SerialName("display_name") val displayName: String,
  /**
   * Boolean for when the account cannot be followed without waiting for approval first
   */
  val locked: Boolean,
  /**
   * The time the account was created
   */
  @SerialName("created_at") val createdAt: String,
  /**
   * The number of followers for the account
   * @precision int
   */
  @SerialName("followers_count") val followersCount: Int,
  /**
   * The number of accounts the given account is following
   * @precision int
   */
  @SerialName("following_count") val followingCount: Int,
  /**
   * The number of statuses the account has made
   * @precision int
   */
  @SerialName("statuses_count") val statusesCount: Int,
  /**
   * Biography of user
   */
  val note: String,
  /**
   * URL of the user's profile page (can be remote)
   */
  val url: String,
  /**
   * URL to the avatar image
   */
  val avatar: String,
  /**
   * URL to the avatar static image (gif)
   */
  @SerialName("avatar_static") val avatarStatic: String,
  /**
   * URL to the header image
   */
  val header: String,
  /**
   * URL to the header static image (gif)
   */
  @SerialName("header_static") val headerStatic: String
)
