fun main() {
    val inputIpRegister = 4
    val inputProgram = resourceFile("input.txt").readLines()

    val device = Device(ipRegister = inputIpRegister)

    println("Part 1 solution: ${device.runProgram(rawProgram = inputProgram, r0Value = 0)}")

    // Target and algorithm for part 2 were found by manual parsing of instructions; see part2.txt.

    val target = 10551264
    fun sumOfFactors(target: Int) = (1..target).filter { target % it == 0 }.sum()

    println("Part 2 solution: ${sumOfFactors(target)}")
}
