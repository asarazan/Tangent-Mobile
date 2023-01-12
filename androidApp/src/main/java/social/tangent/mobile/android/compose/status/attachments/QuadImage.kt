package social.tangent.mobile.android.compose.status.attachments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.compose.images.PreviewableImage
import social.tangent.mobile.api.entities.Attachment
import social.tangent.mobile.launchWebView

@Composable
fun QuadImage(
    attachments: List<Attachment>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(2f)
        .clip(RoundedCornerShape(8.dp)))
    {
        Row(modifier = Modifier.weight(1f)) {
            attachments.dropLast(2).forEach {
                PreviewableImage(
                    url = it.url,
                    blurhash = it.blurhash,
                    modifier = Modifier.weight(1f).clickable { launchWebView(it.url) }
                )
                if (it != attachments[1]) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.weight(1f)) {
            attachments.drop(2).forEach {
                PreviewableImage(
                    url = it.url,
                    blurhash = it.blurhash,
                    modifier = Modifier.weight(1f).clickable { launchWebView(it.url) }
                )
                if (it != attachments.last()) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}