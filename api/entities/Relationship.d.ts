export type Relationship = {

  /**
   * Target account id
   */
  id: string;

  /**
   * Whether the user is currently following the account
   */
  following: boolean;

  /**
   * Whether the user is currently being followed by the account
   */
  followed_by: boolean;

  /**
   * Whether the user is currently blocking the account
   */
  blocking: boolean;

  /**
   * Whether the user is currently muting the account
   */
  muting: boolean;

  /**
   * Whether the user has requested to follow the account
   */
  requested: boolean;
}