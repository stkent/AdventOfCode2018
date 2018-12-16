package extensions

fun <T, U> Map<T, List<U>>.uniquePairings(): Map<T, U>? {
    val result = mutableMapOf<T, U>()
    var remaining = toMap()

    while (remaining.isNotEmpty()) {
        val (key, value) = remaining
            .filter { (_, values) -> values.count() == 1 }
            .mapValues { (_, values) -> values.first() }
            .entries
            .firstOrNull() ?: return null

        result += key to value

        remaining = remaining
            .mapValues { (_, values) -> values - value }
            .filterNot { (_, values) -> values.isEmpty() }
    }

    return result
}
