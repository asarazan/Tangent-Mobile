package social.tangent.mobile.android.html

import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import social.tangent.mobile.api.entities.Emoji

private val EMOJI = Regex(":(\\w+):")

fun CharSequence.emojify(emoji: List<Emoji>): AnnotatedString {
    val input = this
    val map = emoji.associateBy { it.shortcode }
    val matches = EMOJI.findAll(input).toList()
    var fromIndex = 0
    return buildAnnotatedString {
        matches.forEach {
            val match = it.groupValues[0]
            val code = it.groupValues[1]
            val index = it.range.first
            val snippet = input.substring(fromIndex, index)
            val url = map[code]!!.url
            append(snippet)
            appendInlineContent(url, match)
            fromIndex = index + match.length
        }
        val snippet = input.substring(fromIndex, input.length)
        append(snippet)
    }
}