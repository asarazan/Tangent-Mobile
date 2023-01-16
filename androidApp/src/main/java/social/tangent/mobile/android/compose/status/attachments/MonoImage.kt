package social.tangent.mobile.android.compose.status.attachments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.compose.images.PreviewableImage
import social.tangent.mobile.api.entities.Attachment
import social.tangent.mobile.launchWebView

val width = 540
val height = 260

@Composable
fun MonoImage(
    attachment: Attachment,
    modifier: Modifier = Modifier
) {
    PreviewableImage(
        url = attachment.url,
        blurhash = attachment.blurhash,
        sizeKey = "mono",
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .clip(RoundedCornerShape(8.dp))
            .clickable { launchWebView(attachment.url) }
    )
}