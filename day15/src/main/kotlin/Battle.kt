class Battle(private val rawMap: List<String>) {

    data class Result(val rounds: Int, val remainingHp: Int)

    data class ShortestPathResult(val start: GridPoint2d, val distance: Int)

    data class ClosestLocationResult(val closestLocation: GridPoint2d, val firstStep: GridPoint2d)

    companion object {
        fun findFoes(warrior: Warrior, candidates: List<Warrior>): List<Warrior> {
            return candidates.filter { other -> other.isFoeOf(warrior) }
        }

        fun findAttackLocations(
            foeLocations: List<GridPoint2d>,
            liveWarriorLocations: List<GridPoint2d>,
            cavern: List<String>
        ): List<GridPoint2d> {

            return foeLocations
                .flatMap { it.adjacentPoints() }
                .toSet()
                .filterNot { location -> cavern[location.y][location.x] == '█' }
                .filterNot { location -> location in liveWarriorLocations }
                .sortedWith(compareBy(GridPoint2d::y).thenBy(GridPoint2d::x))
        }

        fun findFoeToAttack(warriorLocation: GridPoint2d, foes: List<Warrior>): Warrior? {
            val adjacentLocations = warriorLocation.adjacentPoints()

            return foes
                .filter { foe -> foe.location in adjacentLocations }
                .sortedWith(compareBy(Warrior::hp).thenBy { it.location.y }.thenBy { it.location.x })
                .firstOrNull()
        }

        fun shortestPathBetween(
            start: GridPoint2d,
            end: GridPoint2d,
            liveWarriorLocations: List<GridPoint2d>,
            cavern: List<String>
        ): ShortestPathResult? {
            val firstNode = Node(start)
            val targetNode = Node(end)

            val nodesToEvaluate = mutableSetOf<Node>().apply {
                this += firstNode
            }

            val evaluatedNodes = mutableSetOf<Node>()
            val cameFrom = mutableMapOf<Node, Node>()
            val gScores = mutableMapOf(firstNode to 0)

            fun heuristic(current: Node): Int {
                return current.inputPoint.l1DistanceTo(targetNode.inputPoint)
            }

            val fScores = mutableMapOf(firstNode to heuristic(firstNode))

            while (nodesToEvaluate.isNotEmpty()) {
                val currentNode = nodesToEvaluate.minBy { fScores[it]!! }!!
                if (currentNode == targetNode) {
                    val distance = fScores[currentNode]!!
                    return ShortestPathResult(start, distance)
                }

                nodesToEvaluate.remove(currentNode)
                evaluatedNodes.add(currentNode)

                for ((node, distance) in currentNode.validNeighborCosts(liveWarriorLocations, cavern)) {
                    if (node in evaluatedNodes) continue

                    val tentativeGScore = gScores[currentNode]!! + distance

                    if (node !in nodesToEvaluate) {
                        nodesToEvaluate += node
                    } else if (tentativeGScore >= gScores[node]!!) {
                        continue
                    }

                    cameFrom[node] = currentNode
                    gScores[node] = tentativeGScore
                    fScores[node] = gScores[node]!! + heuristic(node)
                }
            }

            return null
        }

        fun shortestPathBetweenWarriorAndTarget(
            warriorLocation: GridPoint2d,
            targetLocation: GridPoint2d,
            liveWarriorLocations: List<GridPoint2d>,
            cavern: List<String>
        ): ShortestPathResult? {

            var shortestPathResult: ShortestPathResult? = null

            val validNeighbors = warriorLocation
                .adjacentPoints()
                .filterNot { neighbor -> cavern[neighbor.y][neighbor.x] == '█' }
                .filterNot { neighbor -> neighbor in liveWarriorLocations }

            if (validNeighbors.isNotEmpty()) {
                val neighborShortestPathResults: List<ShortestPathResult?> = validNeighbors
                    .mapNotNull { adjacentPoint ->
                        shortestPathBetween(adjacentPoint, targetLocation, liveWarriorLocations, cavern)
                    }

                if (neighborShortestPathResults.isNotEmpty()) {
                    shortestPathResult =
                        neighborShortestPathResults
                            .sortedWith(compareBy({ it!!.distance }, { it!!.start.y }, { it!!.start.x }))
                            .firstOrNull()
                }
            }

            return shortestPathResult
        }

        fun closestLocationResult(
            warrior: Warrior,
            locations: Collection<GridPoint2d>,
            liveWarriorLocations: List<GridPoint2d>,
            cavern: List<String>
        ): ClosestLocationResult? {

            val storedShortestPathResults = mutableMapOf<GridPoint2d, ShortestPathResult>()

            locations.forEach { location ->
                val shortestPathResult = shortestPathBetweenWarriorAndTarget(
                    warrior.location,
                    location,
                    liveWarriorLocations,
                    cavern
                )

                if (shortestPathResult != null) {
                    storedShortestPathResults[location] = shortestPathResult
                }
            }

            if (storedShortestPathResults.isEmpty()) {
                return null
            }

            val sortedLocations =
                storedShortestPathResults
                    .keys
                    .sortedWith(compareBy({ storedShortestPathResults[it]!!.distance }, { it.y }, { it.x }))

            return ClosestLocationResult(
                sortedLocations.first(),
                storedShortestPathResults[sortedLocations.first()]!!.start
            )
        }
    }

    private val cavern: List<String> by lazy {
        return@lazy rawMap.map { row -> row.replace("[GE]".toRegex(), ".") }
    }

    private fun getInitialWarriors(): List<Warrior> {
        var id = 0

        return rawMap
            .withIndex()
            .flatMap { (y, row) ->
                //@formatter:off
                    row.withIndex()
                            .mapNotNull { (x, char) ->
                                Warrior.from(char, id = id++, location = GridPoint2d(x, y))
                            }
                }
    }

    fun executePartI(debug: Boolean = false): Result {
        var round = 0
        var roundWarriors = getInitialWarriors()

        if (debug) {
            println("Initially:")
            print(roundWarriors, cavern)
        }

        while (true) {
            if (debug) println("After ${round + 1} rounds:")

            roundWarriors = roundWarriors
                    .filter(Warrior::isAlive)
                    .sortedWith(compareBy<Warrior> { it.location.y }.thenBy { it.location.x })

            for (warrior in roundWarriors) {
                val turnWarriors = roundWarriors.filter(Warrior::isAlive)
                if (warrior !in turnWarriors) continue

                val foes = findFoes(warrior, candidates = turnWarriors - warrior)
                if (foes.isEmpty()) {
                    return Result(round, remainingHp = turnWarriors.sumBy(Warrior::hp))
                }

                val foeToAttack = findFoeToAttack(warrior.location, foes)
                if (foeToAttack != null) {
                    warrior.attack(foeToAttack)
                    continue
                }

                val attackLocations = findAttackLocations(
                        foeLocations = foes.map(Warrior::location),
                        liveWarriorLocations = turnWarriors.map(Warrior::location),
                        cavern = cavern
                )

                val closestLocation = closestLocationResult(
                        warrior,
                        locations = attackLocations,
                        liveWarriorLocations = turnWarriors.map(Warrior::location),
                        cavern = cavern)
                        ?: continue

                warrior.location = closestLocation.firstStep

                findFoeToAttack(warrior.location, foes)?.let { newFoeToAttack ->
                    warrior.attack(newFoeToAttack)
                }
            }

            if (debug) print(roundWarriors, cavern)

            round++
        }
    }

    fun executePartII(debug: Boolean = false): Result {
        var elfAttackPower = 4

        attackPowerLoop@ while (true) {
            var round = 0
            var roundWarriors = getInitialWarriors()

            roundWarriors.forEach { warrior ->
                if (warrior is Warrior.Elf) {
                    warrior.attackPower = elfAttackPower
                }
            }

            if (debug) {
                println("attack power: $elfAttackPower")
                println("Initially:")
                print(roundWarriors, cavern)
            }

            while (true) {
                if (debug) println("After ${round + 1} rounds:")

                roundWarriors = roundWarriors
                        .filter(Warrior::isAlive)
                        .sortedWith(compareBy<Warrior> { it.location.y }.thenBy { it.location.x })

                for (warrior in roundWarriors) {
                    val turnWarriors = roundWarriors.filter(Warrior::isAlive)
                    if (warrior !in turnWarriors) continue

                    val foes = findFoes(warrior, candidates = turnWarriors - warrior)
                    if (foes.isEmpty()) {
                        return Result(round, remainingHp = turnWarriors.sumBy(Warrior::hp))
                    }

                    val foeToAttack = findFoeToAttack(warrior.location, foes)
                    if (foeToAttack != null) {
                        warrior.attack(foeToAttack)

                        if (foeToAttack is Warrior.Elf && !foeToAttack.isAlive) {
                            elfAttackPower++
                            continue@attackPowerLoop
                        }
                        continue
                    }

                    val attackLocations = findAttackLocations(
                            foeLocations = foes.map(Warrior::location),
                            liveWarriorLocations = turnWarriors.map(Warrior::location),
                            cavern = cavern
                    )

                    val closestLocation = closestLocationResult(
                            warrior,
                            locations = attackLocations,
                            liveWarriorLocations = turnWarriors.map(Warrior::location),
                            cavern = cavern)
                            ?: continue

                    warrior.location = closestLocation.firstStep

                    findFoeToAttack(warrior.location, foes)?.let { newFoeToAttack ->
                        warrior.attack(newFoeToAttack)
                    }
                }

                if (debug) print(roundWarriors, cavern)

                round++
            }
        }
    }

    private fun print(liveWarriors: List<Warrior>, cavern: List<String>) {
        for (y in 0 until cavern.size) {
            var hitPointsString = "   "

            for (x in 0 until cavern[0].length) {
                var printedWarrior = false

                liveWarriors.forEach { warrior ->
                    if (warrior.location == GridPoint2d(x, y)) {
                        print(warrior)
                        printedWarrior = true
                        hitPointsString += "$warrior(${warrior.hp}), "
                    }
                }

                if (!printedWarrior) {
                    print(cavern[y][x])
                }
            }
            print(hitPointsString)
            println()
        }
        println()
    }

    data class Node(val inputPoint: GridPoint2d) {

        override fun equals(other: Any?): Boolean {
            if (other !is Node) return false
            if (other.inputPoint != inputPoint) return false
            return true
        }

        override fun hashCode(): Int {
            return 0
        }

        fun validNeighborCosts(liveWarriorLocations: List<GridPoint2d>, cavern: List<String>): Map<Node, Int> {
            val result = mutableMapOf<Node, Int>()

            val validNeighbors = inputPoint
                    .adjacentPoints()
                    .filterNot { neighbor -> cavern[neighbor.y][neighbor.x] == '█' }
                    .filterNot { neighbor -> neighbor in liveWarriorLocations }

            validNeighbors.forEach { neighbor ->
                result[Node(neighbor)] = 1
            }

            return result
        }

        override fun toString(): String {
            return "(${inputPoint.x}, ${inputPoint.y})"
        }
    }
}
