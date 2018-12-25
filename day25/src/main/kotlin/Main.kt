import extensions.extractInts
import kotlin.math.abs

fun main() {
    val input = resourceFile("input.txt").readLines()
        .map { val ints = it.extractInts(); GridPoint4d(ints[0], ints[1], ints[2], ints[3]) }
//        .also { println(it.joinToString("\n")) }

    println(listOf(GridPoint4d(x=0, y=0, z=0, w=0), GridPoint4d(x=3, y=0, z=0, w=0), GridPoint4d(x=0, y=3, z=0, w=0), GridPoint4d(x=0, y=0, z=3, w=0), GridPoint4d(x=0, y=0, z=0, w=3), GridPoint4d(x=0, y=0, z=0, w=6)).any { it.l1DistanceTo(GridPoint4d(x=12, y=0, z=0, w=0)) <= 3 })

    val constellations = mutableSetOf<Set<GridPoint4d>>()

    while (true) {
        val remaining = input - constellations.flatten()
        if (remaining.isEmpty()) break

        val seed = remaining.first()
        val newConstellation = mutableSetOf(seed)

        while (true) {
            var didExpandConstellation = false

            val newjoins = (remaining - newConstellation).filter { unassigned -> newConstellation.any { it.l1DistanceTo(unassigned) <= 3 } }

            if (newjoins.isNotEmpty()) {
                newConstellation += newjoins
                didExpandConstellation = true
            }

            if (!didExpandConstellation) break
        }

        constellations += newConstellation
        println(constellations.count())
    }

    println(constellations.count())
}


data class GridPoint4d(val x: Int, val y: Int, val z: Int, val w: Int) {

    fun l1DistanceTo(other: GridPoint4d): Int =
        abs(x - other.x) + abs(y - other.y) + abs(z - other.z) + abs(w - other.w)

}