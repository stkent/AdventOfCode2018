fun main() {
    val input = resourceFile("input.txt").readLines()

    val device = Device()

    println("Part 1 solution: ${device.netFrequencyChange(frequencyChangeStrings = input)}")
    println("Part 2 solution: ${device.firstRepeatedFrequency(frequencyChangeStrings = input)}")
}
