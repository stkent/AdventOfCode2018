@file:Suppress("unused", "MemberVisibilityCanBePrivate")

import extensions.pow
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

data class GridPoint3d(val x: Int, val y: Int, val z: Int) {

    companion object {
        val origin = GridPoint3d(0, 0, 0)
    }

    operator fun plus(vector: GridVector3d): GridPoint3d {
        return GridPoint3d(x + vector.x, y + vector.y, z + vector.z)
    }

    fun l1DistanceTo(other: GridPoint3d): Int = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

    fun l2DistanceTo(other: GridPoint3d): Double {
        return sqrt(
                (x - other.x).pow(2).toDouble() +
                        (y - other.y).pow(2).toDouble() +
                        (z - other.z).pow(2).toDouble()
        )
    }

    fun lInfDistanceTo(other: GridPoint3d) = max(max(abs(x - other.x), abs(y - other.y)), abs(z - other.z))

    fun inBounds(
            xBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE,
            yBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE,
            zBounds: IntRange = Int.MIN_VALUE..Int.MAX_VALUE
    ): Boolean {

        return x in xBounds && y in yBounds && z in zBounds
    }

}