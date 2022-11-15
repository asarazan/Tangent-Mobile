import {Field} from "./Field";

/**
 * Represents display or publishing preferences of user's own account.
 * Returned as an additional entity when verifying and updated credentials, as an attribute of Account.
 * @see https://docs.joinmastodon.org/entities/source/
 */
export type Source = {

  // Base Attributes

  /**
   * Profile bio.
   */
  note: string;

  /**
   * Metadata about the account.
   */
  fields: Field[];

  // Nullable Attributes

  /**
   * The default post privacy to be used for new statuses.
   */
  privacy?: "public" | "unlisted" | "private" | "direct";

  /**
   * Whether new statuses should be marked sensitive by default.
   */
  sensitive?: boolean;

  /**
   * The default posting language for new statuses.
   * Type: String (ISO 639-1 language two-letter code)
   */
  language?: string;

  /**
   * The number of pending follow requests.
   * @precision int
   */
  follow_requests_count?: number;
}