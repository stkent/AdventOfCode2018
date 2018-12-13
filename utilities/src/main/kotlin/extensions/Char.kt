package extensions

fun Char.repeated(times: Int): CharSequence {
    val result = CharArray(times)
    result.fill(this)
    return result.joinToString("")
}
