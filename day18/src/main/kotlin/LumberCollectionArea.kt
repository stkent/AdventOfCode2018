import AcreType.TREES
import AcreType.YARD
import extensions.elementCounts

class LumberCollectionArea(rawMap: List<String>) {

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
