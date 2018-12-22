class Cave(private val scanDepth: Int, private val scanTarget: GridPoint2d) {

    private val regionMap = mutableMapOf<GridPoint2d, RegionType>()
    private val erosionMap = mutableMapOf<GridPoint2d, Int>()
    private val geologicIndexMap = mutableMapOf<GridPoint2d, Int>()

    fun risk(): Int {
        var result = 0

        for (y in 0..scanTarget.y) {
            for (x in 0..scanTarget.x) {
                result += regionAt(GridPoint2d(x, y)).ordinal
            }
        }

        return result
    }

    fun rescueTime(): Int {
        return 0
    }

    private fun regionAt(point: GridPoint2d): RegionType {
        check(point.x >= 0 && point.y >= 0) { "Solid rock; cannot compute region type." }

        return regionMap[point]
            ?: RegionType.values()[(erosionAt(point) % 3)]
                .also { regionMap += point to it }
    }

    private fun erosionAt(point: GridPoint2d): Int {
        check(point.x >= 0 && point.y >= 0) { "Solid rock; cannot compute erosion." }

        return erosionMap[point]
            ?: ((geologicIndexAt(point) + scanDepth) % 20183)
                .also { erosionMap += point to it }
    }

    private fun geologicIndexAt(point: GridPoint2d): Int {
        check(point.x >= 0 && point.y >= 0) { "Solid rock; cannot compute geologic index." }

        return geologicIndexMap[point]
            ?: when {
                point.x == 0 && point.y == 0 -> 0
                point == scanTarget -> 0
                point.x == 0 -> (point.y * 48271)
                point.y == 0 -> (point.x * 16807)
                else -> erosionAt(point.shiftBy(dx = -1)) * erosionAt(point.shiftBy(dy = -1))
            }.also { geologicIndexMap += point to it }
    }

}
