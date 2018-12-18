import java.io.File
import kotlin.math.min

fun main() {
    val source = GridPoint2d(500, 0)

    val clay = resourceFile("input.txt")
        .readLines()
//        val clay = listOf(
//            "x=495, y=2..7",
//            "y=7, x=495..501",
//            "x=501, y=3..7",
//            "x=498, y=2..4",
//            "x=506, y=1..2",
//            "x=498, y=10..13",
//            "x=504, y=10..13",
//            "y=13, x=498..504"
//        )
        .flatMap { s ->
            val regex = """(\w)=(\d+), (\w)=(\d+)\.\.(\d+)""".toRegex()
            val (c1, num1, c2, num2, num3) = regex.matchEntire(s)!!.destructured

            if (c1 == "x") {
                return@flatMap (num2.toInt()..num3.toInt()).map { GridPoint2d(x = num1.toInt(), y = it) }
            } else {
                return@flatMap (num2.toInt()..num3.toInt()).map { GridPoint2d(x = it, y = num1.toInt()) }
            }
        }

    val minX = clay.map { it.x }.min()!!
    val maxX = clay.map { it.x }.max()!!
    val minY = min(0, clay.map { it.y }.min()!!)
    val maxY = clay.map { it.y }.max()!!

    File("map.txt").delete()

    for (y in minY..maxY) {
        for (x in minX..maxX) {
            when {
                GridPoint2d(x, y) in clay -> File("map.txt").appendText("█")
                GridPoint2d(x, y) == source -> File("map.txt").appendText("+")
                else -> File("map.txt").appendText(".")
            }
        }

        File("map.txt").appendText("\n")
    }
}

class Ground(private val rawGround: String) {

    val wettableArea: Int by lazy {
        return@lazy 0
    }

}
