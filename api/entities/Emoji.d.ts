/**
 * Represents a custom emoji.
 * @see https://docs.joinmastodon.org/entities/emoji/
 */
export type Emoji = {

  // Required Attributes

  /**
   * Description: The name of the custom emoji.
   * Type: String
   * Version history: Added in 2.0.0
   */
  shortcode: string;

  /**
   * Description: A link to the custom emoji.
   * Type: String (URL)
   * Version history: Added in 2.0.0
   */
  url: string;

  /**
   * Description: A link to a static copy of the custom emoji.
   * Type: String (URL)
   * Version history: Added in 2.0.0
   */
  static_url: string;

  /**
   * Description: Whether this Emoji should be visible in the picker or unlisted.
   * Type: Boolean
   * Version history: Added in 2.0.0
   */
  visible_in_picker: boolean;

  // Optional Attributes

  /**
   * Description: Used for sorting custom emoji in the picker.
   * Type: String
   * Version history: Added in 3.0.0
   */
  category?: string;
}