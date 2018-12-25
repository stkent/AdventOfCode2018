import extensions.extractInts
import extensions.partitionBy

fun main() {
    val input = resourceFile("input.txt").readLines()

    val allStars = input.map {
        val ints = it.extractInts()
        return@map GridPoint4d(ints[0], ints[1], ints[2], ints[3])
    }.toSet()

    println(allStars.partitionBy(connected = { a, b -> a.l1DistanceTo(b) <= 3 }).count())
}
