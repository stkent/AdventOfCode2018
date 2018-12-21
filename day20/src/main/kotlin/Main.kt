fun main() {
    val input = resourceFile("cleaned_input.txt").readLines().first()

    val bestPathLengths = Router().computeBestPathLengths(regex = input)

    println("Part 1 solution: ${bestPathLengths.values.max()}")
    println("Part 2 solution: ${bestPathLengths.count { it.value >= 1000 }}")
}
