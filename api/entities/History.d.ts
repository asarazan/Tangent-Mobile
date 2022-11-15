/**
 * Represents daily usage history of a hashtag.
 * @see https://docs.joinmastodon.org/entities/history/
 */
export type History = {

  // Required Attributes

  /**
   * Description: UNIX timestamp on midnight of the given day.
   * Type: String (UNIX timestamp)
   * Version history: Added in 2.4.1
   */
  day: string;

  /**
   * Description: the counted usage of the tag within that day.
   * Type: String (cast from an integer)
   * Version history: Added in 2.4.1
   */
  uses: string;

  /**
   * Description: the total of accounts using the tag within that day.
   * Type: String (cast from an integer)
   * Version history: Added in 2.4.1
   */
  accounts: string;
};