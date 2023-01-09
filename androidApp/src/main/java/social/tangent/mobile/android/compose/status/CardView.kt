package social.tangent.mobile.android.compose.status

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wolt.blurhashkt.BlurHashDecoder
import social.tangent.mobile.android.compose.images.PreviewableImage
import social.tangent.mobile.android.onBackgroundFaint
import social.tangent.mobile.api.entities.Card

private const val width = 540
private const val height = 260

@Composable
fun CardView(card: Card, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        val blur = if (card.blurhash != null) BlurHashDecoder.decode(card.blurhash, width, height) else null
        if (card.image != null) {
            PreviewableImage(url = card.image!!, modifier = Modifier.aspectRatio(2.5f))
        }
        Column(modifier = modifier.padding(8.dp)) {
            Text(card.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = modifier.height(4.dp))
            if (card.description.isNotBlank()) {
                Text(
                    card.description,
                    color = MaterialTheme.colors.onBackgroundFaint,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = modifier.height(2.dp))
            }
            Text(
                card.url,
                color = MaterialTheme.colors.onBackgroundFaint,
                modifier = Modifier.alpha(0.4f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = modifier.height(2.dp))
            // if (card.html != null) Html(text = card.html!!)
        }
    }
}