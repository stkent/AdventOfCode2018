import extensions.extractInts
import kotlin.math.abs
import kotlin.math.max

fun main() {
    val input = resourceFile("input.txt").readLines()

//    val input = listOf(
//        "pos=<10,12,12>, r=2",
//        "pos=<12,14,12>, r=2",
//        "pos=<16,12,12>, r=4",
//        "pos=<14,14,14>, r=6",
//        "pos=<50,50,50>, r=200",
//        "pos=<10,10,10>, r=5"
//    )

    val pInput = input.map {
        val i = it.extractInts()
        GridPoint3d(i[0], i[1], i[2]) to i[3]
    }.toMap()

    println(pInput)

    val largest = pInput.maxBy { it.value }!!

    println(pInput.filter { it.key.l1DistanceTo(largest.key) <= largest.value}.count())

    val minX = pInput.map { it.key.x }.min()!!
    val maxX = pInput.map { it.key.x }.max()!!
    val minY = pInput.map { it.key.y }.min()!!
    val maxY = pInput.map { it.key.y }.max()!!
    val minZ = pInput.map { it.key.z }.min()!!
    val maxZ = pInput.map { it.key.z }.max()!!

    println(abs((maxX - minX) * (maxY - minY) * (maxZ - minZ)))

    var maxC = Int.MIN_VALUE
    val maxP = mutableSetOf<GridPoint3d>()

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            for (z in minZ..maxZ) {
                val p = GridPoint3d(x, y, z)
                val c = pInput.count { it.key.l1DistanceTo(p) <= it.value }
                if (c > maxC) {
                    maxC = max(maxC, c)
                    maxP.clear()
                    maxP += p
                } else if (c == maxC) {
                    maxP += p
                }
            }
        }
    }

    println(maxP.minBy { it.l1DistanceTo(GridPoint3d.origin) })
}
