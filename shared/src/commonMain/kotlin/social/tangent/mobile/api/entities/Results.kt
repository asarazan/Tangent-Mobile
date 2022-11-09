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
import social.tangent.mobile.api.entities.Status

@Serializable
data class Results(
  /**
   * An array of matched [Account]s
   */
  val accounts: List<Account>,
  /**
   * An array of matchhed [Status]es
   */
  val statuses: List<Status>,
  /**
   * An array of matched hashtags, as strings
   */
  val hashtags: List<String>
)
