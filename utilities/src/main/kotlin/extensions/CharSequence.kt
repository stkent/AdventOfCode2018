@file:Suppress("unused")

package extensions

fun CharSequence.allDistinct(): Boolean {
    check(isNotEmpty()) {
        "This method cannot be called on empty CharSequences."
    }

    return length == toSet().size
}

fun CharSequence.allMatch(): Boolean {
    check(isNotEmpty()) {
        "This method cannot be called on empty CharSequences."
    }

    return toSet().size == 1
}

fun CharSequence.anyRepeat(): Boolean {
    check(isNotEmpty()) {
        "This method cannot be called on empty CharSequences."
    }

    return !allDistinct()
}

fun CharSequence.characterCounts(): Map<Char, Int> {
    return groupingBy { it }.eachCount()
}
