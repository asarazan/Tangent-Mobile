package social.tangent.mobile.android.compose.images

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wolt.blurhashkt.BlurHashDecoder
import social.tangent.mobile.android.R

private const val width = 540
private const val height = 260

@Composable
fun PreviewableImage(
    url: String,
    modifier: Modifier = Modifier,
    blurhash: String? = null
) {
    if (LocalInspectionMode.current) {
        Image(
            painter = painterResource(id = R.drawable.sarazan),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    } else {
        val bmp = remember {
            blurhash?.let {
                BitmapDrawable(BlurHashDecoder.decode(it, width, height))
            }
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                // .crossfade(true)
                .placeholder(bmp)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}