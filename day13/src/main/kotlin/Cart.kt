import GridDirection.*
import Turn.LEFT
import Turn.RIGHT

data class Cart(
    var position: GridPoint2d,
    var direction: GridDirection,
    var crashed: Boolean = false,
    var intersectionCount: Int = 0) {

    companion object {
        fun direction(char: Char): GridDirection? {
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

    fun advance(): Cart {
        position += direction.toVector()
        return this
    }

}
