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
