/**
 * Represents a user's preferences.
 * @see https://docs.joinmastodon.org/entities/preferences/
 */
export type Preferences = {

  // Attributes

  /**
   * Description: Default visibility for new posts. Equivalent to Source#privacy.
   * Type: String (Enumerable, oneOf)
   * public = Public post
   * unlisted = Unlisted post
   * private = Followers-only post
   * direct = Direct post
   * Version history: Added in 2.8.0
   */
  "posting:default:visibility": "public" | "unlisted" | "private" | "direct";

  /**
   * Description: Default sensitivity flag for new posts. Equivalent to Source#sensitive.
   * Type: Boolean
   * Version history: Added in 2.8.0
   */
  "posting:default:sensitive": boolean;

  /**
   * Description: Default language for new posts. Equivalent to Source#language
   * Type: String (ISO 639-1 language two-letter code), or null
   * Version history: Added in 2.8.0
   */
  "posting:default:language": string;

  /**
   * Description: Whether media attachments should be automatically displayed or blurred/hidden.
   * Type: String (Enumerable, oneOf)
   * default = Hide media marked as sensitive
   * show_all = Always show all media by default, regardless of sensitivity
   * hide_all = Always hide all media by default, regardless of sensitivity
   * Version history: Added in 2.8.0
   */
  "reading:expand:media": "default" | "show_all" | "hide_all";

  /**
   * Description: Whether CWs should be expanded by default.
   * Type: Boolean
   * Version history: Added in 2.8.0
   */
  "reading:expand:spoilers": boolean;
};