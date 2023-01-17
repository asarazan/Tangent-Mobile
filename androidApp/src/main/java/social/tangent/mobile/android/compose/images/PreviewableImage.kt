package social.tangent.mobile.android.compose.images

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wolt.blurhashkt.BlurHashDecoder
import social.tangent.mobile.android.R

private val sizeMap = mutableMapOf<String, IntSize>()
private const val blurScale = 16;

@Composable
fun PreviewableImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    contentDescription: String? = null,
    blurhash: String? = null,
    sizeKey: String? = null
) {
    if (!LocalInspectionMode.current) {

        // A lot of shitty optimizations
        // to hopefully reduce image pop-in jank
        var req = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
        var _modifier = modifier
        if (sizeKey != null) {
            sizeMap[sizeKey]?.let {
                val bmp = remember {
                    blurhash?.let {
                        blur ->
                        BitmapDrawable(BlurHashDecoder.decode(
                            blur,
                            it.width / blurScale,
                            it.height / blurScale))
                    }
                }
                req = req.size(it.width, it.height).placeholder(bmp)
            }
            _modifier = _modifier.onGloballyPositioned {
                sizeMap[sizeKey] = it.size
            }
        }

        AsyncImage(
            model = req.build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = _modifier
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.sarazan),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}