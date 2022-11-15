import {Account} from "./Account";
import {Status} from "./Status";

/**
 * Represents a notification of an event relevant to the user.
 * @see https://docs.joinmastodon.org/entities/notification/
 */
export type Notification = {

  /**
   * Description: The id of the notification in the database.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history:
   * 0.9.9 - added
   */
  id: string;

  /**
   * Description: The type of event that resulted in the notification.
   * Type: String (Enumerable oneOf)
   * follow = Someone followed you
   * follow_request = Someone requested to follow you
   * mention = Someone mentioned you in their status
   * reblog = Someone boosted one of your statuses
   * favourite = Someone favourited one of your statuses
   * poll = A poll you have voted in or created has ended
   * status = Someone you enabled notifications for has posted a status
   *
   * Version history:
   * 0.9.9 - added
   * 2.8.0 - added poll
   * 3.1.0 - added follow_request
   * 3.3.0 - added status
   */
  type: "follow" | "follow_request" | "mention" | "reblog" | "favourite" | "poll" | "status";

  /**
   * Description: The timestamp of the notification.
   * Type: String (ISO 8601 Datetime)
   * Version history:
   * 0.9.9 - added
   */
  created_at: string;

  /**
   * Description: The account that performed the action that generated the notification.
   * Type: Account
   * Version history:
   * 0.9.9 - added
   */
  account: Account;

  /**
   * Description: Status that was the object of the notification,
   * e.g. in mentions, reblogs, favourites, or polls.
   * Type: Status
   * Version history:
   * 0.9.9 - added
   */
  status: Status;
}