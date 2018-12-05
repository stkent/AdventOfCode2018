class PolymerProcessor {

    fun fullyReact(polymer: String): String {
        val replacementRegex = ('a'..'z')
            .flatMap { char ->
                val upperChar = char.toUpperCase()
                return@flatMap listOf("$char$upperChar", "$upperChar$char")
            }
            .joinToString(separator = "|").toRegex()

        var result = polymer

        while (true) {
            val updatedResult = result.replace(replacementRegex, "")
            if (updatedResult == result) break
            result = updatedResult
        }

        return result
    }

    fun optimizeAndFullyReact(polymer: String): String {
        return ('a'..'z')
            .map { char -> "$char|${char.toUpperCase()}".toRegex() }
            .map { regex -> polymer.replace(regex, "") }
            .map { s -> fullyReact(s) }
            .minBy { it.length }!!
    }

}
