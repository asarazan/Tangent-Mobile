/**
 * Represents a mention of a user within the content of a status.
 * @see https://docs.joinmastodon.org/entities/mention/
 */
export type Mention = {

  // Required Attributes

  /**
   * Description: The account id of the mentioned user.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history: Added in 0.6.0
   */
  id: string;

  /**
   * Description: The username of the mentioned user.
   * Type: String
   * Version history: Added in 0.6.0
   */
  username: string;

  /**
   * Description: The webfinger acct: URI of the mentioned user.
   * Equivalent to username for local users, or username@domain for remote users.
   * Type: String
   * Version history: Added in 0.6.0
   */
  acct: string;

  /**
   * Description: The location of the mentioned user's profile.
   * Type: String (URL)
   * Version history: Added in 0.6.0
   */
  url: string;
}