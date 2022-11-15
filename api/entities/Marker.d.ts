/**
 * Represents the last read position within a user's timelines.
 * @see https://docs.joinmastodon.org/entities/marker/
 */
export type Marker = {

  // Base Attributes

  /**
   * Description: Information about the user's position in the home timeline.
   * Type: Hash
   * Version history: Added in 3.0.0
   */
  home: MarkerEntry;

  /**
   * Description: Information about the user's position in their notifications.
   * Type: Hash
   * Version history: Added in 3.0.0
   */
  notifications: MarkerEntry;
};

/**
 * This was supposed to be a hash, but see above.
 */
export type MarkerEntry = {

  // Nested Attributes

  /**
   * Description: The ID of the most recently viewed entity.
   * Type: String (cast from integer but not guaranteed to be a number)
   * Version history: Added in 3.0.0
   */
  last_read_id: string;

  /**
   * Description: The timestamp of when the marker was set.
   * Type: String (ISO 8601 Datetime)
   * Version history: Added in 3.0.0
   */
  updated_at: string;

  /**
   * Description: Used for locking to prevent write conflicts.
   * Type: Number
   * Version history: Added in 3.0.0
   */
  version: string;
};