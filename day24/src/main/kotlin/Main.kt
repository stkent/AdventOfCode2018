fun main() {
    val goodInput = resourceFile("immune_system_input.txt").readLines()
    val evilInput = resourceFile("infection_input.txt").readLines()

    // Part 1

    val battle = Battle(rawGoodGroups = goodInput, rawEvilGroups = evilInput)

    println("Part 1 solution: ${(battle.execute() as Battle.Result.Win).liveUnits}")

    // Part 2

    val leastBoostedGoodWin = generateSequence(1, Int::inc)
        .mapNotNull { boost ->
            val boostedBattle = Battle(
                rawGoodGroups = goodInput,
                rawEvilGroups = evilInput,
                goodBoost = boost
            )

            return@mapNotNull boostedBattle.execute() as? Battle.Result.Win
        }
        .first { result -> result.good }

    println("Part 2 solution: ${leastBoostedGoodWin.liveUnits}")
}
