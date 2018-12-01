@file:Suppress("unused")

package extensions

import com.google.common.collect.HashMultiset
import com.google.common.collect.Multiset

fun <T> Collection<T>.allDistinct(): Boolean {
    check(isNotEmpty()) {
        "This method cannot be called on empty Collections."
    }

    val seen = mutableSetOf<T>()

    for (element in this) if (!seen.add(element)) return false
    return true
}

fun <T> Collection<T>.allMatch(): Boolean {
    check(isNotEmpty()) {
        "This method cannot be called on empty Collections."
    }

    return toSet().size == 1
}

fun <T> Collection<T>.anyDistinct(): Boolean {
    check(isNotEmpty()) {
        "This method cannot be called on empty Collections."
    }

    return !allMatch()
}

fun <T> Collection<T>.anyRepeat(): Boolean {
    check(isNotEmpty()) {
        "This method cannot be called on empty Collections."
    }

    return !allDistinct()
}

fun <T> Collection<T>.highestFrequencyElements(): Set<T> {
    if (isEmpty()) return emptySet()

    val elementCounts = elementCounts()
    val highestFrequency = elementCounts.map { it.value }.max()

    return elementCounts
            .filter { it.value == highestFrequency }
            .map { it.key }
            .toSet()
}

fun <T> Collection<T>.lowestFrequencyElements(): Set<T> {
    if (isEmpty()) return emptySet()

    val elementCounts = elementCounts()
    val lowestFrequency = elementCounts.map { it.value }.min()

    return elementCounts
            .filter { it.value == lowestFrequency }
            .map { it.key }
            .toSet()
}

fun <T> Collection<T>.orderedPairs(): Multiset<Pair<T, T>> {
    val unorderedPairs = unorderedPairs()

    val result = HashMultiset.create<Pair<T, T>>()

    unorderedPairs.forEach {
        val orderedPair = Pair(it.first(), it.last())
        result.add(orderedPair)
        result.add(orderedPair.flip())
    }

    return result
}

fun <T> Collection<T>.permutations(): Set<List<T>> {
    if (isEmpty()) return emptySet()

    if (size == 1) return setOf(listOf(first()))

    val result = mutableSetOf<List<T>>()

    for (element in this) {
        (this - element).permutations().forEach {
            val resultList = mutableListOf(element)
            resultList.addAll(it)
            result.add(resultList)
        }
    }

    return result
}

fun <T> Collection<T>.unorderedPairs(): Multiset<Set<T>> {
    val result = HashMultiset.create<Set<T>>()

    if (size < 2) return result

    if (size == 2) {
        result.add(setOf(first(), last()))
        return result
    }

    val head = head()
    val tail = tail()
    result.addAll(tail.map { setOf(head, it) })
    result.addAll(tail.unorderedPairs())
    return result
}
