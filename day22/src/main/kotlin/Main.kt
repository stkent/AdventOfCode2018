import java.math.BigInteger

fun main() {
    val inputDepth = 8112
    val inputTarget = GridPoint2d(x = 13, y = 743)

//    Cave(510, GridPoint2d(10, 10)).regionTypes.values.sumBy { it.ordinal }.also { println(it) }

    Cave(inputDepth, inputTarget).regionMap.values.sumBy { it.ordinal }.also { println(it) }
}

enum class RegionType {
    ROCKY, WET, NARROW
}

class Cave(private val scanDepth: Int, private val scanTarget: GridPoint2d) {

    val regionMap: Map<GridPoint2d, RegionType> by lazy {
        val result = mutableMapOf<GridPoint2d, RegionType>()

        val erosionMap = mutableMapOf<GridPoint2d, BigInteger>()

        for (y in 0..scanTarget.y) {
            for (x in 0..scanTarget.x) {
                val point = GridPoint2d(x, y)

                val geologicIndex = when {
                    point.x == 0 && point.y == 0 -> BigInteger.ZERO
                    point == scanTarget -> BigInteger.ZERO
                    point.x == 0 -> (point.y * 48271).toBigInteger()
                    point.y == 0 -> (point.x * 16807).toBigInteger()
                    else -> erosionMap[point.shiftBy(dx = -1)]!! * erosionMap[point.shiftBy(dy = -1)]!!
                }

                val erosionLevel = ((geologicIndex + scanDepth.toBigInteger()) % 20183.toBigInteger()).also {
                    erosionMap += point to it
                }

                result += point to RegionType.values()[(erosionLevel % 3.toBigInteger()).toInt()]
            }
        }

        return@lazy result
    }

    fun print() {
        for (y in 0..scanTarget.y) {
            for (x in 0..scanTarget.x) {
                when (regionMap[GridPoint2d(x, y)]) {
                    RegionType.ROCKY -> print('.')
                    RegionType.WET -> print('=')
                    RegionType.NARROW -> print('|')
                }
            }
            print('\n')
        }
    }

}
