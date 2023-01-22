package social.tangent.mobile.util

import kotlinx.coroutines.flow.MutableStateFlow

fun MutableStateFlow<Int>.increment() {
    var result = false
    while (!result) {
        result = compareAndSet(value, value + 1)
    }
}

fun MutableStateFlow<Int>.decrement() {
    var result = false
    while (!result) {
        result = compareAndSet(value, value - 1)
    }
}

inline fun MutableStateFlow<Int>.loadOn(
    max: Int? = null,
    fn: () -> Unit
): Boolean {
    var result = false
    try {
        increment()
        if (max == null || value <= max) {
            fn()
            result = true
        }
    } finally {
        decrement()
    }
    return result
}