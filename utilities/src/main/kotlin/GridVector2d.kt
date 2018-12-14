@file:Suppress("unused", "MemberVisibilityCanBePrivate")

import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max

enum class Direction {
    N, E, S, W;

    fun toVector(): GridVector2d {
        return when (this) {
            //@formatter:off
            N -> GridVector2d( 0,  1)
            E -> GridVector2d( 1,  0)
            S -> GridVector2d( 0, -1)
            W -> GridVector2d(-1,  0)
            //@formatter:on
        }
    }

    fun left90() = Direction.values()[(ordinal + 3) % 4]

    fun right90() = Direction.values()[(ordinal + 1) % 4]

    @Suppress("FunctionName")
    fun `180`() = Direction.values()[(ordinal + 2) % 4]
}

data class GridVector2d(val x: Int, val y: Int) {

    operator fun plus(other: GridVector2d): GridVector2d {
        return GridVector2d(x + other.x, y + other.y)
    }

    fun left90() = GridVector2d(-y, x)

    fun right90() = GridVector2d(y, -x)

    @Suppress("FunctionName")
    fun `180`() = GridVector2d(-x, -y)

    val l1Magnitude = abs(x) + abs(y)

    val l2Magnitude = hypot(x.toDouble(), y.toDouble())

    val lInfMagnitude = max(abs(x), abs(y))

}
