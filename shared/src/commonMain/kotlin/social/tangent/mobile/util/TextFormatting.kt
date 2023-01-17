package social.tangent.mobile.util

private val suffixes = listOf("", "k", "m", "b", "t")
fun Long.shortFormat(): String {
    var index = 0
    var mult = 1_000
    var whole = this
    while (this / mult > 0) {
        index++
        whole = this / mult
        mult *= 1_000
    }
    if (index == 0) return "$this"
    val decimal = "${this % mult}".firstOrNull() ?: ""
    val suffix = suffixes[index]
    return "$whole.$decimal$suffix"
}