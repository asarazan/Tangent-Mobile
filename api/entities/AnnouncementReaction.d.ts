/**
 * Represents an emoji reaction to an Announcement.
 * @see https://docs.joinmastodon.org/entities/announcementreaction/
 */
export type AnnouncementReaction = {

  // Base Attributes

  /**
   * Description: The emoji used for the reaction.
   * Either a unicode emoji, or a custom emoji's shortcode.
   * Type: String
   * Version history:
   * 3.1.0 - added
   */
  name: string;

  /**
   * Description: The total number of users who have added this reaction.
   * Type: Number
   * Version history:
   * 3.1.0 - added
   *
   * @precision long
   */
  count: number;

  /**
   * Description: Whether the authorized user has added this reaction to the announcement.
   * Type: Boolean
   * Version history:
   * 3.1.0 - added
   */
  me: boolean;

  // Custom Emoji Attributes

  /**
   * Description: A link to the custom emoji.
   * Type: String (URL)
   * Version history:
   * 3.1.0 - added
   */
  url: string;

  /**
   * Description: A link to a non-animated version of the custom emoji.
   * Type: String (URL)
   * Version history:
   * 3.1.0 - added
   */
  static_url: string;
};