package social.tangent.mobile.android.compose.text

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

fun EmojiInline(id: String, url: String): InlineTextContent {
    return InlineTextContent(
        Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
    ) {
        AsyncImage(
            model = url,
            contentDescription = id,
            modifier = Modifier.fillMaxSize()
        )
    }
}