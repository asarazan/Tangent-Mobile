package social.tangent.mobile.android.html

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import org.jsoup.select.NodeVisitor
import social.tangent.mobile.api.entities.Status
import social.tangent.mobile.api.util.UrlHelpers

object LinkTypes {
    const val Profile = "profile"
    const val Hashtag = "hashtag"
    const val Status = "status"
    const val Url = "url"
}

private data class Hyperlink(
    val element: Element,
    val start: Int
)

private class TangentNodeVisitor(
    val style: SpanStyle,
    val linkStyle: SpanStyle,
    val builder: AnnotatedString.Builder
) : NodeVisitor {

    private var invisible = 0
    private var ellipsize = 0
    private var hyperlink = mutableListOf<Hyperlink>()

    init {
        builder.pushStyle(style)
    }

    override fun head(node: Node, depth: Int) {
        when (node) {
            is Element -> enterElement(node)
            is TextNode -> handleText(node)
        }
    }

    override fun tail(node: Node, depth: Int) {
        when (node) {
            is Element -> exitElement(node)
        }
    }

    private fun enterElement(el: Element) {
        when(el.nodeName()) {
            "a" -> enterHref(el)
            "span" -> enterSpan(el)
            "br" -> append("\n")
        }
    }

    private fun exitElement(el: Element) {
        when(el.nodeName()) {
            "a" -> exitHref(el)
            "span" -> exitSpan(el)
            "p" -> handleEndParagraph(el)
        }
    }

    private fun enterHref(el: Element) {
        hyperlink.add(
            Hyperlink(
                element = el,
                start = builder.length
            )
        )
    }

    @OptIn(ExperimentalTextApi::class)
    private fun exitHref(el: Element) {
        val current = hyperlink.removeLast()
        builder.addStyle(linkStyle, current.start, builder.length)
        builder.addStringAnnotation(
            tag = extractType(current.element),
            annotation = current.element.attr("href"),
            start = current.start,
            end = builder.length
        )
    }

    private fun handleEndParagraph(el: Element) {
        if (el.nextElementSibling() != null) {
            append("\n\n")
        }
    }

    private fun enterSpan(el: Element) {
        when (el.className()) {
            "invisible" -> {
                invisible++
            }
            "ellipsis" -> {
                ellipsize++
            }
        }
    }

    private fun exitSpan(el: Element) {
        when (el.className()) {
            "invisible" -> {
                invisible--
            }
            "ellipsis" -> {
                ellipsize--
            }
        }
    }

    private fun handleText(node: TextNode) {
        var text = node.text()
        if (ellipsize > 0) {
            text = "$text…"
        }
        append(text)
    }

    private fun append(text: String) {
        if (invisible == 0) builder.append(text)
    }
}

fun String.parsedContent(
    style: TextStyle,
    linkStyle: TextStyle,
): AnnotatedString {
    val body = Jsoup.parseBodyFragment(this).body()
    return buildAnnotatedString {
        val visitor = TangentNodeVisitor(
            style = style.toSpanStyle(),
            linkStyle = linkStyle.toSpanStyle(),
            builder = this
        )
        body.traverse(visitor)
    }
}

fun Status.parsedContent(
    style: TextStyle,
    linkStyle: TextStyle,
) = content.parsedContent(style, linkStyle)

private fun extractType(el: Element): String {
    val href = el.attr("href")
    val status = UrlHelpers.statusFromUrl(href)
    return when {
        status != null -> LinkTypes.Status
        el.hasClass("hashtag") -> LinkTypes.Hashtag
        el.hasClass("mention") -> LinkTypes.Profile
        else -> LinkTypes.Url
    }
}