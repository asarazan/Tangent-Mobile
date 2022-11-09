export type Account = {

  /**
   * The ID of the account
   * @precision long
   */
  id: number;

  /**
   * The username of the account
   */
  username: string;

  /**
   * Equals username for local users, includes @domain for remote ones
   */
  acct: string;

  /**
   * The account's display name
   */
  display_name: string;

  /**
   * Boolean for when the account cannot be followed without waiting for approval first
   */
  locked: boolean;

  /**
   * The time the account was created
   */
  created_at: string;

  /**
   The number of followers for the account
   * @precision int
   */
  followers_count: number;

  /**
   * The number of accounts the given account is following
   * @precision int
   */
  following_count: number;

  /**
   * The number of statuses the account has made
   * @precision int
   */
  statuses_count: number;

  /**
   * Biography of user
   */
  note: string;

  /**
   * URL of the user's profile page (can be remote)
   */
  url: string;

  /**
   * URL to the avatar image
   */
  avatar: string;

  /**
   * URL to the avatar static image (gif)
   */
  avatar_static: string;

  /**
   * URL to the header image
   */
  header: string;

  /**
   * URL to the header static image (gif)
   */
  header_static: string;
}