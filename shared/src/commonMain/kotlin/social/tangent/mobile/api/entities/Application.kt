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
 * Represents an application that interfaces with the REST API to access accounts or post statuses.
 * @see https://docs.joinmastodon.org/entities/application/
 */
@Serializable
data class Application(
  /**
   * Description: The name of your application.
   * Type: String
   * Version history: Added in 0.9.9
   */
  val name: String,
  /**
   * Description: The website associated with your application.
   * Type: String (URL)
   * Version history: Added in 0.9.9
   */
  val website: String? = null,
  /**
   * Description: Used for Push Streaming API.
   * Returned with POST /api/v1/apps.
   * Equivalent to PushSubscription#server_key
   * Type: String
   * Version history: Added in 2.8.0
   */
  @SerialName("vapid_key") val vapidKey: String? = null,
  /**
   * Description: Client ID key, to be used for obtaining OAuth tokens
   * Type: String
   * Version history: Added in 0.9.9
   */
  @SerialName("client_id") val clientId: String? = null,
  /**
   * Description: Client secret key, to be used for obtaining OAuth tokens
   * Type: String
   * Version history: Added in 0.9.9
   */
  @SerialName("client_secret") val clientSecret: String? = null
)
