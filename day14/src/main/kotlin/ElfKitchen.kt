import extensions.digits

class ElfKitchen(private val initialScores: List<Int>) {

    data class ElfPositions(val elf1: Int, val elf2: Int)

    fun tenScoresAfterRecipeCount(count: Int): String {
        val scores = initialScores.toMutableList()
        var elf1 = 0
        var elf2 = 1

        while (scores.size < count + 10) {
            val (newElf1, newElf2) = updateScores(scores, elf1, elf2)
            elf1 = newElf1
            elf2 = newElf2
        }

        return scores.drop(count).take(10).joinToString("")
    }

    fun recipeCountBeforeScoreSequence(sequence: Int): Int {
        val scores = initialScores.toMutableList()
        var elf1 = 0
        var elf2 = 1

        val patternDigits = sequence.digits()
        val patternLength = patternDigits.size

        while (true) {
            val (newElf1, newElf2) = updateScores(scores, elf1, elf2)
            elf1 = newElf1
            elf2 = newElf2

            val tailMatchIndex = scores
                .takeLast(patternLength + 1)
                .windowed(patternLength)
                .indexOfFirst { window -> window == patternDigits }

            if (tailMatchIndex != -1) {
                return scores.count() - (patternLength + 1) + tailMatchIndex
            }
        }
    }

    private fun updateScores(scores: MutableList<Int>, elf1: Int, elf2: Int): ElfPositions {
        val score1 = scores[elf1]
        val score2 = scores[elf2]

        scores.addAll((score1 + score2).digits())

        return ElfPositions(
            elf1 = (elf1 + score1 + 1) % scores.size,
            elf2 = (elf2 + score2 + 1) % scores.size
        )
    }

}
