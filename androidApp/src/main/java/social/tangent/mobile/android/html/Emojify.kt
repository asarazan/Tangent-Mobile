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
            val url = map[code]!!.url
            appendSnippet(input, fromIndex, index)
            appendInlineContent(url, match)
            fromIndex = index + match.length
        }
        appendSnippet(input, fromIndex, input.length)
    }
}

private fun AnnotatedString.Builder.appendSnippet(
    input: CharSequence,
    fromIndex: Int,
    index: Int
) {
    when (input) {
        is AnnotatedString -> {
            val snippet = input.subSequence(fromIndex, index)
            append(snippet)
        }
        else -> {
            val snippet = input.substring(fromIndex, index)
            append(snippet)
        }
    }
}