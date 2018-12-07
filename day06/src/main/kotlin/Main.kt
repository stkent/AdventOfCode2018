import extensions.extractInts

fun main() {
    val input = resourceFile("input.txt").readLines()

    val targetPoints = input.map { string ->
        string.extractInts().run { GridPoint2d(x = first(), y = last()) }
    }

    val targetXs = targetPoints.map(GridPoint2d::x)
    val targetYs = targetPoints.map(GridPoint2d::y)
    val minTargetX = targetXs.min()!!
    val maxTargetX = targetXs.max()!!
    val minTargetY = targetYs.min()!!
    val maxTargetY = targetYs.max()!!

    val nearbyPoints = GridPoint2d.region(
        minX = minTargetX,
        maxX = maxTargetX,
        minY = minTargetY,
        maxY = maxTargetY)

    val largestFiniteTargetRegion = nearbyPoints
        .groupBy { nearbyPoint -> targetPoints.uniquePointClosestTo(nearbyPoint) }
        .filterValues { targetRegion ->
            targetRegion.isWithinBounds(minTargetX + 1 until maxTargetX, minTargetY + 1 until maxTargetY)
        }
        .map { (_, targetRegionPoints) -> targetRegionPoints.count() }
        .max()!!

    // part 1

    println(largestFiniteTargetRegion)

    // part 2

    val regionSize = nearbyPoints.count { nearbyPoint ->
        targetPoints.sumBy { it.l1DistanceTo(nearbyPoint) } < 10000
    }

    println(regionSize)
}

fun Collection<GridPoint2d>.uniquePointClosestTo(other: GridPoint2d): GridPoint2d? {
    val pointDistances = this
        .map { otherPoint -> otherPoint to otherPoint.l1DistanceTo(other) }
        .sortedBy { (_, distance) -> distance }

    val (closestPoint, shortestDistance) = pointDistances[0]
    val (_, secondShortestDistance) = pointDistances[1]

    return if (shortestDistance < secondShortestDistance) closestPoint else null
}

fun Collection<GridPoint2d>.isWithinBounds(xBounds: IntRange, yBounds: IntRange): Boolean {
    return this.all { point -> point.inBounds(xBounds, yBounds) }
}
