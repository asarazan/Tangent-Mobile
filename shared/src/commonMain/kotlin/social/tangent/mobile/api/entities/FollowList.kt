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
 * Represents a list of some users that the authenticated user follows.
 * @see https://docs.joinmastodon.org/entities/list/
 */
@Serializable
data class FollowList(

  /**
   * Description: The internal database ID of the list.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history:
   * 2.1.0 - added
   */
  val id: String,

  /**
   * Description: The user-defined title of the list.
   * Type: String
   * Version history:
   * 2.1.0 - added
   */
  val title: String,

  /**
   * Description: The user-defined title of the list.
   * Type: String (Enumerable oneOf)
   * followed = Show replies to any followed user
   * list = Show replies to members of the list
   * none = Show replies to no one
   * Version history:
   * 3.3.0 - added
   */
  @SerialName("replies_policy")
  val repliesPolicy: RepliesPolicy
) {

  @Serializable
  enum class RepliesPolicy(

    val serialName: String
  ) {
    @SerialName("followed") FOLLOWED("followed"),
    @SerialName("list") LIST("list"),
    @SerialName("none") NONE("none");
  }
}
