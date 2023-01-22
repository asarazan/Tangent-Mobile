package social.tangent.mobile.android.compose.status

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import social.tangent.mobile.android.MyApplicationTheme
import social.tangent.mobile.android.activity.AccountActivity
import social.tangent.mobile.android.compose.text.AnnotatedClickableText
import social.tangent.mobile.android.compose.text.rememberEmojiMap
import social.tangent.mobile.android.html.LinkTypes.Hashtag
import social.tangent.mobile.android.html.LinkTypes.Profile
import social.tangent.mobile.android.html.LinkTypes.Status
import social.tangent.mobile.android.html.emojify
import social.tangent.mobile.android.html.parsedContent
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.mock.mockStatus
import social.tangent.mobile.launchWebView

@OptIn(ExperimentalTextApi::class)
@Composable
fun ContentView(
    status: Status,
    modifier: Modifier = Modifier
) {
    val style = MaterialTheme.typography.body1
    val parsed = status.parsedContent(
        style = style.copy(
            color = MaterialTheme.colors.onBackground
        ),
        linkStyle = style.copy(
            color = MaterialTheme.colors.secondaryVariant,
            textDecoration = TextDecoration.Underline
        )
    )
    val emojified = parsed.emojify(status.emojis)
    val contentMap = rememberEmojiMap(emoji = status.emojis)
    val activity = LocalContext.current as Activity
    AnnotatedClickableText(
        text = emojified,
        // color = MaterialTheme.colors.onBackground,
        modifier = modifier.fillMaxSize(),
        inlineContent = contentMap
    ) {
        parsed.getStringAnnotations(it, it).firstOrNull()?.let {
            annotation ->
            when (annotation.tag) {
                Profile -> {
                    activity.startActivity(
                        AccountActivity.create(activity, "", "")
                    )
                }
                Hashtag -> {
                    // TODO
                }
                Status -> {
                    // TODO
                }
                else -> { launchWebView(annotation.item) }
            }
        }
        parsed.getUrlAnnotations(it, it).firstOrNull()?.let {
            annotation ->
            launchWebView(annotation.item.url)
        }
    }
}


@Preview(widthDp = 540)
@Composable
fun PreviewContentView() {
    MyApplicationTheme(darkTheme = true) {
        Surface {
            ContentView(status = mockStatus)
        }
    }
}