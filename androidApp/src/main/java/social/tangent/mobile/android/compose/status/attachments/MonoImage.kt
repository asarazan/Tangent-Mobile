package social.tangent.mobile.android.compose.status.attachments

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

val width = 540
val height = 260

@Composable
fun MonoImage(
    attachment: Attachment,
    modifier: Modifier = Modifier
) {
    val bmp = attachment.blurhash?.let { hash ->
        remember {
            BlurHashDecoder.decode(hash, width, height)
        }
    }
    PreviewableImage(
        url = attachment.url,
        placeholder = bmp,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .clip(RoundedCornerShape(8.dp))
    )
}