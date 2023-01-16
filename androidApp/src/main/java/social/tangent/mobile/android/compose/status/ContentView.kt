package social.tangent.mobile.android.compose.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import social.tangent.mobile.android.MyApplicationTheme
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
    // val text = HtmlCompat.fromHtml(status.content, HtmlCompat.FROM_HTML_MODE_LEGACY).trim()
    // val annotated = spannableStringToAnnotatedString(
    //     text = text,
    //     baseStyle = MaterialTheme.typography.body1.toSpanStyle(),
    //     density = LocalDensity.current
    // )
    // val annotated = buildAnnotatedString {
    //     append(status.content)
    //     addStyle(MaterialTheme.typography.body1.toSpanStyle().copy(color = MaterialTheme.colors.onBackground), 0, status.content.length)
    // }
    // LinkableTextView(annotatedString = annotated, modifier = modifier)

    ClickableText(text = parsed, modifier = modifier.fillMaxSize()) {
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