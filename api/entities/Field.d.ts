/**
 * Represents a profile field as a name-value pair with optional verification.
 * @see https://docs.joinmastodon.org/entities/field/
 */
export type Field = {

  /**
   * Description: The key of a given field's key-value pair.
   * Type: String
   * Version history: Added in 2.4.0
   */
  name: string;

  /**
   * Description: The value associated with the name key.
   * Type: String (HTML)
   * Version history: Added in 2.4.0
   */
  value: string;

  /**
   * Description: Timestamp of when the server verified a URL value for a rel="me” link.
   * Type: String (ISO 8601 Datetime) if value is a verified URL. Otherwise, null
   * Version history: Added in 2.6.0
   */
  verified_at?: string;
}