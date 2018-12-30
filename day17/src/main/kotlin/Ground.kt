import SpreadEnd.NewSource
import SpreadEnd.Wall
import java.util.*

class Ground(private val clay: Set<GridPoint2d>) {

    data class WetAreas(val settled: Int, val running: Int)

    companion object {
        fun fromScan(scans: List<String>): Ground {
            val clay = scans.flatMap { s ->
                val regex = """(\w)=(\d+), \w=(\d+)\.\.(\d+)""".toRegex()
                val (coord, c1, c2, c3) = regex.matchEntire(s)!!.destructured
                val n1 = c1.toInt()
                val n2 = c2.toInt()
                val n3 = c3.toInt()

                return@flatMap (n2..n3).map {
                    return@map if (coord == "x") {
                        GridPoint2d(x = n1, y = it)
                    } else {
                        GridPoint2d(x = it, y = n1)
                    }
                }
            }.toSet()

            return Ground(clay)
        }

        fun fromSlice(rawGround: List<String>): Ground {
            val clay = mutableSetOf<GridPoint2d>()

            rawGround.withIndex().forEach { (y, row) ->
                row.withIndex().forEach { (x, char) ->
                    when (char) {
                        '█' -> clay += GridPoint2d(x, y)
                    }
                }
            }

            return Ground(clay)
        }
    }

    private val clayMinY = clay.map(GridPoint2d::y).min()!!
    private val clayMaxY = clay.map(GridPoint2d::y).max()!!

    fun computeWetAreas(spring: GridPoint2d): WetAreas {
        val settled = mutableSetOf<GridPoint2d>()
        val running = mutableSetOf<GridPoint2d>()

        fall(
            source = spring,
            settled = settled,
            running = running
        )

        val boundedSettled = settled.filter { location -> location.isInBounds(yBounds = clayMinY..clayMaxY) }
        val boundedRunning = running.filter { location -> location.isInBounds(yBounds = clayMinY..clayMaxY) }

        return WetAreas(boundedSettled.count(), boundedRunning.count())
    }

    private fun fall(
        source: GridPoint2d,
        settled: MutableSet<GridPoint2d>,
        running: MutableSet<GridPoint2d>
    ) {
        val cascade: Deque<GridPoint2d> = ArrayDeque<GridPoint2d>()

        // Fall until we hit supportive material.
        val splash = (source.y..clayMaxY)
            .asSequence()
            .map { fallDistance -> source.copy(y = fallDistance).also { cascade.push(it) } }
            .firstOrNull { it.shiftBy(dy = 1) in (clay + settled) }

        if (splash != null && splash !in running) {
            // We hit some supportive material for the first time; iterate up the cascade, flowing horizontally
            // and generating new sources as appropriate.
            while (cascade.isNotEmpty()) {
                val spreadSource = cascade.pop()

                // The layer now below us is not formed of supportive material; no horizontal flow is possible.
                if (spreadSource.shiftBy(dy = 1) in running) {
                    running += spreadSource
                    continue
                }

                // Spread left and right.
                val (leftSpread, leftEnd) = computeSpread(source = spreadSource, dx = -1, settled = settled)
                val (rightSpread, rightEnd) = computeSpread(source = spreadSource, dx = +1, settled = settled)
                val fullSpread = leftSpread + rightSpread

                if (leftEnd is Wall && rightEnd is Wall) {
                    // If we're bounded by walls on both sides, this layer of water is now settled.
                    running.removeAll(fullSpread)
                    settled.addAll(fullSpread)
                } else {
                    // If we're not bounded by a wall on at least one side, this layer of water is running.
                    running.addAll(fullSpread)

                    // Fall off one (or both) sides.
                    if (leftEnd is NewSource) fall(leftEnd.location, settled, running)
                    if (rightEnd is NewSource) fall(rightEnd.location, settled, running)
                }
            }
        } else {
            // We reached the bottom boundary of the region of interest; no more water can settle.
            running.addAll(cascade)
        }
    }

    private fun computeSpread(source: GridPoint2d, dx: Int, settled: Set<GridPoint2d>): Spread {
        val extent = mutableSetOf(source)

        while (true) {
            val current = extent.last().shiftBy(dx = dx)

            if (current.shiftBy(dy = 1) !in (clay + settled)) {
                return Spread(extent, end = NewSource(location = current))
            }

            if (current in clay) {
                return Spread(extent, end = Wall)
            }

            extent += current
        }
    }

    private fun prettyPrintWater(settled: Set<GridPoint2d>, running: Set<GridPoint2d>) {
        val all = (clay + settled + running)
        val xMin = all.map(GridPoint2d::x).min()!!
        val xMax = all.map(GridPoint2d::x).max()!!

        for (y in all.map(GridPoint2d::y).min()!!..all.map(GridPoint2d::y).max()!!) {
            for (x in xMin..xMax) {
                val pt = GridPoint2d(x, y)
                when (pt) {
                    in clay -> print('█')
                    in settled -> print('~')
                    in running -> print('|')
                    else -> print('.')
                }
            }
            print("\n")
        }
    }

}

private data class Spread(val extent: Set<GridPoint2d>, val end: SpreadEnd)

private sealed class SpreadEnd {
    object Wall : SpreadEnd()
    data class NewSource(val location: GridPoint2d) : SpreadEnd()
}
