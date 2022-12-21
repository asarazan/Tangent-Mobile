package social.tangent.mobile.android.compose.status

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.R
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Fave
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Reblog
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun StatusFooter(
    vm: SharedTimelineViewModel,
    status: Status,
    modifier: Modifier = Modifier
) {
    // val lottieHeart by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.favorite_heart))
    Row(
        modifier = modifier.fillMaxWidth(1f),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FooterButton(id = R.drawable.comment_regular) {}
        FooterButton(
            id = R.drawable.retweet_solid,
            color = if (status.reblogged == true) Color.Green else MaterialTheme.colors.onBackground
        ) {
            vm.send(Reblog(status, !(status.reblogged ?: false)))
        }
        FooterButton(
            id = if (status.favourited == true) R.drawable.heart_solid else R.drawable.heart_regular,
            color = if (status.favourited == true) Color.Red else MaterialTheme.colors.onBackground
        ) {
            vm.send(Fave(status, !(status.favourited ?: false)))
        }
        // LottieAnimation(composition = lottieHeart)
        FooterButton(id = R.drawable.share_from_square) {}
    }
}

@Composable
fun FooterButton(
    @DrawableRes id: Int,
    color: Color = MaterialTheme.colors.onBackground,
    onClick: () -> Unit = {}
) {
    IconButton(onClick = onClick) {
        Image(
            modifier = Modifier
                .height(48.dp)
                .padding(12.dp),
            painter = painterResource(id),
            colorFilter = ColorFilter.tint(color),
            contentDescription = null
        )
    }
}

@Preview(widthDp = 540)
@Composable
fun Preview() {
    MyApplicationTheme(darkTheme = false) {
        Surface {
            StatusFooter(PreviewModel(mockState), mockStatus)
        }
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewDark() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            StatusFooter(PreviewModel(mockState), mockStatus)
        }
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewWithActive() {
    MyApplicationTheme(darkTheme = false) {
        Surface {
            StatusFooter(
                PreviewModel(mockState),
                status = mockStatus.copy(
                    favourited = true,
                    reblogged = true
                )
            )
        }
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewDarkWithActive() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            StatusFooter(
                PreviewModel(mockState),
                status = mockStatus.copy(
                    favourited = true,
                    reblogged = true
                )
            )
        }
    }
}
