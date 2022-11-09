import {Account} from "./Account";
import {Status} from "./Status";

export type Notification = {

  /**
   * The notification ID
   */
  id: string;

  /**
   * One of: "mention", "reblog", "favourite", "follow"
   */
  type: "mention" | "reblog" | "favourite" | "follow";

  /**
   * The time the notification was created
   */
  created_at: string;

  /**
   * The {@link Account} sending the notification to the user
   */
  account: Account;

  /**
   * The {@link Status} associated with the notification, if applicable
   */
  status: Status;
}