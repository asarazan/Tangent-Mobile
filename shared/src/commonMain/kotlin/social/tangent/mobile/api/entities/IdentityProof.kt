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
 * Represents a proof from an external identity provider.
 * @see https://docs.joinmastodon.org/entities/identityproof/
 */
@Serializable
data class IdentityProof(

  /**
   * Description: The name of the identity provider.
   * Type: String
   * Version history: Added in 2.8.0
   */
  val provider: String,

  /**
   * Description: The account owner's username on the identity provider's service.
   * Type: String
   * Version history: Added in 2.8.0
   */
  @SerialName("provider_username") val providerUsername: String,

  /**
   * Description: The account owner's profile URL on the identity provider.
   * Type: String (URL)
   * Version history: Added in 2.8.0
   */
  @SerialName("profile_url") val profileUrl: String,

  /**
   * Description: A link to a statement of identity proof, hosted by the identity provider.
   * Type: String (URL)
   * Version history: Added in 2.8.0
   */
  @SerialName("proof_url") val proofUrl: String,

  /**
   * Description: When the identity proof was last updated.
   * Type: String (ISO 8601 Datetime)
   * Version history: Added in 2.8.0
   */
  @SerialName("updated_at") val updatedAt: String
)
