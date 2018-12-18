fun main() {
    val spring = GridPoint2d(500, 0)

//    val clay = resourceFile("input.txt")
//        .readLines()
//    val scans = listOf(
//        "x=495, y=2..7",
//        "y=7, x=495..501",
//        "x=501, y=3..7",
//        "x=498, y=2..4",
//        "x=506, y=1..2",
//        "x=498, y=10..13",
//        "x=504, y=10..13",
//        "y=13, x=498..504"
//    )
//
//    Ground.fromScan(spring, scans).prettyPrint()

    Ground.fromSlice(
        listOf(
            "...+..",
            "█....█",
            "......",
            ".█....",
            ".█..█.",
            ".████.",
            "......"
        )
    ).wettableArea(spring = GridPoint2d(x = 3, y = 0)).also { println("result = $it") }
}
