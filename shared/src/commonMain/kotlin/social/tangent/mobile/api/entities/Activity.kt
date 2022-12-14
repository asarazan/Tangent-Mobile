/****************************************************
** DO NOT EDIT THIS FILE BY HAND!                  **
** This file was automatically generated by Martok **
** More info at https://github.com/asarazan/martok **
*****************************************************/
package social.tangent.mobile.api.entities

import kotlinx.serialization.Serializable

/**
 * Represents a weekly bucket of instance activity.
 * @see https://docs.joinmastodon.org/entities/activity/
 */
@Serializable
data class Activity(

    /**
     * Description: Midnight at the first day of the week.
     * Type: String (UNIX Timestamp)
     * Version history: Added in 2.1.2
     */
    val week: String,

    /**
     * Description: Statuses created since the week began.
     * Type: String (cast from an integer)
     * Version history: Added in 2.1.2
     */
    val statuses: List<String>,

    /**
     * Description: User logins since the week began.
     * Type: String (cast from an integer)
     * Version history: Added in 2.1.2
     */
    val logins: String,

    /**
     * Description: User registrations since the week began.
     * Type: String (cast from an integer)
     * Version history: Added in 2.1.2
     */
    val registration: String
)
