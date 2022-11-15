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
   * The account id header.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   */
  id:              string;

  /**
   * The username of the account, not including domain.
   */
  username:        string;

  /**
   * The Webfinger account URI.
   * Equal to username for local users, or username@domain for remote users.
   */
  acct:            string;

  /**
   * The location of the user's profile page.
   */
  url:             string;

  // Display Attributes

  /**
   * The profile's display name.
   */
  display_name:    string;

  /**
   * The profile's bio / description.
   * Type: String (HTML)
   */
  note:            string;

  /**
   * An image icon that is shown next to statuses and in the profile.
   * Type: string (URL)
   */
  avatar:          string;

  /**
   * A static version of the avatar.
   * Equal to avatar if its value is a static image; different if avatar is an animated GIF.
   * Type: string (URL)
   */
  avatar_static:   string;

  /**
   * An image banner that is shown above the profile and in profile cards.
   * Type: string (URL)
   */
  header:          string;

  /**
   * A static version of the header.
   * Equal to header if its value is a static image; different if header is an animated GIF.
   * Type: string (URL)
   */
  header_static:   string;

  /**
   * Whether the account manually approves follow requests.
   */
  locked:          boolean;

  /**
   * Custom emoji entities to be used when rendering the profile.
   * If none, an empty array will be returned.
   */
  emojis:          Emoji[];

  /**
   * Whether the account has opted into discovery features such as the profile directory.
   */
  discoverable:    boolean;

  // Statistical Attributes

  /**
   * When the account was created.
   * Type: String (ISO 8601 Datetime)
   */
  created_at:      string;

  /**
   * When the most recent status was posted.
   * Type: String (ISO 8601 Datetime)
   */
  last_status_at:  string;

  /**
   * How many statuses are attached to this account.
   * @precision int
   */
  statuses_count:  number;

  /**
   * The reported followers of this profile.
   * @precision int
   */
  followers_count: number;

  /**
   * The reported follows of this profile.
   * @precision int
   */
  following_count: number;

  // Optional Attributes

  /**
   * Indicates that the profile is currently inactive and that its user has moved to a new account.
   */
  moved?:          Account;

  /**
   * Additional metadata attached to a profile as name-value pairs.
   */
  fields?:         Field[];

  /**
   * A presentational flag.
   * Indicates that the account may perform automated actions, may not be monitored, or identifies as a robot.
   */
  bot?:            boolean;

  /**
   * An extra entity to be used with API methods to verify credentials and update credentials.
   * @link https://docs.joinmastodon.org/methods/accounts/#verify-account-credentials
   * @link https://docs.joinmastodon.org/methods/accounts/#update-account-credentials
   */
  source?:         Source;

  /**
   * An extra entity returned when an account is suspended.
   */
  suspended?:      boolean;

  /**
   * When a timed mute will expire, if applicable.
   * Type: String (ISO 8601 Datetime)
   */
  mute_expires_at?: boolean;
}
