/**
 * Represents a status that will be published at a future scheduled date.
 * @see https://docs.joinmastodon.org/entities/scheduledstatus/
 */
export type ScheduledStatus = {

  // Required Attributes

  /**
   * Description: ID of the scheduled status in the database.
   * Type: String (cast from an integer but not guaranteed to be a number)
   * Version history: Added in 2.7.0
   */
  id: string;

  /**
   * Description: ID of the status in the database.
   * Type: String (ISO 8601 Datetime)
   * Version history: Added in 2.7.0
   *
   * @DateTime
   */
  scheduled_at: string;

  // The rest are under construction.
  // We'll add them once they're finalized!
};