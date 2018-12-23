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
        val firstNode = Node(point = GridPoint2d.origin, tool = Tool.TORCH)
        val targetNode = Node(point = scanTarget, tool = Tool.TORCH)

        fun heuristic(current: Node): Int {
            return current.point.l1DistanceTo(targetNode.point)
        }

        val nodesToEvaluate = mutableSetOf(firstNode)
        val evaluatedNodes = mutableSetOf<Node>()

        val gScores = mutableMapOf(firstNode to 0)
        val fScores = mutableMapOf(firstNode to heuristic(firstNode))

        while (nodesToEvaluate.isNotEmpty()) {
            val currentNode = nodesToEvaluate.minBy { fScores[it]!! }!!
            if (currentNode == targetNode) return fScores[currentNode]!!

            nodesToEvaluate.remove(currentNode)
            evaluatedNodes.add(currentNode)

            for ((node, distance) in currentNode.validNeighborCosts(this)) {
                if (node in evaluatedNodes) continue

                val tentativeGScore = gScores[currentNode]!! + distance

                if (node !in nodesToEvaluate) {
                    nodesToEvaluate += node
                } else if (tentativeGScore >= gScores[node]!!) {
                    continue
                }

                gScores[node] = tentativeGScore
                fScores[node] = gScores[node]!! + heuristic(node)
            }
        }

        throw IllegalStateException("Algorithm should terminate in while loop")
    }
























    fun regionAt(point: GridPoint2d): RegionType {
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

data class Node(val point: GridPoint2d, val tool: Tool) {

    fun validNeighborCosts(cave: Cave): Map<Node, Int> {
        return validNeighborCosts { point -> cave.regionAt(point).allowedTools }
    }

    private fun validNeighborCosts(allowedTools: (GridPoint2d) -> Collection<Tool>): Map<Node, Int> {
        val newPointNeighbors = point
            .adjacentPoints()
            .filter { point -> point.x >= 0 && point.y >= 0 }
            .map { point -> Node(point, tool) }
            .filter { (point, tool) -> tool in allowedTools(point) }

        val newToolNeighbors = allowedTools(point)
            .minus(tool)
            .map { newTool -> Node(point, newTool) }

        return (newPointNeighbors.map { it to 1 } + newToolNeighbors.map { it to 7 }).toMap()
    }

}
