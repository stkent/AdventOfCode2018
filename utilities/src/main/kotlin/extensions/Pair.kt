@file:Suppress("unused")

package extensions

fun <T, U> Pair<T, U>.flip(): Pair<U, T> = Pair(second, first)

fun <T : Comparable<T>> Pair<T, T>.max() = maxOf(first, second)

fun <T : Comparable<T>> Pair<T, T>.min() = minOf(first, second)

fun <T> Pair<T, T>.valuesMatch() = first == second
