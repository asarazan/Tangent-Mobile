export type Attachment = {

  /**
   * ID of the attachment
   */
  id: string;

  /**
   * One of: "image", "video", "gifv"
   */
  type: "image" | "video" | "gifv";

  /**
   * URL of the locally hosted version of the image
   */
  url: string;

  /**
   * For remote images, the remote URL of the original image
   */
  remote_url: string;

  /**
   * URL of the preview image
   */
  preview_url: string;

  /**
   * Shorter URL for the image, for insertion into text (only present on local images)
   */
  text_url: string;
}