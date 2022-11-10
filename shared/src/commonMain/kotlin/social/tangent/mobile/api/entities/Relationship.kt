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
data class Relationship(
  /**
   * Target account id
   */
  val id: String,
  /**
   * Whether the user is currently following the account
   */
  val following: Boolean,
  /**
   * Whether the user is currently being followed by the account
   */
  @SerialName("followed_by") val followedBy: Boolean,
  /**
   * Whether the user is currently blocking the account
   */
  val blocking: Boolean,
  /**
   * Whether the user is currently muting the account
   */
  val muting: Boolean,
  /**
   * Whether the user has requested to follow the account
   */
  val requested: Boolean
)
