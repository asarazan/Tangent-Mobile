/**
 * Represents a proof from an external identity provider.
 * @see https://docs.joinmastodon.org/entities/identityproof/
 */
export type IdentityProof = {

  // Attributes

  /**
   * Description: The name of the identity provider.
   * Type: String
   * Version history: Added in 2.8.0
   */
  provider: string;

  /**
   * Description: The account owner's username on the identity provider's service.
   * Type: String
   * Version history: Added in 2.8.0
   */
  provider_username: string;

  /**
   * Description: The account owner's profile URL on the identity provider.
   * Type: String (URL)
   * Version history: Added in 2.8.0
   */
  profile_url: string;

  /**
   * Description: A link to a statement of identity proof, hosted by the identity provider.
   * Type: String (URL)
   * Version history: Added in 2.8.0
   */
  proof_url: string;

  /**
   * Description: When the identity proof was last updated.
   * Type: String (ISO 8601 Datetime)
   * Version history: Added in 2.8.0
   *
   * @DateTime
   */
  updated_at: string;
};