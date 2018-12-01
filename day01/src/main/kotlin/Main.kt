import extensions.accumulate
import extensions.firstRepeat
import extensions.loopForever

fun main() {
    val input = resourceFile("input.txt").readLines()

    val frequencyShifts = input.map { Integer.parseInt(it) }

    println("part1: ${frequencyShifts.sum()}")

    val part2 = frequencyShifts
        .loopForever()
        .accumulate(Int::plus)
        .firstRepeat()

    println("part2: $part2")
}
