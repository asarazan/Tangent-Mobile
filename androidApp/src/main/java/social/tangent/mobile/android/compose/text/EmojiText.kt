package social.tangent.mobile.android.compose.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import social.tangent.mobile.android.html.emojify
import social.tangent.mobile.api.entities.Emoji

private val EMOJI = Regex(":(\\w+):")

@Composable
fun EmojiText(
    text: String,
    emoji: List<Emoji>,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle = LocalTextStyle.current
) {
    println("EMOJI TEXT:\n${text}\n")
    val processed = text.emojify(emoji)
    Text(
        text = processed, // TODO
    )
}