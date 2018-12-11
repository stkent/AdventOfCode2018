fun main() {
    val input = resourceFile("input.txt").readLines()

    val (sky, time) = LightTracker().findMessage(rawInitialLights = input, fontHeight = 9)

    println("Part 1 solution: ")
    println(sky.joinToString("\n"))
    println()

    println("Part 2 solution: $time")
}
