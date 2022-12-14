/**
 * Represents an error message.
 * @see https://docs.joinmastodon.org/entities/error/
 *
 * Possible reasons
 * 401 - Unauthorized
 * require_authenticated_user!
 *
 * Error: This API requires an authenticated user. Appears when the instance is in secure mode, which disables all public use of API methods.
 * 403 - Forbidden
 * current_user.disabled?
 *
 * Error: Your login is currently disabled. Appears when the OAuth token's authorized user has had their account disabled by a moderator.
 * !current_user.confirmed?
 *
 * Error: Your login is missing a confirmed e-mail address. Appears when the email address associated with the OAuth token's authorized user's account has not yet been confirmed.
 * !current_user.approved?
 *
 * Error: Your login is currently pending approval. Appears when the OAuth token's authorized user has signed up on an instance with approval-required registrations, and the user has not yet had their account approved by a moderator.
 * 404 - Not Found
 * RecordNotFound
 *
 * Error: Record not found. Appears when an entity record does not exist, or the authorized user is not within the audience of a private entity.operates on a user.
 * 422 - Unprocessable Entity
 * RecordInvalid
 *
 * Error: {string}. May appear when entity creation failed.
 * RecordNotUnique
 *
 * Error: Duplicate record. Appears when you are trying to pin an account or status that is already pinned.
 * !current_user
 *
 * Error: This method requires an authenticated user. Appears when using an OAuth token without an authorized user (or no token at all), while trying to call an API method that requires a user to be processed.
 */
export type Error = {

  // Required Attributes

  /**
   * Description: The error message.
   * Type: String
   * Version history: Added in 0.6.0
   */
  error: string;

  /**
   * Description: A longer description of the error, mainly provided with the OAuth API.
   * Type: String
   * Version history: Added in 0.6.0
   */
  error_description?: string;
}