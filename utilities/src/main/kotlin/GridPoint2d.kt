@file:Suppress("unused", "MemberVisibilityCanBePrivate")

import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max

data class GridPoint2d(val x: Int, val y: Int) {

    companion object {
        val origin = GridPoint2d(0, 0)
    }

    operator fun plus(vector: GridVector2d) = GridPoint2d(x + vector.x, y + vector.y)

    fun neighbors4(): Set<GridPoint2d> = setOf(
            GridPoint2d(x + 1, y),
            GridPoint2d(x - 1, y),
            GridPoint2d(x, y + 1),
            GridPoint2d(x, y - 1)
    )

    fun neighbors8(): Set<GridPoint2d> = neighbors4().union(
            setOf(
                    GridPoint2d(x + 1, y + 1),
                    GridPoint2d(x + 1, y - 1),
                    GridPoint2d(x - 1, y - 1),
                    GridPoint2d(x - 1, y + 1)
            )
    )

    fun l1DistanceTo(other: GridPoint2d) = abs(x - other.x) + abs(y - other.y)

    fun l2DistanceTo(other: GridPoint2d) = hypot((x - other.x).toDouble(), (y - other.y).toDouble())

    fun lInfDistanceTo(other: GridPoint2d) = max(abs(x - other.x), abs(y - other.y))

    fun inBounds(
            xBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE,
            yBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE
    ): Boolean {

        return x in xBounds && y in yBounds
    }

}