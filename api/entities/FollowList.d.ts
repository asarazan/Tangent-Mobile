/**
 * Represents a list of some users that the authenticated user follows.
 * @see https://docs.joinmastodon.org/entities/list/
 */
export type FollowList = {

  // Required Attributes

  /**
   * Description: The internal database ID of the list.
   * Type: String (cast from an integer, but not guaranteed to be a number)
   * Version history:
   * 2.1.0 - added
   */
  id: string;

  /**
   * Description: The user-defined title of the list.
   * Type: String
   * Version history:
   * 2.1.0 - added
   */
  title: string;

  /**
   * Description: The user-defined title of the list.
   * Type: String (Enumerable oneOf)
   * followed = Show replies to any followed user
   * list = Show replies to members of the list
   * none = Show replies to no one
   * Version history:
   * 3.3.0 - added
   */
  replies_policy: "followed" | "list" | "none";
};
