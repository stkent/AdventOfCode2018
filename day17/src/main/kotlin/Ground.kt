

// observation: no adjacent traps (string search for parsed slice returned no results for .██.)
class Ground(private val clay: Set<GridPoint2d>) {

    companion object {
        fun fromScan(spring: GridPoint2d, scans: List<String>): Ground {
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

    fun wettableArea(spring: GridPoint2d): Set<GridPoint2d> {
        val wetArea = mutableSetOf<GridPoint2d>()

        val splash = (clayMinY..clayMaxY)
            .asSequence()
            .map { fall -> spring.shiftBy(dy = fall).also { wetArea.add(it) } }
            .firstOrNull { it.shiftBy(dy = 1) in clay
            } ?: return wetArea + setOf(spring.copy(y = clayMaxY))

        var rightSteps = 1
        while (true) {
            if (splash.shiftBy(dx = rightSteps) !in clay &&
                splash.shiftBy(dx = rightSteps, dy = 1) in clay &&
                splash.shiftBy(dx = rightSteps + 1, dy = 1) !in clay) {

                return wetArea + wettableArea(splash.shiftBy(dx = 2))
            } else if (splash.shiftBy(dx = rightSteps) in clay && splash.shiftBy(dx = rightSteps, dy = 1) in clay) {
                break
            }

            wetArea.add(splash.shiftBy(dx = rightSteps))

            rightSteps += 1
        }

        println("rightSteps = $rightSteps")
        println("wetArea = $wetArea")

        return wetArea
    }

    private val clayMinX: Int by lazy {
        return@lazy clay.map(GridPoint2d::x).min()!!
    }

    private val clayMaxX: Int by lazy {
        return@lazy clay.map(GridPoint2d::x).max()!!
    }

    private val clayMinY: Int by lazy {
        return@lazy clay.map(GridPoint2d::y).min()!!
    }

    private val clayMaxY: Int by lazy {
        return@lazy clay.map(GridPoint2d::y).max()!!
    }

    fun prettyPrint() {
        for (y in clayMinY..clayMaxY) {
            for (x in clayMinX..clayMaxX) {
                print(if (GridPoint2d(x, y) in clay) '█' else '.')
            }

            print('\n')
        }
    }

}
