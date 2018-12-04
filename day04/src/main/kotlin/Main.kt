fun main() {
    val input = resourceFile("input.txt").readLines()

    val guardAnalyzer = GuardAnalyzer(rawGuardEvents = input)

    val (guardId1, minute1) = guardAnalyzer.sleepiestGuard1()
    val (guardId2, minute2) = guardAnalyzer.sleepiestGuard2()

    println("Part 1 solution: ${guardId1 * minute1}")
    println("Part 2 solution: ${guardId2 * minute2}")
}
