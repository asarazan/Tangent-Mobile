package social.tangent.mobile.android.compose.util

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun randomColor(): Color {
    return Color(
        Random.nextFloat(),
        Random.nextFloat(),
        Random.nextFloat(),
        1f
    )
}