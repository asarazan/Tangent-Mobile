package social.tangent.mobile.android.compose.status

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.R
import social.tangent.mobile.api.entities.Account
import social.tangent.mobile.api.mock.MockApi

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun _PreviewableImage(
    url: String,
    modifier: Modifier = Modifier,
    @DrawableRes fallback: Int = R.drawable.sarazan
) {
    if (LocalInspectionMode.current) {
        Image(
            painter = painterResource(id = fallback),
            contentDescription = null,
            modifier = modifier.fillMaxSize().clip(CircleShape)
        )
    } else {
        GlideImage(
            model = url,
            contentDescription = null,
            modifier = modifier.fillMaxSize().clip(CircleShape)
        )
        // AsyncImage(
        //     model = ImageRequest.Builder(LocalContext.current)
        //         .data(url)
        //         .crossfade(true)
        //         .build(),
        //     contentDescription = null,
        //     modifier = modifier.fillMaxSize().clip(CircleShape)
        // )
    }
}

@Composable
fun Avatar(
    account: Account,
    modifier: Modifier = Modifier
) {
    val url = account.avatar
    Box(modifier = modifier.fillMaxWidth().aspectRatio(1f)) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.onBackground.copy(alpha = 0.1f)))
        _PreviewableImage(url)
    }
    // TODO secondary...
}

@Preview(widthDp = 256, heightDp = 512)
@Composable
fun TryImage() {
    MyApplicationTheme(darkTheme = true) {
        Avatar(MockApi.fakeStatus.account)
    }
}
