/**
 * Represents a profile field as a name-value pair with optional verification.
 * @see https://docs.joinmastodon.org/entities/field/
 */
export type Field = {

  /**
   * The key of a given field's key-value pair.
   */
  name:        string;

  /**
   * The value associated with the name key.
   * Type: String (HTML)
   */
  value:       string;

  /**
   * Timestamp of when the server verified a URL value for a rel="me" link.
   * String (ISO 8601 Datetime) if value is a verified URL.
   */
  verified_at?: string;
}