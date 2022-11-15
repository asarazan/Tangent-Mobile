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

/**
 * Represents a poll attached to a status.
 * @see https://docs.joinmastodon.org/entities/poll/
 */
@Serializable
data class Poll(
  /**
   * Description: The ID of the poll in the database.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history: Added in 2.8.0
   */
  val id: String,
  /**
   * Description: When the poll ends.
   * Type: String (ISO 8601 Datetime), or null if the poll does not end
   * Version history: Added in 2.8.0
   */
  @SerialName("expires_at") val expiresAt: String,
  /**
   * Description: Is the poll currently expired?
   * Type: Boolean
   * Version history: Added in 2.8.0
   */
  val expired: Boolean,
  /**
   * Description: Does the poll allow multiple-choice answers?
   * Type: Boolean
   * Version history: Added in 2.8.0
   */
  val multiple: Boolean,
  /**
   * Description: How many votes have been received.
   * Type: Number
   * Version history: Added in 2.8.0
   * @precision long
   */
  @SerialName("votes_count") val votesCount: Long,
  /**
   * Description: How many unique accounts have voted on a multiple-choice poll.
   * Type: Number, or null if multiple is false.
   * Version history: Added in 2.8.0
   */
  @SerialName("voters_count") val votersCount: Double? = null,
  /**
   * Description: When called with a user token, has the authorized user voted?
   * Type: Boolean, or null if no current user
   * Version history: Added in 2.8.0
   */
  val voted: Boolean? = null,
  /**
   * Description: When called with a user token, which options has the authorized user chosen?
   * Contains an array of index values for options.
   * Type: Array of Number, or null if no current user
   * Version history: Added in 2.8.0
   * @precision long
   */
  @SerialName("own_votes") val ownVotes: List<Long>? = null,
  /**
   * Description: Possible answers for the poll.
   * Type: Array of Hash
   * Version history: Added in 2.8.0
   */
  val options: List<OptionsItem>,
  /**
   * Description: Custom emoji to be used for rendering poll options.
   * Type: Array of Emoji
   * Version history: Added in 2.8.0
   */
  val emojis: List<Emoji>
) {
  @Serializable
  data class OptionsItem(
    /**
     * The text value of the poll option. String.
     */
    val title: String,
    /**
     * The number of received votes for this option.
     * Number, or null if results are not published yet.
     */
    @SerialName("votes_count") val votesCount: Double? = null
  )
}
