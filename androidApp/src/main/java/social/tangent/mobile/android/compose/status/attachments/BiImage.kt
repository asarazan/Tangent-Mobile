package social.tangent.mobile.android.compose.status.attachments

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.wolt.blurhashkt.BlurHashDecoder
import social.tangent.mobile.android.compose.images.PreviewableImage
import social.tangent.mobile.api.entities.Attachment

@Composable
fun BiImage(
    attachments: List<Attachment>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(2f)
        .clip(RoundedCornerShape(8.dp)))
    {
        attachments.forEach {
            val bmp = it.blurhash?.let { hash ->
                remember {
                    BlurHashDecoder.decode(hash, width / 2, height)
                }
            }
            PreviewableImage(
                url = it.url,
                placeholder = bmp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}