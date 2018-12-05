import java.util.*
import kotlin.math.abs

class PolymerProcessor {

    fun fullyReact(polymer: String): String {
        val result = ArrayDeque<Char>()

        polymer.forEach { unit ->
            if (result.peekLast()?.reactsWith(unit) == true) {
                result.removeLast()
            } else {
                result.addLast(unit)
            }
        }

        return result.joinToString(separator = "")
    }

    fun optimizeAndFullyReact(polymer: String): String {
        return ('a'..'z')
            .map { unitTypeToRemove -> polymer.filterNot { unit -> unit.isSameUnitType(unitTypeToRemove) } }
            .map(::fullyReact)
            .minBy(String::length)!!
    }

}

fun Char.reactsWith(other: Char) = abs(this - other) == 32

fun Char.isSameUnitType(other: Char) = (this - other) % 32 == 0
