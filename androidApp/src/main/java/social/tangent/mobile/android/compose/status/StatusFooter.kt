package social.tangent.mobile.android.compose.status

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.R
import social.tangent.mobile.android.onBackgroundFaint
import social.tangent.mobile.android.util.shareStatus
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.mock.mockState
import social.tangent.mobile.api.mock.mockStatus
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
    val faved = status.favourited ?: false
    val reblogged = status.reblogged ?: false
    Row(
        modifier = modifier.fillMaxWidth(1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FooterButton(
            id = R.drawable.comment_regular,
            count = status.repliesCount
        ) {
            /* TODO */
        }
        Spacer(modifier = Modifier.weight(1f))

        FooterButton(
            id = R.drawable.retweet_solid,
            color = if (reblogged) Color.Green else MaterialTheme.colors.onBackgroundFaint,
            count = status.reblogsCount
        ) {
            vm.send(Reblog(status, !reblogged))
        }
        Spacer(modifier = Modifier.weight(1f))

        FooterButton(
            id = if (faved) R.drawable.heart_solid else R.drawable.heart_regular,
            color = if (faved) Color.Red else MaterialTheme.colors.onBackgroundFaint,
            count = status.favouritesCount
        ) {
            vm.send(Fave(status, !faved))
        }
        Spacer(modifier = Modifier.weight(1f))

        val context = LocalContext.current as Activity
        FooterButton(id = R.drawable.share_from_square) {
            shareStatus(context, status)
        }
    }
}

@Composable
fun FooterButton(
    @DrawableRes id: Int,
    color: Color = MaterialTheme.colors.onBackgroundFaint,
    count: Long = 0,
    onClick: () -> Unit = {}
) {
    IconButton(onClick = onClick) {
        Image(
            modifier = Modifier
                .height(48.dp)
                .padding(14.dp),
            painter = painterResource(id),
            colorFilter = ColorFilter.tint(color),
            contentDescription = null
        )
    }
    if (count > 0) {
        Text(text = "$count", color = color)
    }
}

@Preview(widthDp = 540)
@Composable
fun Preview() {
    MyApplicationTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colors.background) {
            StatusFooter(PreviewModel(mockState), mockStatus)
        }
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewDark() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            StatusFooter(PreviewModel(mockState), mockStatus)
        }
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewWithActive() {
    MyApplicationTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colors.background) {
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
