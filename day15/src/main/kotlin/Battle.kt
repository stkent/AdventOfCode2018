class Battle(private val rawMap: List<String>) {

    data class Result(val rounds: Int, val remainingHp: Int)

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
                .flatMap(GridPoint2d::adjacentPoints)
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

        fun findNextLocation(
            warriorLocation: GridPoint2d,
            attackLocations: List<GridPoint2d>,
            cavern: List<String>
        ): GridPoint2d? {

            // todo: this
            return null
        }

        // components of find next location
    }

    private val cavern: List<String> by lazy {
        return@lazy rawMap.map { row -> row.replace("[GE]".toRegex(), ".") }
    }

    private val initialWarriors: List<Warrior> by lazy {
        var id = 0

        return@lazy rawMap
            .withIndex()
            .flatMap { (y, row) ->
                //@formatter:off
                row.withIndex()
                   .mapNotNull { (x, char) ->
                       Warrior.from(char, id = id++, location = GridPoint2d(x, y))
                   }
                //@formatter:on
            }
    }

    fun execute(): Result {
        var round = 0
        var roundWarriors = initialWarriors

        while (true) {
            roundWarriors = roundWarriors
                .filter(Warrior::isAlive)
                .sortedWith(compareBy<Warrior> { it.location.y }.thenBy { it.location.x })

            for (warrior in roundWarriors) {
                val turnWarriors = roundWarriors.filter(Warrior::isAlive)

                // If warrior has already died during this round, there's nothing to do; advance to next warrior.
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

                if (attackLocations.isEmpty()) continue

                findNextLocation(warrior.location, attackLocations, cavern)
                    ?.let { warrior.location = it }
                    ?: continue

                findFoeToAttack(warrior.location, foes)
                    ?.let { warrior.attack(it) }
            }

            round++
            break
        }

        return Result(rounds = round, remainingHp = 1)
    }

//    private fun closestLocation(warrior: Warrior, locations: Collection<GridPoint2d>): GridPoint2d? {
//        return locations
//            .groupBy { location -> distanceBetween(warrior.location, end = location) }
//            .filterKeys { it != null }
//            .minBy { (distance, _) -> distance!! }!!
//            .value
//            .firstOrNull()
//    }
//
//    private fun distanceBetween(start: GridPoint2d, end: GridPoint2d): Int? {
//        return 0
//    }

}
