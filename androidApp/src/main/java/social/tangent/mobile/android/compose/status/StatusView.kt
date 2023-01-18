package social.tangent.mobile.android.compose.status

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.screenshot.ScreenshotBox
import com.smarttoolfactory.screenshot.ScreenshotState
import com.smarttoolfactory.screenshot.rememberScreenshotState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.compose.status.attachments.StatusAttachments
import social.tangent.mobile.android.compose.text.EmojiText
import social.tangent.mobile.android.compose.util.RoundedBorder
import social.tangent.mobile.android.onBackgroundFaint
import social.tangent.mobile.android.onBackgroundFainter
import social.tangent.mobile.android.util.longToast
import social.tangent.mobile.android.util.shareImage
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.mock.MockApi
import social.tangent.mobile.api.mock.mockState
import social.tangent.mobile.api.mock.mockStatus
import social.tangent.mobile.data.extensions.actionableStatus
import social.tangent.mobile.launchWebView
import social.tangent.mobile.viewmodel.SharedTimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel
import social.tangent.mobile.viewmodel.TimelineViewModel.Event.Profile
import social.tangent.mobile.viewmodel.base.PreviewModel

@Composable
fun StatusView(
    vm: SharedTimelineViewModel,
    status: Status
) {
    val screenshot = rememberScreenshotState()
    ScreenshotBox(screenshotState = screenshot) {
        StatusViewInternal(
            vm = vm,
            status = status.actionableStatus,
            outerStatus = status,
            modifier = Modifier
                .clickable {
                    vm.send(TimelineViewModel.Event.Click(status))
                }
        )
    }
    ListenForScreenshot(vm = vm, status = status, screenshot = screenshot)
}

@Composable
private fun StatusViewInternal(
    vm: SharedTimelineViewModel,
    status: Status,
    outerStatus: Status,
    modifier: Modifier = Modifier
) {
    // val json = status.serialize()
    // println("STATUS CONTENT:\n${status.serialize()}\n")
    Column(modifier = modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)) {
        BoostHeader(status = outerStatus)
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .width(72.dp)
                    .padding(end = 8.dp, bottom = 8.dp)
            ) {
                Avatar(account = status.account, modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        vm.send(Profile(status))
                    })
            }
            Column {
                EmojiText(
                    text = status.account.displayName,
                    emoji = status.account.emojis,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = "${status.account.acct} • ${status.formatTime()}",
                    color = MaterialTheme.colors.onBackgroundFaint,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false
                )
                // Html(text = status.content.trimPTags(), modifier = Modifier.fillMaxWidth())
                // Text(text = "\n-----------------\n")
                ContentView(status = status)
                // Text(text = "\n-----------------\n")
                // Text(text = status.content)
                val attachments = status.mediaAttachments
                if (attachments.isNotEmpty()) {
                    Box(modifier = Modifier.padding(top = 8.dp)) {
                        StatusAttachments(attachments)
                    }
                } else if (status.card != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    RoundedBorder(
                        thickness = 1.dp,
                        backgroundColor = MaterialTheme.colors.background,
                        color = MaterialTheme.colors.onBackgroundFainter,
                        corner = 8.dp,
                        modifier = Modifier.clickable {
                            launchWebView(status.card!!.url)
                        })
                    {
                        CardView(card = status.card!!)
                    }
                }
                StatusFooter(vm, status, outerStatus = outerStatus)
            }
        }
    }
}

@Composable
fun ListenForScreenshot(
    vm: SharedTimelineViewModel,
    status: Status,
    screenshot: ScreenshotState,
) {
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()
    LaunchedEffect("screenshot") {
        vm.sideEffectFlow.onEach {
            println("Received side effect: ${it.javaClass.simpleName}")
            when (it) {
                is TimelineViewModel.Effect.Screenshot -> {
                    if (it.status.actionableStatus == status.actionableStatus) {
                        scope.launch(Dispatchers.IO) {
                            var count = 0
                            while (screenshot.bitmap == null && count++ < 5) {
                                screenshot.capture()
                                Thread.sleep(100L)
                            }
                            if (screenshot.bitmap == null) {
                                longToast("Could not take screenshot...")
                            } else {
                                activity.shareImage(screenshot.bitmap!!)
                            }
                        }
                    }
                }
                else -> {}
            }
        }.launchIn(scope)
    }
}

@Preview(widthDp = 540)
@Composable
fun PreviewStatusViewDark() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            StatusView(
                PreviewModel(mockState),
                mockStatus.copy(reblogged = true, favourited = true)
            )
        }
    }
}



@Preview(widthDp = 540)
@Composable
fun PreviewStatusViewRTL() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            StatusView(PreviewModel(mockState), MockApi.rtlStatus)
        }
    }
}

@Preview(widthDp = 540, showBackground = true)
@Composable
fun PreviewStatusViewTall() {
    MyApplicationTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            StatusView(PreviewModel(mockState), MockApi.longStatus)
        }
    }
}
