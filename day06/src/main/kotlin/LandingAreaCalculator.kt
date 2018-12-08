import extensions.extractInts

class LandingAreaCalculator(rawTargetPoints: List<String>) {

    private val targetPoints = rawTargetPoints.map { string ->
        string.extractInts().run { GridPoint2d(x = first(), y = last()) }
    }

    private val targetXs = targetPoints.map(GridPoint2d::x)
    private val targetYs = targetPoints.map(GridPoint2d::y)
    private val minTargetX = targetXs.min()!!
    private val maxTargetX = targetXs.max()!!
    private val minTargetY = targetYs.min()!!
    private val maxTargetY = targetYs.max()!!

    private val nearbyPoints = run {
        val result = mutableListOf<GridPoint2d>()

        for (x in minTargetX..maxTargetX) {
            for (y in minTargetY..maxTargetY) {
                result.add(GridPoint2d(x, y))
            }
        }

        return@run result
    }

    val safeArea1: Int by lazy {
        nearbyPoints
            .groupBy { nearbyPoint -> targetPoints.uniquePointClosestTo(nearbyPoint) }
            .filterValues { targetRegion ->
                targetRegion.isWithinBounds(minTargetX + 1 until maxTargetX, minTargetY + 1 until maxTargetY)
            }
            .map { (_, targetRegionPoints) -> targetRegionPoints.count() }
            .max()!!
    }

    fun safeArea2(distanceThreshold: Int): Int {
        return nearbyPoints.count { nearbyPoint ->
            targetPoints.sumBy { it.l1DistanceTo(nearbyPoint) } < distanceThreshold
        }
    }

}

private fun Collection<GridPoint2d>.uniquePointClosestTo(other: GridPoint2d): GridPoint2d? {
    val pointDistances = this
        .map { otherPoint -> otherPoint to otherPoint.l1DistanceTo(other) }
        .sortedBy { (_, distance) -> distance }

    val (closestPoint, shortestDistance) = pointDistances[0]
    val (_, secondShortestDistance) = pointDistances[1]

    return if (shortestDistance < secondShortestDistance) closestPoint else null
}

private fun Collection<GridPoint2d>.isWithinBounds(xBounds: IntRange, yBounds: IntRange): Boolean {
    return this.all { point -> point.inBounds(xBounds, yBounds) }
}
