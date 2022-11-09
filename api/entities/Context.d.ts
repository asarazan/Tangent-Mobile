import {Status} from "./Status";

export type Context = {

  /**
   * The ancestors of the status in the conversation, as a list of {@link Status}
   */
  ancestors: Status[];

  /**
   * The descendants of the status in the conversation, as a list of {@link Status}
   */
  descendants: Status[];
}