/**
 * Represents the relationship between accounts, such as following / blocking / muting / etc.
 * @see https://docs.joinmastodon.org/entities/relationship/
 */
export type Relationship = {

  // Required Attributes

  /**
   * Description: The account id.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history:
   * 0.6.0 - added
   */
  id: string;

  /**
   * Description: Are you following this user?
   * Type: Boolean
   * Version history:
   * 0.6.0 - added
   */
  following: boolean;

  /**
   * Description: Do you have a pending follow request for this user?
   * Type: Boolean
   * Version history:
   * 0.9.9 - added
   */
  requested: boolean;

  /**
   * Description: Are you featuring this user on your profile?
   * Type: Boolean
   * Version history:
   * 2.5.0 - added
   */
  endorsed: boolean;

  /**
   * Description: Are you followed by this user?
   * Type: Boolean
   * Version history:
   * 0.6.0 - added
   */
  followed_by: boolean;

  /**
   * Description: Are you muting this user?
   * Type: Boolean
   * Version history: Added in 1.1.0
   */
  muting: boolean;

  /**
   * Description: Are you muting notifications from this user?
   * Type: Boolean
   * Version history:
   * 2.1.0 - added
   */
  muting_notifications: boolean;

  /**
   * Description: Are you receiving this user's boosts in your home timeline?
   * Type: Boolean
   * Version history:
   * 2.1.0 - added
   */
  showing_reblogs: boolean;

  /**
   * Description: Have you enabled notifications for this user?
   * Type: Boolean
   * Version history:
   * 3.3.0 - added
   */
  notifying: boolean;

  /**
   * Description: Are you blocking this user?
   * Type: Boolean
   * Version history:
   * 0.6.0 - added
   */
  blocking: boolean;

  /**
   * Description: Are you blocking this user's domain?
   * Type: Boolean
   * Version history:
   * 1.4.0 - added
   */
  domain_blocking: boolean;

  /**
   * Description: Is this user blocking you?
   * Type: Boolean
   * Version history:
   * 2.8.0 - added
   */
  blocked_by: boolean;

  /**
   * Description: This user's profile bio
   * Type: String
   * Version history:
   * 3.2.0 - added
   */
  note: string;
}