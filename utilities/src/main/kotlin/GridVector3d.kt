@file:Suppress("unused", "MemberVisibilityCanBePrivate")

import extensions.pow
import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte1.other
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

data class GridVector3d(val x: Int, val y: Int, val z: Int) {

    operator fun plus(other: GridVector3d): GridVector3d {
        return GridVector3d(x + other.x, y + other.y, z + other.z)
    }

    val l1Magnitude = abs(x) + abs(y) + abs(z)

    val l2Magnitude = sqrt(x.pow(2).toDouble() + y.pow(2).toDouble() + z.pow(2).toDouble())

    val lInfMagnitude = maxOf(abs(x), abs(y), abs(z))

}
