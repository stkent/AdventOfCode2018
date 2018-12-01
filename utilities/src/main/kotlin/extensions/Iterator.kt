@file:Suppress("unused")

package extensions

import com.google.common.collect.HashMultiset

fun <T> Iterator<T>.firstRepeat(targetCount: Int = 2): T? {
    check(targetCount >= 2) {
        "This method cannot be called with a targetCount less than 2."
    }

    val seen = HashMultiset.create<T>()

    for (next in this) {
        if (seen.count(next) + 1 == targetCount) {
            return next
        }

        seen.add(next)
    }

    return null
}
