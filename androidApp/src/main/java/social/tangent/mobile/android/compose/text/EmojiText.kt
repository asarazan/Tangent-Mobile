package social.tangent.mobile.android.compose.text

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import social.tangent.mobile.android.html.emojify
import social.tangent.mobile.api.entities.Emoji

@Composable
fun rememberEmojiMap(emoji: List<Emoji>): Map<String, InlineTextContent> {
    return remember {
        emoji.associate {
            Pair(it.url, EmojiInline(id = it.shortcode, url = it.url))
        }
    }
}

@Composable
fun EmojiText(
    text: String,
    emoji: List<Emoji>,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle = LocalTextStyle.current
) {
    println("EMOJI TEXT:\n${text}\n")
    val processed = remember { text.emojify(emoji) }
    val contentMap = rememberEmojiMap(emoji = emoji)
    Text(
        text = processed,
        inlineContent = contentMap,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        overflow = overflow,
        style = style
    )
}