/**
 * Represents an OAuth token used for authenticating with the API and performing actions.
 * @see https://docs.joinmastodon.org/entities/token/
 */
export type Token = {

  // Attributes

  /**
   * Description: An OAuth token to be used for authorization.
   * Type: String
   * Version history: Added in 0.1.0
   */
  access_token: string;

  /**
   * Description: The OAuth token type. Mastodon uses Bearer tokens.
   * Type: String
   * Version history: Added in 0.1.0
   */
  token_type: string;

  /**
   * Description: The OAuth scopes granted by this token, space-separated.
   * Type: String
   * Version history: Added in 0.1.0
   */
  scope: string;

  /**
   * Description: When the token was generated.
   * Type: Number (UNIX Timestamp)
   * Version history: Added in 0.1.0
   *
   * @precision long
   */
  created_at: number;
};
