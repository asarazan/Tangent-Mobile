package social.tangent.mobile.android.compose.status

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.R
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.fakes.urls

@Composable
fun PreviewableImage(
    url: String,
    modifier: Modifier = Modifier,
    @DrawableRes fallback: Int = R.drawable.sarazan
) {
    Icons.Rounded
    if (LocalInspectionMode.current) {
        Image(
            painter = painterResource(id = fallback),
            contentDescription = null,
            modifier = modifier.clip(CircleShape)
        )
    } else {
        // AsyncImage(url, contentDescription = null)
    }
}

@Composable
fun Avatar(
    status: Status,
    modifier: Modifier = Modifier
) {
    val actual = status.reblog ?: status
    val url = actual.account.avatar
    PreviewableImage(url, modifier = modifier)
    // TODO secondary...
}

@Preview
@Composable
fun TryImage() {
    MyApplicationTheme {
        PreviewableImage(url = urls.avatar)
    }
}
