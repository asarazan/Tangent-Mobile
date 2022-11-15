import {Account} from "./Account";
import {Status} from "./Status";
import {Tag} from "./Tag";

/**
 * Represents the results of a search.
 * @see https://docs.joinmastodon.org/entities/results/
 */
export type Results = {

  // Required Attributes

  /**
   * Description: Accounts which match the given query
   * Type: Array of Account
   * Version history: Added in x.x.x
   */
  accounts: Account[];

  /**
   * Description: Statuses which match the given query
   * Type: Array of Status
   * Version history: Added in x.x.x
   */
  statuses: Status[];

  /**
   * Description: Hashtags which match the given query
   * Type: Array of Tag (v2). Array of String (v1).
   * Version history: v1 added in 1.1.0 and deprecated in 3.0.0. v2 added in 2.4.1 and replaced v1 in 3.0.0.
   *
   * Note: Made an executive decision to keep v2.
   */
  hashtags: Tag[];
}