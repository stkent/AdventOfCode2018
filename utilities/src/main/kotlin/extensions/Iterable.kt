@file:Suppress("unused")

package extensions

// Adapted from stdlib proposal: https://youtrack.jetbrains.com/issue/KT-7657#comment=27-2602841
fun <T> Iterable<T>.accumulate(operation: (accumulated: T, next: T) -> T): List<T> {
    val iterator = iterator()

    if (!iterator.hasNext()) return emptyList()

    val result = mutableListOf(iterator.next())

    for (element in iterator) {
        result.add(operation(result.last(), element))
    }

    return result
}

// Adapted from stdlib proposal: https://youtrack.jetbrains.com/issue/KT-7657#comment=27-2602841
fun <T, R> Iterable<T>.accumulate(initial: R, operation: (accumulated: R, next: T) -> R): List<R> {
    val result = mutableListOf(initial)

    for (element in this) {
        result.add(operation(result.last(), element))
    }

    return result
}

fun <T> Iterable<T>.elementCounts(): Map<T, Int> {
    return groupingBy { it }.eachCount()
}

fun <T> Iterable<T>.firstRepeat() = iterator().firstRepeat()

fun <T> Iterable<T>.firstRepeat(targetCount: Int): T? {
    check(targetCount >= 3) {
        "This method cannot be called with a targetCount less than 3."
    }

    return iterator().firstRepeat(targetCount = targetCount)
}

fun <T> Iterable<T>.head() = first()

// Returns a finite Sequence that loops through the original iterable the specified number of times.
fun <T> Iterable<T>.repeat(times: Int): Sequence<T> {
    if (!iterator().hasNext()) return emptySequence()

    return sequence {
        repeat(times) { yieldAll(this@repeat) }
    }
}

// Returns an infinite Sequence that loops through the original iterable indefinitely.
fun <T> Iterable<T>.repeatIndefinitely(): Sequence<T> {
    if (!iterator().hasNext()) return emptySequence()

    return sequence {
        while (true) yieldAll(this@repeatIndefinitely)
    }
}

fun <T> Iterable<T>.tail() = drop(1)
