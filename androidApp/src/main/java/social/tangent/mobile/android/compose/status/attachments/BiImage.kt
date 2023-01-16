package social.tangent.mobile.android.compose.status.attachments

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
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
            PreviewableImage(
                url = it.url,
                blurhash = it.blurhash,
                sizeKey = "bi",
                modifier = Modifier.weight(1f)
            )
            if (it != attachments.last()) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}