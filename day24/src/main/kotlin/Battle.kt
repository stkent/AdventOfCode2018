class Battle(rawGoodGroups: List<String>, rawEvilGroups: List<String>, goodBoost: Int = 0) {

    private val goodGroups = rawGoodGroups.map { Group.create(good = true, rawGroup = it, boost = goodBoost) }
    private val evilGroups = rawEvilGroups.map { Group.create(good = false, rawGroup = it) }

    sealed class Result {
        data class Win(val good: Boolean, val liveUnits: Int) : Result()
        object Stalemate : Result()
    }

    private val battleOver: Boolean
        get() = liveGroups.groupBy(Group::good).count() < 2

    private val liveGroups: List<Group>
        get() = (goodGroups + evilGroups).filterNot { it.size == 0 }

    fun execute(): Result {
        while (!battleOver) {
            val pendingAttacks = computePendingAttacks(liveGroups)
            val unitsDied = performAttacks(pendingAttacks)
            if (!unitsDied) return Result.Stalemate
        }

        return Result.Win(
            good = liveGroups.first().good,
            liveUnits = liveGroups.sumBy { it.size }
        )
    }

    private fun computePendingAttacks(groups: List<Group>): Map<Group, Group?> {
        val result = mutableMapOf<Group, Group?>()
        val attacked = mutableSetOf<Group>()
        val targetingOrder = groups
            .sortedWith(compareByDescending(Group::effectivePower).thenByDescending(Group::initiative))

        targetingOrder.forEach { attacker ->
            val bestTarget = attacker.bestTarget(candidates = targetingOrder - attacker - attacked)
            result += attacker to bestTarget
            bestTarget?.let { attacked += it }
        }

        return result
    }

    // Returns true if any unit died as a result of any attack; false otherwise
    private fun performAttacks(pendingAttacks: Map<Group, Group?>): Boolean {
        var unitsDied = false

        val attackOrder = pendingAttacks
            .keys
            .sortedWith(compareByDescending(Group::initiative))

        attackOrder
            .asSequence()
            .filterNot { it.size == 0 }
            .forEach { attacker ->
                pendingAttacks[attacker]?.let { target ->
                    attacker.attack(target).also { unitsDied = unitsDied || it }
                }
            }

        return unitsDied
    }

}
