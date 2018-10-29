@file:Suppress("unused")

package extensions

/**
 * Removes the specified keys and their corresponding values from this map.
 *
 * Based on MutableMap<K, V>.remove(K) and MutableCollection<E>.extensions.removeAll(Collection<E>).
 *
 * @return `true` if any of the specified keys were removed from the map, `false` if the map was not modified.
 */
fun <K, V : Any> MutableMap<K, V>.removeAll(keys: Iterable<K>): Boolean {
    return keys.mapNotNull { remove(it) }.isNotEmpty()
}
