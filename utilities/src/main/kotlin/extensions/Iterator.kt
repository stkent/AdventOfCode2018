@file:Suppress("unused")

package extensions

import com.google.common.collect.HashMultiset

fun <T> Iterator<T>.firstRepeat(): T? {
    val seen = mutableSetOf<T>()

    for (element in this) {
        if (!seen.add(element)) return element
    }

    return null
}

fun <T> Iterator<T>.firstRepeat(targetCount: Int): T? {
    check(targetCount >= 3) {
        "This method cannot be called with a targetCount less than 3."
    }

    val seen = HashMultiset.create<T>()

    for (element in this) {
        if (seen.count(element) + 1 == targetCount) {
            return element
        }

        seen.add(element)
    }

    return null
}

fun <T> Iterator<T>.firstRepeatIndex(): Int? {
    val seen = mutableSetOf<T>()

    for (element in withIndex()) {
        if (!seen.add(element.value)) return element.index
    }

    return null
}

fun <T> Iterator<T>.firstRepeatIndex(targetCount: Int): Int? {
    check(targetCount >= 3) {
        "This method cannot be called with a targetCount less than 3."
    }

    val seen = HashMultiset.create<T>()

    for (element in withIndex()) {
        if (seen.count(element.value) + 1 == targetCount) {
            return element.index
        }

        seen.add(element.value)
    }

    return null
}
