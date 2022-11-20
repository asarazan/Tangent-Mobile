import {Account} from "./Account";
import {Attachment} from "./Attachment";
import {Mention} from "./Mention";
import {Tag} from "./Tag";
import {Application} from "./Application";
import {Emoji} from "./Emoji";
import {Poll} from "./Poll";
import {Card} from "./Card";

/**
 * Represents a status posted by an account.
 * @see https://docs.joinmastodon.org/entities/status/
 */
export type Status = {

  /**
   * Description: ID of the status in the database.
   * Type: String (cast from an integer but not guaranteed to be a number)
   * Version history: Added in 0.1.0
   */
  id: string;

  /**
   * Description: URI of the status used for federation.
   * Type: String
   * Version history: Added in 0.1.0
   */
  uri: string;

  /**
   * Description: The date when this status was created.
   * Type: String (ISO 8601 Datetime)
   * Version history: Added in 0.1.0
   *
   * @DateTime
   */
  created_at: string;

  /**
   * Description: The account that authored this status.
   * Type: Account
   * Version history: Added in 0.1.0
   */
  account: Account;

  /**
   * Description: HTML-encoded status content.
   * Type: String (HTML)
   * Version history: Added in 0.1.0
   */
  content: string;

  /**
   * Description: Visibility of this status.
   * Type: String (Enumerable oneOf)
   * public = Visible to everyone, shown in public timelines.
   * unlisted = Visible to public, but not included in public timelines.
   * private = Visible to followers only, and to any mentioned users.
   * direct = Visible only to mentioned users.
   *
   * Version history: Added in 0.9.9
   */
  visibility: "public" | "unlisted" | "private" | "direct";

  /**
   * Description: Is this status marked as sensitive content?
   * Type: Boolean
   * Version history: Added in 0.9.9
   */
  sensitive: boolean;

  /**
   * Description: Subject or summary line, below which status content is collapsed until expanded.
   * Type: String
   * Version history: Added in 1.0.0
   */
  spoiler_text: string;

  /**
   * Description: Media that is attached to this status.
   * Type: Array of Attachment
   * Version history: Added in 0.6.0
   */
  media_attachments: Attachment[];

  /**
   * Description: The application used to post this status.
   * Type: Application
   * Version history: Added in 0.9.9
   */
  application?: Application;

  // Rendering Attributes

  /**
   * Description: Mentions of users within the status content.
   * Type: Array of Mention
   * Version history: Added in 0.6.0
   */
  mentions: Mention[];

  /**
   * Description: Hashtags used within the status content.
   * Type: Array of Tag
   * Version history: Added in 0.9.0
   */
  tags: Tag[];

  /**
   * Description: Custom emoji to be used when rendering status content.
   * Type: Array of Emoji
   * Version history: Added in 2.0.0
   */
  emojis: Emoji[];

  // Informational Attributes

  /**
   * Description: How many boosts this status has received.
   * Type: Number
   * Version history: Added in 0.1.0
   *
   * @precision long
   */
  reblogs_count: number;

  /**
   * Description: How many favourites this status has received.
   * Type: Number
   * Version history: Added in 0.1.0
   *
   * @precision long
   */
  favourites_count: number;

  /**
   * Description: How many replies this status has received.
   * Type: Number
   * Version history: Added in 2.5.0
   *
   * @precision long
   */
  replies_count: number;

  // Nullable Attributes

  /**
   * Description: A link to the status's HTML representation.
   * Type: String (URL)
   * Version history: Added in 0.1.0
   */
  url?: string;

  /**
   * Description: ID of the status being replied.
   * Type: String (cast from an integer but not guaranteed to be a number)
   * Version history: Added in 0.1.0
   */
  in_reply_to_id?: string;

  /**
   * Description: ID of the account being replied to.
   * Type: String (cast from an integer but not guaranteed to be a number)
   * Version history: Added in 1.0.0
   */
  in_reply_to_account_id?: string;

  /**
   * Description: The status being reblogged.
   * Type: Status
   * Version history: Added in 0.1.0
   */
  reblog?: Status;

  /**
   * Description: The poll attached to the status.
   * Type: Poll
   * Version history: Added in 2.8.0
   */
  poll?: Poll;

  /**
   * Description: Preview card for links included within status content.
   * Type: Card
   * Version history: Added in 2.6.0
   */
  card?: Card;

  /**
   * Description: Primary language of this status.
   * Type: String (ISO 639 Part 1 two-letter language code)
   * Version history: Added in 1.4.0
   */
  language?: string;

  /**
   * Description: Plain-text source of a status.
   * Returned instead of content when status is deleted,
   * so the user may redraft from the source text without the client having to reverse-engineer the original text from the HTML content.
   * Type: String
   * Version history: Added in 2.9.0
   */
  text?: string;

  // Authorized User Attributes

  /**
   * Description: Have you favourited this status?
   * Type: Boolean
   * Version history: Added in 0.1.0
   */
  favourited?: boolean;

  /**
   * Description: Have you boosted this status?
   * Type: Boolean
   * Version history: Added in 0.1.0
   */
  reblogged?: boolean;

  /**
   * Description: Have you muted notifications for this status's conversation?
   * Type: Boolean
   * Version history: Added in 1.4.0
   */
  muted?: boolean;

  /**
   * Description: Have you bookmarked this status?
   * Type: Boolean
   * Version history: Added in 3.1.0
   */
  bookmarked?: boolean;

  /**
   * Description: Have you pinned this status? Only appears if the status is pinnable.
   * Type: Boolean
   * Version history: Added in 1.6.0
   */
  pinned?: boolean;
}