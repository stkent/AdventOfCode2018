fun main() {
    val input = resourceFile("input.txt").readLines()

    val lumberCollectionArea = LumberCollectionArea(rawMap = input)

    val countsAtTime10 = lumberCollectionArea.countsAtTime(10)
    println("Part 1 solution: ${countsAtTime10.trees * countsAtTime10.yard}")

    // Closed-form solution for part 2 was based on inspection of resource value as a function of time; see part2.png.

    val cycle = listOf(
        189501, // 971
        189720,
        187040,
        189057,
        189044,
        190046,
        189001,
        192764,
        192192,
        194029,
        193688,
        195836,
        193795,
        194256,
        194820,
        195160,
        195702,
        198702,
        200208,
        201492,
        201722,
        198950,
        196308,
        196196,
        194598,
        193795,
        191760,
        191520
    )

    println("Part 2 solution: ${cycle[(1000000000 - 971) % cycle.size]}")
}

