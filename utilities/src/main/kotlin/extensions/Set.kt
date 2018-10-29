@file:Suppress("unused")

package extensions

fun <E> Set<E>.powerSet(): Set<Set<E>> {
    return when (size) {
        0 -> setOf(emptySet())
        1 -> setOf(this, emptySet())

        else -> {
            val anyElement = iterator().next()

            val exclusiveRemainderPowerSet = minus(anyElement).powerSet()

            val inclusiveRemainderPowerSet = emptySet<Set<E>>().toMutableSet()
            exclusiveRemainderPowerSet.mapTo(inclusiveRemainderPowerSet) { it.union(setOf(anyElement)) }

            return exclusiveRemainderPowerSet.union(inclusiveRemainderPowerSet)
        }
    }
}
