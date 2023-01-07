package social.tangent.mobile.android.compose.images

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import social.tangent.mobile.android.R

@Composable
fun PreviewableImage(
    url: String,
    modifier: Modifier = Modifier,
    placeholder: Bitmap? = null
) {
    if (LocalInspectionMode.current) {
        if (placeholder != null) {
            Image(
                bitmap = placeholder.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.sarazan),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .placeholder(BitmapDrawable(placeholder))
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}