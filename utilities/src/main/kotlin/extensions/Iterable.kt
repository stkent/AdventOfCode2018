@file:Suppress("unused")

package extensions

// Adapted from stdlib proposal: https://youtrack.jetbrains.com/issue/KT-7657#comment=27-2602841
fun <T> Iterable<T>.accumulate(operation: (accumulated: T, next: T) -> T): List<T> {
    val iterator = iterator()

    if (!iterator.hasNext()) return emptyList()

    val result = mutableListOf(iterator.next())
    for (next in iterator) result.add(operation(result.last(), next))
    return result
}

// Adapted from stdlib proposal: https://youtrack.jetbrains.com/issue/KT-7657#comment=27-2602841
fun <T, R> Iterable<T>.accumulate(initial: R, operation: (accumulated: R, next: T) -> R): List<R> {
    val iterator = iterator()

    if (!iterator.hasNext()) return emptyList()

    val result = mutableListOf(initial)
    for (next in iterator) result.add(operation(result.last(), next))
    return result
}

fun <E> Iterable<E>.elementCounts(): Map<E, Int> {
    return groupingBy { it }.eachCount()
}

fun <T> Iterable<T>.firstRepeat(targetCount: Int = 2): T? {
    check(targetCount >= 2) {
        "This method cannot be called with a targetCount less than 2."
    }

    return iterator().firstRepeat(targetCount)
}

fun <T> Iterable<T>.head() = first()

// Returns a finite Sequence that loops through the original list the specified number of times.
fun <E> Iterable<E>.loop(times: Int): Sequence<E> {
    return sequence {
        repeat(times) { yieldAll(this@loop) }
    }
}

// Returns an infinite Sequence that loops through the original (finite) list indefinitely.
fun <E> Iterable<E>.loopForever(): Sequence<E> {
    return sequence {
        while (true) yieldAll(this@loopForever)
    }
}

fun <T> Iterable<T>.tail() = drop(1)
