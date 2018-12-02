import extensions.charCounts
import extensions.unorderedPairs
import extensions.valuesMatch

fun main() {
    val input = resourceFile("input.txt").readLines()

    // 1

    val doubleCharCount = input.count { id -> id.charCounts().any { it.value == 2 } }
    val tripleCharCount = input.count { id -> id.charCounts().any { it.value == 3 } }

    println(doubleCharCount * tripleCharCount)

    // 2

    val idLength = input.first().length

    val s2 = input.unorderedPairs()
        .keys
        .asSequence()
        .map { idPair -> commonString(idPair.first, idPair.second) }
        .first { commonString -> commonString.length == idLength - 1 }

    println(s2)
}

fun commonString(left: String, right: String): String {
    return left.zip(right)
        .filter(Pair<Char, Char>::valuesMatch)
        .map(Pair<Char, Char>::first)
        .joinToString(separator = "")
}
