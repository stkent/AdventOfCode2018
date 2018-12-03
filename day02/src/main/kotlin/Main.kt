fun main() {
    val input = resourceFile("input.txt").readLines()

    val boxIdsWrapper = BoxIdsWrapper(boxIds = input)

    println("Part 1 solution: ${boxIdsWrapper.checksum}")
    println("Part 2 solution: ${boxIdsWrapper.prototypeBoxIdsCommonChars}")
}
