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
import social.tangent.mobile.api.entities.Emoji
import social.tangent.mobile.api.entities.Poll
import social.tangent.mobile.api.entities.Card
import social.tangent.mobile.api.entities.Status

/**
 * Represents a status posted by an account.
 * @see https://docs.joinmastodon.org/entities/status/
 */
@Serializable
data class Status(
  /**
   * Description: ID of the status in the database.
   * Type: String (cast from an integer but not guaranteed to be a number)
   * Version history: Added in 0.1.0
   */
  val id: String,
  /**
   * Description: URI of the status used for federation.
   * Type: String
   * Version history: Added in 0.1.0
   */
  val uri: String,
  /**
   * Description: The date when this status was created.
   * Type: String (ISO 8601 Datetime)
   * Version history: Added in 0.1.0
   */
  @SerialName("created_at") val createdAt: String,
  /**
   * Description: The account that authored this status.
   * Type: Account
   * Version history: Added in 0.1.0
   */
  val account: Account,
  /**
   * Description: HTML-encoded status content.
   * Type: String (HTML)
   * Version history: Added in 0.1.0
   */
  val content: String,
  /**
   * Description: Visibility of this status.
   * Type: String (Enumerable oneOf)
   * public = Visible to everyone, shown in public timelines.
   * unlisted = Visible to public, but not included in public timelines.
   * private = Visible to followers only, and to any mentioned users.
   * direct = Visible only to mentioned users.
   * 
   * Version history: Added in 0.9.9
   */
  val visibility: Visibility,
  /**
   * Description: Is this status marked as sensitive content?
   * Type: Boolean
   * Version history: Added in 0.9.9
   */
  val sensitive: Boolean,
  /**
   * Description: Subject or summary line, below which status content is collapsed until expanded.
   * Type: String
   * Version history: Added in 1.0.0
   */
  @SerialName("spoiler_text") val spoilerText: String,
  /**
   * Description: Media that is attached to this status.
   * Type: Array of Attachment
   * Version history: Added in 0.6.0
   */
  @SerialName("media_attachments") val mediaAttachments: List<Attachment>,
  /**
   * Description: The application used to post this status.
   * Type: Application
   * Version history: Added in 0.9.9
   */
  val application: Application,
  /**
   * Description: Mentions of users within the status content.
   * Type: Array of Mention
   * Version history: Added in 0.6.0
   */
  val mentions: List<Mention>,
  /**
   * Description: Hashtags used within the status content.
   * Type: Array of Tag
   * Version history: Added in 0.9.0
   */
  val tags: List<Tag>,
  /**
   * Description: Custom emoji to be used when rendering status content.
   * Type: Array of Emoji
   * Version history: Added in 2.0.0
   */
  val emojis: List<Emoji>,
  /**
   * Description: How many boosts this status has received.
   * Type: Number
   * Version history: Added in 0.1.0
   * @precision long
   */
  @SerialName("reblogs_count") val reblogsCount: Long,
  /**
   * Description: How many favourites this status has received.
   * Type: Number
   * Version history: Added in 0.1.0
   * @precision long
   */
  @SerialName("favourites_count") val favouritesCount: Long,
  /**
   * Description: How many replies this status has received.
   * Type: Number
   * Version history: Added in 2.5.0
   * @precision long
   */
  @SerialName("replies_count") val repliesCount: Long,
  /**
   * Description: A link to the status's HTML representation.
   * Type: String (URL)
   * Version history: Added in 0.1.0
   */
  val url: String? = null,
  /**
   * Description: ID of the status being replied.
   * Type: String (cast from an integer but not guaranteed to be a number)
   * Version history: Added in 0.1.0
   */
  @SerialName("in_reply_to_id") val inReplyToId: String? = null,
  /**
   * Description: ID of the account being replied to.
   * Type: String (cast from an integer but not guaranteed to be a number)
   * Version history: Added in 1.0.0
   */
  @SerialName("in_reply_to_account_id")
  val inReplyToAccountId: String? = null,
  /**
   * Description: The status being reblogged.
   * Type: Status
   * Version history: Added in 0.1.0
   */
  val reblog: Status? = null,
  /**
   * Description: The poll attached to the status.
   * Type: Poll
   * Version history: Added in 2.8.0
   */
  val poll: Poll? = null,
  /**
   * Description: Preview card for links included within status content.
   * Type: Card
   * Version history: Added in 2.6.0
   */
  val card: Card? = null,
  /**
   * Description: Primary language of this status.
   * Type: String (ISO 639 Part 1 two-letter language code)
   * Version history: Added in 1.4.0
   */
  val language: String? = null,
  /**
   * Description: Plain-text source of a status.
   * Returned instead of content when status is deleted,
   * so the user may redraft from the source text without the client having to reverse-engineer the original text from the HTML content.
   * Type: String
   * Version history: Added in 2.9.0
   */
  val text: String? = null,
  /**
   * Description: Have you favourited this status?
   * Type: Boolean
   * Version history: Added in 0.1.0
   */
  val favourited: Boolean? = null,
  /**
   * Description: Have you boosted this status?
   * Type: Boolean
   * Version history: Added in 0.1.0
   */
  val reblogged: Boolean? = null,
  /**
   * Description: Have you muted notifications for this status's conversation?
   * Type: Boolean
   * Version history: Added in 1.4.0
   */
  val muted: Boolean? = null,
  /**
   * Description: Have you bookmarked this status?
   * Type: Boolean
   * Version history: Added in 3.1.0
   */
  val bookmarked: Boolean? = null,
  /**
   * Description: Have you pinned this status? Only appears if the status is pinnable.
   * Type: Boolean
   * Version history: Added in 1.6.0
   */
  val pinned: Boolean? = null
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
