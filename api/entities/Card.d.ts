/**
 * Represents a rich preview card that is generated using OpenGraph tags from a URL.
 * @see https://docs.joinmastodon.org/entities/card/
 */
export type Card = {

  // Base Attributes

  /**
   * Description: Location of linked resource.
   * Type: String (URL)
   * Version history:
   * 1.0.0 - added
   */
  url: string;

  /**
   * Description: Title of linked resource.
   * Type: String
   * Version history:
   * 1.0.0 - added
   */
  title: string;

  /**
   * Description: Description of preview.
   * Type: String
   * Version history:
   * 1.0.0 - added
   */
  description: string;

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
  type: "link" | "photo" | "video" | "rich";

  // Optional Attributes

  /**
   * Description: The author of the original resource.
   * Type: String
   * Version history:
   * 1.3.0 - added
   */
  author_name?: string;

  /**
   * Description: A link to the author of the original resource.
   * Type: String (URL)
   * Version history:
   * 1.3.0 - added
   */
  author_url?: string;

  /**
   * Description: The provider of the original resource.
   * Type: String
   * Version history:
   * 1.3.0 - added
   */
  provider_name?: string;

  /**
   * Description: A link to the provider of the original resource.
   * Type: String (URL)
   * Version history:
   * 1.3.0 - added
   */
  provider_url?: string;

  /**
   * Description: HTML to be used for generating the preview card.
   * Type: String (HTML)
   * Version history:
   * 1.3.0 - added
   */
  html?: string;

  /**
   * Description: Width of preview, in pixels.
   * Type: Number
   * Version history:
   * 1.3.0 - added
   *
   * @precision long
   */
  width?: number;

  /**
   * Description: Height of preview, in pixels.
   * Type: Number
   * Version history:
   * 1.3.0 - added
   *
   * @precision long
   */
  height?: number;

  /**
   * Description: Preview thumbnail.
   * Type: String (URL)
   * Version history:
   * 1.0.0 - added
   */
  image?: string;

  /**
   * Description: Used for photo embeds, instead of custom html.
   * Type: String (URL)
   * Version history:
   * 2.1.0 - added
   */
  embed_url?: string;

  /**
   * Description: A hash computed by the BlurHash algorithm, for generating colorful preview thumbnails when media has not been downloaded yet.
   * Type: String
   * Version history:
   * 3.2.0 - added
   */
  blurhash?: string;
}