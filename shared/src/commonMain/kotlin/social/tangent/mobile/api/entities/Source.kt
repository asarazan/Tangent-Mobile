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

import social.tangent.mobile.api.entities.Field

/**
 * Represents display or publishing preferences of user's own account.
 * Returned as an additional entity when verifying and updated credentials, as an attribute of Account.
 * @see https://docs.joinmastodon.org/entities/source/
 */
@Serializable
data class Source(
  /**
   * Profile bio.
   */
  val note: String,
  /**
   * Metadata about the account.
   */
  val fields: List<Field>,
  /**
   * The default post privacy to be used for new statuses.
   */
  val privacy: Privacy? = null,
  /**
   * Whether new statuses should be marked sensitive by default.
   */
  val sensitive: Boolean? = null,
  /**
   * The default posting language for new statuses.
   * Type: String (ISO 639-1 language two-letter code)
   */
  val language: String? = null,
  /**
   * The number of pending follow requests.
   * @precision int
   */
  @SerialName("follow_requests_count")
  val followRequestsCount: Int? = null
) {
  @Serializable
  enum class Privacy(
    val serialName: String
  ) {
    @SerialName("public") PUBLIC("public"),
    @SerialName("unlisted") UNLISTED("unlisted"),
    @SerialName("private") PRIVATE("private"),
    @SerialName("direct") DIRECT("direct");
  }
}
