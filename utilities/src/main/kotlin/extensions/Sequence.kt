@file:Suppress("unused")

package extensions

fun <T> Sequence<T>.accumulate(operation: (accumulated: T, next: T) -> T): Sequence<T> {
    val iterator = iterator()

    if (!iterator.hasNext()) return emptySequence()

    return sequence {
        var accumulated = iterator.next().also { yield(it) }

        for (next in iterator) {
            accumulated = operation(accumulated, next)
            yield(accumulated)
        }
    }
}

fun <T> Sequence<T>.firstRepeat(targetCount: Int = 2): T? {
    check(targetCount >= 2) {
        "This method cannot be called with a targetCount less than 2."
    }

    return iterator().firstRepeat(targetCount)
}
