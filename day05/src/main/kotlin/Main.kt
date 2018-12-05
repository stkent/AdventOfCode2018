fun main() {
    val input = resourceFile("input.txt").readLines().first()



    println(fullyReact(input).length)

    val l = ('a'..'z')
        .map { char -> "$char|${char.toUpperCase()}".toRegex() }
        .map { regex -> input.replace(regex, "") }
        .map { s -> fullyReact(s) }
        .minBy { it.length }

    println(l!!.length)

//    var output = input
//
//    while (output.contains())
}

fun fullyReact(s: String): String {
    val replacementRegex = ('a'..'z')
        .flatMap { char ->
            val upperChar = char.toUpperCase()
            return@flatMap listOf("$char$upperChar", "$upperChar$char")
        }
        .joinToString(separator = "|").toRegex()

    var result = s

    while (true) {
        val updatedResult = result.replace(replacementRegex, "")
        if (updatedResult == result) break
        result = updatedResult
    }

    return result
}