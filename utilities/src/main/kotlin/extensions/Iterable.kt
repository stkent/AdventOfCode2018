@file:Suppress("unused")

package extensions

fun <T> Iterable<T>.elementCounts(): Map<T, Int> {
    return groupingBy { it }.eachCount()
}

fun <T> Iterable<T>.head() = first()

fun <T> Iterable<T>.tail() = drop(1)
