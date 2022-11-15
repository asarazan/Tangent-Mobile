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

/**
 * Represents the software instance of Mastodon running on this domain.
 * @see https://docs.joinmastodon.org/entities/instance/
 */
@Serializable
data class Instance(
  /**
   * Description: The domain name of the instance.
   * Type: String
   * Version history: Added in 1.1.0
   */
  val uri: String,
  /**
   * Description: The title of the website.
   * Type: String
   * Version history: Added in 1.1.0
   */
  val title: String,
  /**
   * Description: Admin-defined description of the Mastodon site.
   * Type: String
   * Version history: Added in 1.1.0
   */
  val description: String,
  /**
   * Description: A shorter description defined by the admin.
   * Type: String
   * Version history: Added in 2.9.2
   */
  @SerialName("short_description") val shortDescription: String,
  /**
   * Description: An email that may be contacted for any inquiries.
   * Type: String
   * Version history: Added in 1.1.0
   */
  val email: String,
  /**
   * Description: The version of Mastodon installed on the instance.
   * Type: String
   * Version history: Added in 1.3.0
   */
  val version: String,
  /**
   * Description: Primary languages of the website and its staff.
   * Type: Array of String (ISO 639 Part 1-5 language codes)
   * Version history: Added in 2.3.0
   */
  val languages: List<String>,
  /**
   * Description: Whether registrations are enabled.
   * Type: Boolean
   * Version history: Added in 2.7.2
   */
  val registrations: Boolean,
  /**
   * Description: Whether registrations require moderator approval.
   * Type: Boolean
   * Version history: Added in 2.9.2
   */
  @SerialName("approval_required") val approvalRequired: Boolean,
  /**
   * Description: Whether invites are enabled.
   * Type: Boolean
   * Version history: Added in 3.1.4
   */
  @SerialName("invites_enabled") val invitesEnabled: Boolean,
  /**
   * Description: URLs of interest for clients apps.
   * Type: Hash (streaming_api)
   * Version history: Added in 1.4.2
   * 
   * urls[streaming_api]
   * Websockets address for push streaming. String (URL).
   */
  val urls: Urls,
  /**
   * Description: Statistics about how much information the instance contains.
   * Type: Hash (user_count, status_count, domain_count)
   * Version history: Added in 1.6.0
   */
  val stats: Stats,
  /**
   * Description: Banner image for the website.
   * Type: String (URL) or null
   * Version history: Added in 1.6.1
   */
  val thumbnail: String? = null,
  /**
   * Description: A user that can be contacted, as an alternative to email.
   * Type: Account or null
   * Version history: Added in 2.3.0
   */
  @SerialName("contact_account") val contactAccount: Account? = null
) {
  @Serializable
  data class Urls(
    @SerialName("streaming_api") val streamingApi: String
  )


  @Serializable
  data class Stats(
    /**
     * Users registered on this instance. Number.
     * @precision int
     */
    @SerialName("user_count") val userCount: Int,
    /**
     * Statuses authored by users on instance. Number.
     * @precision int
     */
    @SerialName("status_count") val statusCount: Int,
    /**
     * Domains federated with this instance. Number.
     * @precision int
     */
    @SerialName("domain_count") val domainCount: Int
  )
}
