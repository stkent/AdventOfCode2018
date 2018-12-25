import extensions.extractInts
import kotlin.math.abs

fun main() {
    val input = resourceFile("input.txt").readLines()

    val allStars = input.map {
        val ints = it.extractInts()
        return@map GridPoint4d(ints[0], ints[1], ints[2], ints[3])
    }

    val constellations = mutableSetOf<Set<GridPoint4d>>()

    while (true) {
        val availableStars = allStars - constellations.flatten()
        if (availableStars.isEmpty()) break

        val constellation = mutableSetOf(availableStars.first())

        while (true) {
            val newMembers = (availableStars - constellation)
                .filter { availableStar ->
                    constellation.any { member ->
                        member.l1DistanceTo(availableStar) <= 3
                    }
                }

            if (newMembers.isNotEmpty()) {
                constellation += newMembers
            } else {
                break
            }
        }

        constellations += constellation
    }

    println(constellations.count())
}


data class GridPoint4d(val x: Int, val y: Int, val z: Int, val w: Int) {

    fun l1DistanceTo(other: GridPoint4d): Int =
        abs(x - other.x) + abs(y - other.y) + abs(z - other.z) + abs(w - other.w)

}
