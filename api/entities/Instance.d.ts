import {Account} from "./Account";

/**
 * Represents the software instance of Mastodon running on this domain.
 * @see https://docs.joinmastodon.org/entities/instance/
 */
export type Instance = {

  // Required Attributes

  /**
   * Description: The domain name of the instance.
   * Type: String
   * Version history: Added in 1.1.0
   */
  uri: string;

  /**
   * Description: The title of the website.
   * Type: String
   * Version history: Added in 1.1.0
   */
  title: string;

  /**
   * Description: Admin-defined description of the Mastodon site.
   * Type: String
   * Version history: Added in 1.1.0
   */
  description: string;

  /**
   * Description: A shorter description defined by the admin.
   * Type: String
   * Version history: Added in 2.9.2
   */
  short_description: string;

  /**
   * Description: An email that may be contacted for any inquiries.
   * Type: String
   * Version history: Added in 1.1.0
   */
  email: string;

  /**
   * Description: The version of Mastodon installed on the instance.
   * Type: String
   * Version history: Added in 1.3.0
   */
  version: string;

  /**
   * Description: Primary languages of the website and its staff.
   * Type: Array of String (ISO 639 Part 1-5 language codes)
   * Version history: Added in 2.3.0
   */
  languages: string[];

  /**
   * Description: Whether registrations are enabled.
   * Type: Boolean
   * Version history: Added in 2.7.2
   */
  registrations: boolean;

  /**
   * Description: Whether registrations require moderator approval.
   * Type: Boolean
   * Version history: Added in 2.9.2
   */
  approval_required: boolean;

  /**
   * Description: Whether invites are enabled.
   * Type: Boolean
   * Version history: Added in 3.1.4
   */
  invites_enabled: boolean;

  /**
   * Description: URLs of interest for clients apps.
   * Type: Hash (streaming_api)
   * Version history: Added in 1.4.2
   *
   * urls[streaming_api]
   * Websockets address for push streaming. String (URL).
   */
  urls: {
    streaming_api: string;
  };

  /**
   * Description: Statistics about how much information the instance contains.
   * Type: Hash (user_count, status_count, domain_count)
   * Version history: Added in 1.6.0
   */
  stats: {

    /**
     * Users registered on this instance. Number.
     * @precision long
     */
    user_count: number;

    /**
     * Statuses authored by users on instance. Number.
     * @precision long
     */
    status_count: number;

    /**
     * Domains federated with this instance. Number.
     * @precision long
     */
    domain_count: number;
  };

  // Optional Attributes

  /**
   * Description: Banner image for the website.
   * Type: String (URL) or null
   * Version history: Added in 1.6.1
   */
  thumbnail?: string;

  /**
   * Description: A user that can be contacted, as an alternative to email.
   * Type: Account or null
   * Version history: Added in 2.3.0
   */
  contact_account?: Account;
}
