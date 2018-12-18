import AcreType.TREES
import AcreType.YARD
import extensions.elementCounts

fun main() {
    val input = resourceFile("input.txt").readLines()
    val input2 = listOf(
        ".#.#...|#.",
        ".....#|##|",
        ".|..|...#.",
        "..|#.....#",
        "#.#|||#|#|",
        "...#.||...",
        ".|....|...",
        "||...#|.#|",
        "|.||||..|.",
        "...#.|..|."
    )

    val counts = LumberYard(rawMap = input).countsAtTime(10)
    println(counts.trees * counts.yard)

    val cycle = listOf(
        189501, // 971
        189720,
        187040,
        189057,
        189044,
        190046,
        189001,
        192764,
        192192,
        194029,
        193688,
        195836,
        193795,
        194256,
        194820,
        195160,
        195702,
        198702,
        200208,
        201492,
        201722,
        198950,
        196308,
        196196,
        194598,
        193795,
        191760,
        191520
    )

    println(cycle[(1000000000 - 971) % cycle.size])
}

class LumberYard(rawMap: List<String>) {

    data class AcreTypeCounts(val trees: Int, val yard: Int)

    private val initialMap: Map<GridPoint2d, AcreType> by lazy {
        val result = mutableMapOf<GridPoint2d, AcreType>()

        rawMap.forEachIndexed { y, row ->
            row.forEachIndexed { x, char ->
                result += GridPoint2d(x, y) to AcreType.fromChar(char)
            }
        }

        return@lazy result
    }

    fun countsAtTime(minutes: Int): AcreTypeCounts {
        var currentMap = initialMap

        repeat(minutes) {
            val newMap = mutableMapOf<GridPoint2d, AcreType>()

            for ((acre, currentType) in currentMap) {
                val neighborTypes = acre.surroundingPoints().mapNotNull { neighbor ->
                    currentMap.getOrDefault(neighbor, null)
                }

                val newType = currentType.evolve(neighborTypes)
                newMap += acre to newType
            }

            currentMap = newMap
        }

        val typeMap = currentMap.values.elementCounts()
        return AcreTypeCounts(trees = typeMap[TREES]!!, yard = typeMap[YARD]!!)
    }

}

enum class AcreType {

    OPEN, TREES, YARD;

    companion object {
        fun fromChar(char: Char): AcreType {
            return when (char) {
                '.' -> OPEN
                '|' -> TREES
                '#' -> YARD
                else -> throw IllegalStateException("Unknown acre type")
            }
        }
    }

    fun evolve(neighborTypes: List<AcreType>): AcreType {
        return when (this) {
            OPEN -> {
                if (neighborTypes.count { it == TREES } >= 3) TREES else OPEN
            }

            TREES -> {
                if (neighborTypes.count { it == YARD } >= 3) YARD else TREES
            }

            YARD -> {
                if (neighborTypes.count { it == YARD } >= 1 &&
                    neighborTypes.count { it == TREES } >= 1) YARD else OPEN
            }
        }
    }

}
