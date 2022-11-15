import {History} from "./History";

/**
 * Represents a hashtag used within the content of a status.
 * @see https://docs.joinmastodon.org/entities/tag/
 */
export type Tag = {

  // Base Attributes

  /**
   * Description: The value of the hashtag after the # sign.
   * Type: String
   * Version history: Added in 0.9.0
   */
  name: string;

  /**
   * Description: A link to the hashtag on the instance.
   * Type: String (URL)
   * Version history: Added in 0.9.0
   */
  url: string;

  // Optional Attributes

  /**
   * Description: Usage statistics for given days.
   * Type: Array of History
   * Version history: Added in 2.4.1
   */
  history?: History[];
}