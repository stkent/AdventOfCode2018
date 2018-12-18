import kotlin.math.max
import kotlin.math.min

fun main() {
    val spring = GridPoint2d(500, 0)

//    val clay = resourceFile("input.txt")
//        .readLines()
    val scans = listOf(
        "x=495, y=2..7",
        "y=7, x=495..501",
        "x=501, y=3..7",
        "x=498, y=2..4",
        "x=506, y=1..2",
        "x=498, y=10..13",
        "x=504, y=10..13",
        "y=13, x=498..504"
    )

    Ground.fromScan(spring, scans).prettyPrint()
}

class Ground(private val spring: GridPoint2d, private val clay: Set<GridPoint2d>) {

    companion object {
        fun fromScan(spring: GridPoint2d, scans: List<String>): Ground {
            val clay = scans.flatMap { s ->
                val regex = """(\w)=(\d+), \w=(\d+)\.\.(\d+)""".toRegex()
                val (coord, c1, c2, c3) = regex.matchEntire(s)!!.destructured
                val n1 = c1.toInt()
                val n2 = c2.toInt()
                val n3 = c3.toInt()

                return@flatMap (n2..n3).map {
                    return@map if (coord == "x") {
                        GridPoint2d(x = n1, y = it)
                    } else {
                        GridPoint2d(x = it, y = n1)
                    }
                }
            }.toSet()

            return Ground(spring, clay)
        }

        fun fromSlice(rawGround: List<String>): Ground {
            var spring: GridPoint2d? = null
            val clay = mutableSetOf<GridPoint2d>()

            rawGround.withIndex().forEach { (y, row) ->
                row.withIndex().forEach { (x, char) ->
                    when (char) {
                        '+' -> spring = GridPoint2d(x, y)
                        '█' -> clay += GridPoint2d(x, y)
                    }
                }
            }

            return Ground(spring!!, clay)
        }
    }

    val wettableArea: Int by lazy {
        return@lazy 0
    }

    private val minX: Int by lazy {
        return@lazy min(spring.x, clay.map(GridPoint2d::x).min()!!)
    }

    private val maxX: Int by lazy {
        return@lazy max(spring.x, clay.map(GridPoint2d::x).max()!!)
    }

    private val minY: Int by lazy {
        return@lazy min(spring.y, clay.map(GridPoint2d::y).min()!!)
    }

    private val maxY: Int by lazy {
        return@lazy max(spring.y, clay.map(GridPoint2d::y).max()!!)
    }

    fun prettyPrint() {
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                when {
                    GridPoint2d(x, y) in clay -> print('█')
                    GridPoint2d(x, y) == spring -> print('+')
                    else -> print('.')
                }
            }

            print('\n')
        }
    }

}
