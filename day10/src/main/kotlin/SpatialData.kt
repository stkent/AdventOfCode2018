data class SpatialData(val position: GridPoint2d, val velocity: GridVector2d) {

    companion object {
        private val regex =
            "position=<\\s*(-?\\d+),\\s*(-?\\d+)> velocity=<\\s*(-?\\d+),\\s*(-?\\d+)>".toRegex()

        fun fromString(s: String): SpatialData {
            val (x, y, vx, vy) = regex.matchEntire(s)!!.destructured
            return SpatialData(
                position = GridPoint2d(x.toInt(), y.toInt()),
                velocity = GridVector2d(vx.toInt(), vy.toInt())
            )
        }
    }

}
