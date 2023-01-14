package social.tangent.mobile.android.compose.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.launch

private data class ScrollState(var isScrolling: Boolean = false, var initialized: Boolean = false)

fun Modifier.rememberScroll(key: String, state: LazyListState): Modifier = composed {
    val settings = rememberSettings()
    val scope = rememberCoroutineScope()
    val scrollState = remember {
        ScrollState()
    }
    if (!scrollState.initialized) {
        scrollState.initialized = true
        val index = remember { settings.getIntOrNull("__rememberScroll_index_${key}") }
        val offset = remember { settings.getIntOrNull("__rememberScroll_offset_${key}") }
        if (index != null && offset != null) {
            SideEffect {
                scope.launch {
                    state.scrollToItem(index, offset)
                }
            }
        }
    }

    val shouldSave = scrollState.isScrolling && !state.isScrollInProgress
    if (shouldSave) {
        LaunchedEffect(state) {
            val it = Pair(state.firstVisibleItemIndex, state.firstVisibleItemScrollOffset)
            settings.putInt("__rememberScroll_index_${key}", it.first)
            settings.putInt("__rememberScroll_offset_${key}", it.second)
        }
    }
    scrollState.isScrolling = state.isScrollInProgress
    this
}