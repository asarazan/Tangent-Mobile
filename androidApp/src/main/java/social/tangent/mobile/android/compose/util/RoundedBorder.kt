package social.tangent.mobile.android.compose.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun RoundedBorder(
    thickness: Dp,
    backgroundColor: Color,
    color: Color,
    corner: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(modifier = modifier.clip(RoundedCornerShape(corner)), color = color) {
        Surface(modifier = Modifier.fillMaxSize().padding(thickness).clip(RoundedCornerShape(corner)), color = backgroundColor) {
            content()
        }
    }
}