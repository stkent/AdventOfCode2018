@file:Suppress("unused")

package extensions

fun String.isAnagramOf(target: String): Boolean {
    return toCharArray().sorted() == target.toCharArray().sorted()
}

fun String.isLong() = isLong(radix = 10)

fun String.isLong(radix: Int) = toLongOrNull(radix) != null

fun String.isPalindrome(ignoreWhitespace: Boolean = false): Boolean {
    val forward = if (ignoreWhitespace) replace(Regex("[^a-zA-Z]+"), "") else this
    val reverse = forward.reversed()
    return forward == reverse
}

// Rotates a String to the left.
fun String.rotate(steps: Int): String {
    val nSteps = steps.nonNegativeRem(length)
    return takeLast(length - nSteps) + take(nSteps)
}

fun String.swap(index1: Int, index2: Int): String {
    val chars = toCharArray()
    val char1 = chars[index1]
    val char2 = chars[index2]
    chars[index1] = char2
    chars[index2] = char1
    return String(chars)
}
