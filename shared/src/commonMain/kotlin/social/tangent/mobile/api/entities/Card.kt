/****************************************************
** DO NOT EDIT THIS FILE BY HAND!                  **
** This file was automatically generated by Martok **
** More info at https://github.com/asarazan/martok **
*****************************************************/
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
 * Represents a rich preview card that is generated using OpenGraph tags from a URL.
 * @see https://docs.joinmastodon.org/entities/card/
 */
@Serializable
data class Card(

  /**
   * Description: Location of linked resource.
   * Type: String (URL)
   * Version history:
   * 1.0.0 - added
   */
  val url: String,

  /**
   * Description: Title of linked resource.
   * Type: String
   * Version history:
   * 1.0.0 - added
   */
  val title: String,

  /**
   * Description: Description of preview.
   * Type: String
   * Version history:
   * 1.0.0 - added
   */
  val description: String,

  /**
   * Description: The type of the preview card.
   * Type: String (Enumerable, oneOf)
   * link = Link OEmbed
   * photo = Photo OEmbed
   * video = Video OEmbed
   * rich = iframe OEmbed. Not currently accepted, so won't show up in practice.
   * Version history:
   * 1.3.0 - added
   */
  val type: Type,

  /**
   * Description: The author of the original resource.
   * Type: String
   * Version history:
   * 1.3.0 - added
   */
  @SerialName("author_name")
  val authorName: String? = null,

  /**
   * Description: A link to the author of the original resource.
   * Type: String (URL)
   * Version history:
   * 1.3.0 - added
   */
  @SerialName("author_url")
  val authorUrl: String? = null,

  /**
   * Description: The provider of the original resource.
   * Type: String
   * Version history:
   * 1.3.0 - added
   */
  @SerialName("provider_name")
  val providerName: String? = null,

  /**
   * Description: A link to the provider of the original resource.
   * Type: String (URL)
   * Version history:
   * 1.3.0 - added
   */
  @SerialName("provider_url")
  val providerUrl: String? = null,

  /**
   * Description: HTML to be used for generating the preview card.
   * Type: String (HTML)
   * Version history:
   * 1.3.0 - added
   */
  val html: String? = null,

  /**
   * Description: Width of preview, in pixels.
   * Type: Number
   * Version history:
   * 1.3.0 - added
   * @precision long
   */
  val width: Long? = null,

  /**
   * Description: Height of preview, in pixels.
   * Type: Number
   * Version history:
   * 1.3.0 - added
   * @precision long
   */
  val height: Long? = null,

  /**
   * Description: Preview thumbnail.
   * Type: String (URL)
   * Version history:
   * 1.0.0 - added
   */
  val image: String? = null,

  /**
   * Description: Used for photo embeds, instead of custom html.
   * Type: String (URL)
   * Version history:
   * 2.1.0 - added
   */
  @SerialName("embed_url")
  val embedUrl: String? = null,

  /**
   * Description: A hash computed by the BlurHash algorithm, for generating colorful preview thumbnails when media has not been downloaded yet.
   * Type: String
   * Version history:
   * 3.2.0 - added
   */
  val blurhash: String? = null
) {

  @Serializable
  enum class Type(
    val serialName: String
  ) {
    @SerialName("link") LINK("link"),
    @SerialName("photo") PHOTO("photo"),
    @SerialName("video") VIDEO("video"),
    @SerialName("rich") RICH("rich");
  }
}
