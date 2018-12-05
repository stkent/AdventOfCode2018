fun main() {
    val input = resourceFile("input.txt").readLines().first()

    val polymerProcessor = PolymerProcessor()

    println("Part 1 solution: ${polymerProcessor.fullyReact(polymer = input).length}")
    println("Part 2 solution: ${polymerProcessor.optimizeAndFullyReact(polymer = input).length}")
}
