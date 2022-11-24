/****************************************************
** DO NOT EDIT THIS FILE BY HAND!                  **
** This file was automatically generated by Martok **
** More info at https://github.com/asarazan/martok **
*****************************************************/
package social.tangent.mobile.api.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a user's preferences.
 * @see https://docs.joinmastodon.org/entities/preferences/
 */
@Serializable
data class Preferences(

    /**
     * Description: Default visibility for new posts. Equivalent to Source#privacy.
     * Type: String (Enumerable, oneOf)
     * public = Public post
     * unlisted = Unlisted post
     * private = Followers-only post
     * direct = Direct post
     * Version history: Added in 2.8.0
     */
    @SerialName("posting:default:visibility")
    val postingDefaultVisibility: PostingDefaultVisibility,

    /**
     * Description: Default sensitivity flag for new posts. Equivalent to Source#sensitive.
     * Type: Boolean
     * Version history: Added in 2.8.0
     */
    @SerialName("posting:default:sensitive")
    val postingDefaultSensitive: Boolean,

    /**
     * Description: Default language for new posts. Equivalent to Source#language
     * Type: String (ISO 639-1 language two-letter code), or null
     * Version history: Added in 2.8.0
     */
    @SerialName("posting:default:language")
    val postingDefaultLanguage: String? = null,

    /**
     * Description: Whether media attachments should be automatically displayed or blurred/hidden.
     * Type: String (Enumerable, oneOf)
     * default = Hide media marked as sensitive
     * show_all = Always show all media by default, regardless of sensitivity
     * hide_all = Always hide all media by default, regardless of sensitivity
     * Version history: Added in 2.8.0
     */
    @SerialName("reading:expand:media")
    val readingExpandMedia: ReadingExpandMedia,

    /**
     * Description: Whether CWs should be expanded by default.
     * Type: Boolean
     * Version history: Added in 2.8.0
     */
    @SerialName("reading:expand:spoilers")
    val readingExpandSpoilers: Boolean
) {

    @Serializable
    enum class PostingDefaultVisibility(
        val serialName: String
    ) {
        @SerialName("public")
        PUBLIC("public"),

        @SerialName("unlisted")
        UNLISTED("unlisted"),

        @SerialName("private")
        PRIVATE("private"),

        @SerialName("direct")
        DIRECT("direct");
    }

    @Serializable
    enum class ReadingExpandMedia(
        val serialName: String
    ) {
        @SerialName("default")
        DEFAULT("default"),

        @SerialName("show_all")
        SHOW_ALL("show_all"),

        @SerialName("hide_all")
        HIDE_ALL("hide_all");
    }
}
