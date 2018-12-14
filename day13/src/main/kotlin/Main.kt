fun main() {
    val input = resourceFile("input.txt").readLines()

//    val input = listOf(
//        "|",
//        "v",
//        "|",
//        "|",
//        "|",
//        "^",
//        "|"
//    )

//    val input = listOf(
//        """/->-\        """,
//        """|   |  /----\""",
//        """| /-+--+-\  |""",
//        """| | |  | v  |""",
//        """\-+-/  \-+--/""",
//        """\------/     """
//    )

//    val input = listOf(
//        """/>-<\  """,
//        """|   |  """,
//        """| /<+-\""",
//        """| | | v""",
//        """\>+</ |""",
//        """  |   ^""",
//        """  \<->/"""
//    )

//    val input = listOf(
//        ">-<"
//    )

//    val input = listOf(
//        "><"
//    )

    val mine = Mine(rawMap = input)

    val crashData = mine.computeCrashData()

    println("Part 1 solution: ${crashData.crashes.first().formatForSubmission()}")
    println("Part 2 solution: ${crashData.lastCart.formatForSubmission()}")
}

fun GridPoint2d?.formatForSubmission(): String? {
    return this?.let { "${it.x},${it.y}" } ?: "N/A"
}
