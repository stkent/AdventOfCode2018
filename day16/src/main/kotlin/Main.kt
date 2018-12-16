fun main() {
    val samplesInput = resourceFile("input_samples.txt").readLines()
    val programInput = resourceFile("input_program.txt").readLines()

    val device = Device(rawSamples = samplesInput)

    println("Part 1 solution: ${device.mostAmbiguousSamples()}")
    println("Part 2 solution: ${device.runProgram(rawProgram = programInput)}")
}
