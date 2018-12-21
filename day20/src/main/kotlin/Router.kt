import java.util.*
import kotlin.math.min

class Router {
    fun computeBestPathLengths(regex: CharSequence): Map<GridPoint2d, Int> {
        var currentPoint = GridPoint2d.origin
        val bestPathLengths = mutableMapOf(currentPoint to 0)
        val branchPoints = ArrayDeque<GridPoint2d>()

        for (char in regex) {
            when (char) {
                '(' -> branchPoints.addLast(currentPoint)
                ')' -> currentPoint = branchPoints.pollLast()
                '|' -> currentPoint = branchPoints.peekLast()

                else -> {
                    val nextPoint = currentPoint + Direction.valueOf("$char").toVector()

                    val newPathLength = bestPathLengths[currentPoint]!! + 1
                    val oldBestPathLength = bestPathLengths[nextPoint]
                    val newBestPathLength = min(newPathLength, (oldBestPathLength ?: Int.MAX_VALUE))

                    bestPathLengths[nextPoint] = newBestPathLength
                    currentPoint = nextPoint
                }
            }
        }

        return bestPathLengths
    }
}
