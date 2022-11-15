/**
 * Represents a weekly bucket of instance activity.
 * @see https://docs.joinmastodon.org/entities/activity/
 */
export type Activity = {

  /**
   * Description: Midnight at the first day of the week.
   * Type: String (UNIX Timestamp)
   * Version history: Added in 2.1.2
   */
  week: string;

  /**
   * Description: Statuses created since the week began.
   * Type: String (cast from an integer)
   * Version history: Added in 2.1.2
   */
  statuses: string[];

  /**
   * Description: User logins since the week began.
   * Type: String (cast from an integer)
   * Version history: Added in 2.1.2
   */
  logins: string;

  /**
   * Description: User registrations since the week began.
   * Type: String (cast from an integer)
   * Version history: Added in 2.1.2
   */
  registration: string;
};