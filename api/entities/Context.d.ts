import {Status} from "./Status";

/**
 * Represents the tree around a given status. Used for reconstructing threads of statuses.
 * @see https://docs.joinmastodon.org/entities/context/
 */
export type Context = {

  // Required Attributes

  /**
   * Description: Parents in the thread.
   * Type: Array of Status
   * Version history: Added in 0.6.0
   */
  ancestors: Status[];

  /**
   * Description: Children in the thread.
   * Type: Array of Status
   * Version history: Added in 0.6.0
   */
  descendants: Status[];
}