import {AnnouncementReaction} from "./AnnouncementReaction";

/**
 * Represents an announcement set by an administrator.
 * @see https://docs.joinmastodon.org/entities/announcement/
 */
export type Announcement = {

  // Base Attributes

  /**
   * Description: The announcement id.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history:
   * 3.1.0 - added
   */
  id: string;

  /**
   * Description: The content of the announcement.
   * Type: String
   * Version history:
   * 3.1.0 - added
   */
  text: string;

  /**
   * Description: Whether the announcement is currently active.
   * Type: Boolean
   * Version history:
   * 3.1.0 - added
   */
  published: boolean;

  /**
   * Description: Whether the announcement has a start/end time.
   * Type: Boolean
   * Version history:
   * 3.1.0 - added
   */
  all_day: boolean;

  /**
   * Description: When the announcement was created.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.1.0 - added
   *
   * @DateTime
   */
  created_at: string;

  /**
   * Description: When the announcement was last updated.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.1.0 - added
   *
   * @DateTime
   */
  updated_at: string;

  /**
   * Description: Whether the announcement has been read by the user.
   * Type: Boolean
   * Version history:
   * 3.1.0 - added
   */
  read: boolean;

  /**
   * Description: Emoji reactions attached to the announcement.
   * Type: Array of AnnouncementReaction
   * Version history:
   * 3.1.0 - added
   */
  reactions: AnnouncementReaction[];

  // Optional Attributes

  /**
   * Description: When the future announcement was scheduled.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.1.0 - added
   *
   * @DateTime
   */
  scheduled_at: string;

  /**
   * Description: When the future announcement will start.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.1.0 - added
   *
   * @DateTime
   */
  starts_at: string;

  /**
   * Description: When the future announcement will end.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.1.0 - added
   *
   * @DateTime
   */
  ends_at: string;
};