import Direction.*
import Turn.LEFT
import Turn.RIGHT

class Cart(var position: GridPoint2d, var direction: Direction) {

    companion object {
        fun direction(char: Char): Direction? {
            return when (char) {
                //@formatter:off
                 '^' -> N
                 '>' -> E
                 'v' -> S
                 '<' -> W
                else -> null
                //@formatter:on
            }
        }
    }

    var hasCrashed = false
        private set

    val nextIntersectionTurn: Turn?
        get() {
            return when (intersectionCount % 3) {
                //@formatter:off
                   0 -> LEFT
                   2 -> RIGHT
                else -> null
                //@formatter:on
            }
        }

    private var intersectionCount = 0

    fun advance(): Cart {
        position += direction.toVector()
        return this
    }

    fun didCrash() {
        hasCrashed = true
    }

    fun didPassThroughIntersection() {
        intersectionCount++
    }

}
