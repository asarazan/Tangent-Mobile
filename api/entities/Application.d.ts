/**
 * Represents an application that interfaces with the REST API to access accounts or post statuses.
 * @see https://docs.joinmastodon.org/entities/application/
 */
export type Application = {

  // Required Attributes

  /**
   * Description: The name of your application.
   * Type: String
   * Version history: Added in 0.9.9
   */
  name: string;

  // Optional Attributes

  /**
   * Description: The website associated with your application.
   * Type: String (URL)
   * Version history: Added in 0.9.9
   */
  website?: string;

  /**
   * Description: Used for Push Streaming API.
   * Returned with POST /api/v1/apps.
   * Equivalent to PushSubscription#server_key
   * Type: String
   * Version history: Added in 2.8.0
   */
  vapid_key?: string;

  // Client Attributes

  /**
   * Description: Client ID key, to be used for obtaining OAuth tokens
   * Type: String
   * Version history: Added in 0.9.9
   */
  client_id?: string;

  /**
   * Description: Client secret key, to be used for obtaining OAuth tokens
   * Type: String
   * Version history: Added in 0.9.9
   */
  client_secret?: string;
}