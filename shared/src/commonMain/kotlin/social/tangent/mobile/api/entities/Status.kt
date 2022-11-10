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
import social.tangent.mobile.api.entities.Attachment
import social.tangent.mobile.api.entities.Mention
import social.tangent.mobile.api.entities.Tag
import social.tangent.mobile.api.entities.Application
import social.tangent.mobile.api.entities.Status

@Serializable
data class Status(
  /**
   * The ID of the status
   */
  val id: String,
  /**
   * A Fediverse-unique resource ID
   */
  val uri: String,
  /**
   * URL to the status page (can be remote)
   */
  val url: String,
  /**
   * The [Account] which posted the status
   */
  val account: Account,
  /**
   * null or the ID of the status it replies to
   */
  @SerialName("in_reply_to_id") val inReplyToId: String? = null,
  /**
   * null or the ID of the account it replies to
   */
  @SerialName("in_reply_to_account_id")
  val inReplyToAccountId: String? = null,
  /**
   * null or the reblogged [Status]
   */
  val reblog: Status? = null,
  /**
   * Body of the status; this will contain HTML (remote HTML already sanitized)
   */
  val content: String,
  /**
   * The time the status was created
   */
  @SerialName("created_at") val createdAt: String,
  /**
   * The number of reblogs for the status
   */
  @SerialName("reblogs_count") val reblogsCount: Double,
  /**
   * The number of favourites for the status
   */
  @SerialName("favourites_count") val favouritesCount: Double,
  /**
   * Whether the authenticated user has reblogged the status
   */
  val reblogged: Boolean,
  /**
   * Whether the authenticated user has favourited the status
   */
  val favourited: Boolean,
  /**
   * Whether media attachments should be hidden by default
   */
  val sensitive: Boolean,
  /**
   * If not empty, warning text that should be displayed before the actual content
   */
  @SerialName("spoiler_text") val spoilerText: String,
  /**
   * One of: public, unlisted, private, direct
   */
  val visibility: Visibility,
  /**
   * An array of [Attachments]s
   */
  @SerialName("media_attachments") val mediaAttachments: List<Attachment>,
  /**
   * An array of [Mentions]
   */
  val mentions: List<Mention>,
  /**
   * An array of [Tag]s
   */
  val tags: List<Tag>,
  /**
   * [Application] from which the status was posted
   */
  val application: Application
) {
  @Serializable
  enum class Visibility(
    val serialName: String
  ) {
    @SerialName("public") PUBLIC("public"),
    @SerialName("unlisted") UNLISTED("unlisted"),
    @SerialName("private") PRIVATE("private"),
    @SerialName("direct") DIRECT("direct");
  }
}
