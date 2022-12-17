package social.tangent.mobile.android.compose.util

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import kotlin.math.max

private const val SPACING_FIX = 3f

fun String.trimPTags(): String = trim()//.removePrefix("<p>").removeSuffix("</p>")

@Composable
fun Html(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textColor: Color = MaterialTheme.colors.onBackground
) {
    AndroidView(
        modifier = modifier,
        update = { it.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).trim() },
        factory = { context ->
            val spacingReady =
                max(textStyle.lineHeight.value - textStyle.fontSize.value - SPACING_FIX, 0f)
            val extraSpacing = spToPx(spacingReady.toInt(), context)
            val gravity = when (textStyle.textAlign) {
                TextAlign.Center -> Gravity.CENTER
                TextAlign.End -> Gravity.END
                else -> Gravity.START
            }
            TextView(context).apply {
                textSize = textStyle.fontSize.value
                setLineSpacing(extraSpacing, 1f)
                setTextColor(textColor.toArgb())
                setGravity(gravity)
            }
        })
}

fun spToPx(sp: Int, context: Context): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp.toFloat(),
        context.resources.displayMetrics
    )