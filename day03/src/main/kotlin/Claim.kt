import extensions.extractInts

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int) {

    companion object {
        fun fromString(s: String): Claim {
            val ints = s.extractInts()
            return Claim(ints[0], ints[1], ints[2], ints[3], ints[4])
        }
    }

    val squares: Set<GridPoint2d> by lazy {
        val result = mutableSetOf<GridPoint2d>()

        for (x in left until (left + width)) {
            for (y in top until (top + height)) {
                result.add(GridPoint2d(x, y))
            }
        }

        return@lazy result
    }

}
