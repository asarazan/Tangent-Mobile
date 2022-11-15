import {Account} from "./Account";
import {Status} from "./Status";

/**
 * Represents a conversation with "direct message" visibility.
 * @see https://docs.joinmastodon.org/entities/conversation/
 */
export type Conversation = {

  // Required Attributes

  /**
   * Description: Local database ID of the conversation.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history: Added in 2.6.0
   */
  id: string;

  /**
   * Description: Participants in the conversation.
   * Type: Array of Account
   * Version history: Added in 2.6.0
   */
  accounts: Account[];

  /**
   * Description: Is the conversation currently marked as unread?
   * Type: Boolean
   * Version history: Added in 2.6.0
   */
  unread: boolean;

  // Optional Attributes

  /**
   * Description: The last status in the conversation, to be used for optional display.
   * Type: Status
   * Version history: Added in 2.6.0
   */
  last_status?: Status;
};