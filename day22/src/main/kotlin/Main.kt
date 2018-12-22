fun main() {
    val inputDepth = 8112
    val inputTarget = GridPoint2d(x = 13, y = 743)

    val cave = Cave(inputDepth, inputTarget)

    println("Part 1 solution: ${cave.risk()}")
    println("Part 2 solution: ${cave.rescueTime()}")
}
