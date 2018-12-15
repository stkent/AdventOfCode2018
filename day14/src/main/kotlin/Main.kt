fun main() {
    val input = 503761

    val elfKitchen = ElfKitchen(initialScores = listOf(3, 7))

    println("Part 1 solution: ${elfKitchen.tenScoresAfterRecipeCount(count = input)}")
    println("Part 2 solution: ${elfKitchen.recipeCountBeforeScoreSequence(sequence = input)}")
}
