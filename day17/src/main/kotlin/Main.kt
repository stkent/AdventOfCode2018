fun main() {
    val spring = GridPoint2d(500, 0)
    val input = resourceFile("input.txt").readLines()
//
//    val input = listOf(
//        "x=495, y=2..7",
//        "y=7, x=495..501",
//        "x=501, y=3..7",
//        "x=498, y=2..4",
//        "x=506, y=1..2",
//        "x=498, y=10..13",
//        "x=504, y=10..13",
//        "y=13, x=498..504"
//    )

    val wetAreas = Ground.fromScan(scans = input).computeWetAreas(spring = spring)

    println("Part 1 solution: ${wetAreas.settled + wetAreas.running}")
    println("Part 2 solution: ${wetAreas.settled}")
}
