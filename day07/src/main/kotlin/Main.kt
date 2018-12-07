fun main() {
    val input = resourceFile("input.txt").readLines()

    val sleighKit = SleighKit(instructions = input)

    println("Part 1 solution: ${sleighKit.stepOrder}")
    println("Part 2 solution: ${sleighKit.timeToComplete(workerCount = 5)}")
}
