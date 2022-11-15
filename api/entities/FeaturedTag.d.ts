/**
 * Represents a hashtag that is featured on a profile.
 * @see https://docs.joinmastodon.org/entities/featuredtag/
 */
export type FeaturedTag = {

  // Attributes

  /**
   * Description: The internal ID of the featured tag in the database.
   * Type: String (cast from integer but not guaranteed to be a number)
   * Version history: Added in 3.0.0
   */
  id: string;

  /**
   * Description: The name of the hashtag being featured.
   * Type: String
   * Version history: Added in 3.0.0
   */
  name: string;

  /**
   * Description: A link to all statuses by a user that contain this hashtag.
   * Type: String (URL)
   * Version history: Added in 3.3.0
   */
  url: string;

  /**
   * Description: The number of authored statuses containing this hashtag.
   * Type: Number
   * Version history: Added in 3.0.0
   *
   * @precision int
   */
  statuses_count: number;

  /**
   * Description: The timestamp of the last authored status containing this hashtag.
   * Type: String (ISO 8601 Datetime)
   * Version history: Added in 3.0.0
   */
  last_status_at: string;
};