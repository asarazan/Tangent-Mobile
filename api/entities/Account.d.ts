import {Emoji} from "./Emoji";
import {Field} from "./Field";
import {Source} from "./Source";

/**
 * Represents a user of Mastodon and their associated profile.
 * @see https://docs.joinmastodon.org/entities/account/
 */
export type Account = {

  // Base Attributes

  /**
   * Description: The account idheader.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history:
   * 0.1.0 - added
   */
  id: string;

  /**
   * Description: The username of the account, not including domain.
   * Type: String
   * Version history:
   * 0.1.0 - added
   */
  username: string;

  /**
   * Description: The Webfinger account URI.
   * Equal to username for local users, or username@domain for remote users.
   * Type: String
   * Version history:
   * 0.1.0 - added
   */
  acct: string;

  /**
   * Description: The location of the user's profile page.
   * Type: String (HTTPS URL)
   * Version history:
   * 0.1.0 - added
   */
  url: string;

  // Display Attributes

  /**
   * Description: The profile's display name.
   * Type: String
   * Version history:
   * 0.1.0 - added
   */
  display_name: string;

  /**
   * Description: The profile's bio / description.
   * Type: String (HTML)
   * Version history:
   * 0.1.0 - added
   */
  note: string;

  /**
   * Description: An image icon that is shown next to statuses and in the profile.
   * Type: String (URL)
   * Version history:
   * 0.1.0 - added
   */
  avatar: string;

  /**
   * Description: A static version of the avatar.
   * Equal to avatar if its value is a static image; different if avatar is an animated GIF.
   * Type: String (URL)
   * Version history:
   * 1.1.2 - added
   */
  avatar_static: string;

  /**
   * Description: An image banner that is shown above the profile and in profile cards.
   * Type: String (URL)
   * Version history:
   * 0.1.0 - added
   */
  header: string;

  /**
   * Description: A static version of the header.
   * Equal to header if its value is a static image; different if header is an animated GIF.
   * Type: String (URL)
   * Version history:
   * 1.1.2 - added
   */
  header_static: string;

  /**
   * Description: Whether the account manually approves follow requests.
   * Type: Boolean
   * Version history:
   * 0.1.0 - added
   */
  locked: boolean;

  /**
   * Description: Custom emoji entities to be used when rendering the profile. If none, an empty array will be returned.
   * Type: Array of Emoji
   * Version history:
   * 2.4.0 - added
   */
  emojis: Emoji[];

  /**
   * Description: Whether the account has opted into discovery features such as the profile directory.
   * Type: Boolean
   * Version history:
   * 3.1.0 - added
   */
  discoverable: boolean;

  // Statistical Attributes

  /**
   * Description: When the account was created.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 0.1.0 - added
   */
  created_at: string;

  /**
   * Description: When the most recent status was posted.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.0.0 - added
   * 3.1.0 - now returns date only, no time
   */
  last_status_at: string;

  /**
   * Description: How many statuses are attached to this account.
   * Type: Number
   * Version history:
   * 0.1.0 - added
   *
   * @precision long
   */
  statuses_count: number;

  /**
   * Description: The reported followers of this profile.
   * Type: Number
   * Version history:
   * 0.1.0 - added
   *
   * @precision long
   */
  followers_count: number;

  /**
   * Description: The reported follows of this profile.
   * Type: Number
   * Version history:
   * 0.1.0 - added
   *
   * @precision long
   */
  following_count: number;

  // Optional Attributes

  /**
   * Description: Indicates that the profile is currently inactive and that its user has moved to a new account.
   * Type: Account
   * Version history:
   * 2.1.0 - added
   */
  moved?: Account;

  /**
   * Description: Additional metadata attached to a profile as name-value pairs.
   * Type: Array of Field
   * Version history:
   * 2.4.0 - added
   */
  fields?: Field[];

  /**
   * Description: A presentational flag.
   * Indicates that the account may perform automated actions, may not be monitored, or identifies as a robot.
   * Type: Boolean
   * Version history:
   * 2.4.0 - added
   */
  bot?: boolean;

  /**
   * Description: An extra entity to be used with API methods to verify credentials and update credentials.
   * Type: Source
   * Version history:
   * 2.4.0 - added
   *
   * @link https://docs.joinmastodon.org/methods/accounts/#verify-account-credentials
   * @link https://docs.joinmastodon.org/methods/accounts/#update-account-credentials
   */
  source?: Source;

  /**
   * Description: An extra entity returned when an account is suspended.
   * Type: Boolean
   * Version history:
   * 3.3.0 - added
   */
  suspended?: boolean;

  /**
   * Description: When a timed mute will expire, if applicable.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 3.3.0 - added
   */
  mute_expires_at?: boolean;
}
