/**
 * Unsure.
 * @see https://docs.joinmastodon.org/entities/report/
 */
export type Report = {

  /**
   * The ID of the report
   */
  id: string;

  /**
   * The action taken in response to the report
   */
  action_taken: string;
}