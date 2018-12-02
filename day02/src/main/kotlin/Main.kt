import extensions.characterCounts
import extensions.orderedPairs

fun main() {
    val input = resourceFile("input.txt").readLines()

    // 1

    val numberTwice = input.count { string -> string.characterCounts().any { count -> count.value == 2 } }
    val numberThrice = input.count { string -> string.characterCounts().any { count -> count.value == 3 } }

    println(numberTwice * numberThrice)

    // 2

    val input2 = listOf(
        "abcde",
        "fghij",
        "klmno",
        "pqrst",
        "fguij",
        "axcye",
        "wvxyz"
    )

    println(input2.windowed(2, 1).map { Pair(it.first(), it.last()) })

    val s2 = input.orderedPairs().toSet()
        .first { pair ->
            var diffChars = 0
            for (i in 0 until pair.first.length) {
                if (pair.first[i] != pair.second[i]) {
                    diffChars++
                }
            }

            diffChars == 1
        }

    println(s2.first.toList().zip(s2.second.toList()).filter { it.first == it.second }.map { it.first }.joinToString(separator = ""))
}
