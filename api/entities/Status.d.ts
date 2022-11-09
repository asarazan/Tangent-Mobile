import {Account} from "./Account";
import {Attachment} from "./Attachment";
import {Mention} from "./Mention";
import {Tag} from "./Tag";
import {Application} from "./Application";

export type Status = {

  /**
   * The ID of the status
   */
  id: string;

  /**
   * A Fediverse-unique resource ID
   */
  uri: string;

  /**
   * URL to the status page (can be remote)
   */
  url: string;

  /**
   * The {@link Account} which posted the status
   */
  account: Account;

  /**
   * null or the ID of the status it replies to
   */
  in_reply_to_id?: string;

  /**
   * null or the ID of the account it replies to
   */
  in_reply_to_account_id?: string;

  /**
   * null or the reblogged {@link Status}
   */
  reblog?: Status;

  /**
   * Body of the status; this will contain HTML (remote HTML already sanitized)
   */
  content: string;

  /**
   * The time the status was created
   */
  created_at: string;

  /**
   * The number of reblogs for the status
   */
  reblogs_count: number;

  /**
   * The number of favourites for the status
   */
  favourites_count: number;

  /**
   * Whether the authenticated user has reblogged the status
   */
  reblogged: boolean;

  /**
   * Whether the authenticated user has favourited the status
   */
  favourited: boolean;

  /**
   * Whether media attachments should be hidden by default
   */
  sensitive: boolean;

  /**
   * If not empty, warning text that should be displayed before the actual content
   */
  spoiler_text: string;

  /**
   * One of: public, unlisted, private, direct
   */
  visibility: "public" | "unlisted" | "private" | "direct";

  /**
   * An array of {@link Attachments}s
   */
  media_attachments: Attachment[];

  /**
   * An array of {@link Mentions}
   */
  mentions: Mention[];

  /**
   * An array of {@link Tag}s
   */
  tags: Tag[];

  /**
   * {@link Application} from which the status was posted
   */
  application: Application;
}