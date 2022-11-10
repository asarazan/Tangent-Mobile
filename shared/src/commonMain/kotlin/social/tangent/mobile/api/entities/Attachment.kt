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
data class Attachment(
  /**
   * ID of the attachment
   */
  val id: String,
  /**
   * One of: "image", "video", "gifv"
   */
  val type: Type,
  /**
   * URL of the locally hosted version of the image
   */
  val url: String,
  /**
   * For remote images, the remote URL of the original image
   */
  @SerialName("remote_url") val remoteUrl: String,
  /**
   * URL of the preview image
   */
  @SerialName("preview_url") val previewUrl: String,
  /**
   * Shorter URL for the image, for insertion into text (only present on local images)
   */
  @SerialName("text_url") val textUrl: String
) {
  @Serializable
  enum class Type(
    val serialName: String
  ) {
    @SerialName("image") IMAGE("image"),
    @SerialName("video") VIDEO("video"),
    @SerialName("gifv") GIFV("gifv");
  }
}
