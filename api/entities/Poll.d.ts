import {Emoji} from "./Emoji";

/**
 * Represents a poll attached to a status.
 * @see https://docs.joinmastodon.org/entities/poll/
 */
export type Poll = {

  // Attributes

  /**
   * Description: The ID of the poll in the database.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history: Added in 2.8.0
   */
  id: string;

  /**
   * Description: When the poll ends.
   * Type: String (ISO 8601 Datetime), or null if the poll does not end
   * Version history: Added in 2.8.0
   */
  expires_at: string;

  /**
   * Description: Is the poll currently expired?
   * Type: Boolean
   * Version history: Added in 2.8.0
   */
  expired: boolean;

  /**
   * Description: Does the poll allow multiple-choice answers?
   * Type: Boolean
   * Version history: Added in 2.8.0
   */
  multiple: boolean;

  /**
   * Description: How many votes have been received.
   * Type: Number
   * Version history: Added in 2.8.0
   *
   * @precision long
   */
  votes_count: number;

  /**
   * Description: How many unique accounts have voted on a multiple-choice poll.
   * Type: Number, or null if multiple is false.
   * Version history: Added in 2.8.0
   */
  voters_count?: number;

  /**
   * Description: When called with a user token, has the authorized user voted?
   * Type: Boolean, or null if no current user
   * Version history: Added in 2.8.0
   */
  voted?: boolean;

  /**
   * Description: When called with a user token, which options has the authorized user chosen?
   * Contains an array of index values for options.
   * Type: Array of Number, or null if no current user
   * Version history: Added in 2.8.0
   *
   * @precision long
   */
  own_votes?: number[];

  /**
   * Description: Possible answers for the poll.
   * Type: Array of Hash
   * Version history: Added in 2.8.0
   */
  options: {

    /**
     * The text value of the poll option. String.
     */
    title: string;

    /**
     * The number of received votes for this option.
     * Number, or null if results are not published yet.
     */
    votes_count?: number;
  }[];

  /**
   * Description: Custom emoji to be used for rendering poll options.
   * Type: Array of Emoji
   * Version history: Added in 2.8.0
   */
  emojis: Emoji[];
};