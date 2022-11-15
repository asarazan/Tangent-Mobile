import {Field} from "./Field";

/**
 * Represents display or publishing preferences of user's own account.
 * Returned as an additional entity when verifying and updated credentials, as an attribute of Account.
 * @see https://docs.joinmastodon.org/entities/source/
 */
export type Source = {

  // Base Attributes

  /**
   * Description: Profile bio.
   * Type: String
   * Version history: Added in 1.5.0
   */
  note: string;

  /**
   * Description: Metadata about the account.
   * Type: Array of Field
   * Version history: Added in 2.4.0
   */
  fields: Field[];

  // Nullable Attributes

  /**
   * Description: The default post privacy to be used for new statuses.
   * Type: String (Enumerable, oneOf)
   * public = Public post
   * unlisted = Unlisted post
   * private = Followers-only post
   * direct = Direct post
   * Version history: Added in 1.5.0
   */
  privacy?: "public" | "unlisted" | "private" | "direct";

  /**
   * Description: Whether new statuses should be marked sensitive by default.
   * Type: Boolean
   * Version history: Added in 1.5.0
   */
  sensitive?: boolean;

  /**
   * Description: The default posting language for new statuses.
   * Type: String (ISO 639-1 language two-letter code)
   * Version history: Added in 2.4.2
   */
  language?: string;

  /**
   * Description: The number of pending follow requests.
   * Type: Number
   * Version history: Added in 3.0.0.
   *
   * @precision int
   */
  follow_requests_count?: number;
}