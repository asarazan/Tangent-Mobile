package social.tangent.mobile.android.compose.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

private data class ScrollState(var isScrolling: Boolean = false, var initialized: Boolean = false)

fun Modifier.rememberScroll(key: String, state: LazyListState): Modifier = composed {
    val settings = rememberSettings()
    val scrollState = remember {
        ScrollState()
    }
    if (!scrollState.initialized) {
        scrollState.initialized = true
        val index = settings.getIntOrNull("__rememberScroll_index_${key}")
        val offset = settings.getIntOrNull("__rememberScroll_offset_${key}")
        if (index != null && offset != null) {
            LaunchedEffect(state) {
                state.scrollToItem(index, offset)
            }
        }
    }

    if (scrollState.isScrolling && !state.isScrollInProgress) {
        LaunchedEffect(state) {
            settings.putInt("__rememberScroll_index_${key}", state.firstVisibleItemIndex)
            settings.putInt("__rememberScroll_offset_${key}", state.firstVisibleItemScrollOffset)
        }
    }
    scrollState.isScrolling = state.isScrollInProgress
    this
}