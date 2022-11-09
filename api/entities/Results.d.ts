import {Account} from "./Account";
import {Status} from "./Status";

export type Results = {

  /**
   * An array of matched {@link Account}s
   */
  accounts: Account[];

  /**
   * An array of matchhed {@link Status}es
   */
  statuses: Status[];

  /**
   * An array of matched hashtags, as strings
   */
  hashtags: string[];
}