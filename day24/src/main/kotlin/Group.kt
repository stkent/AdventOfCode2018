import extensions.tail
import java.util.*
import kotlin.math.max

data class Group(
    val id: UUID = UUID.randomUUID(),
    val good: Boolean,
    var size: Int,
    val unitHp: Int,
    val attackDamage: Int,
    val attack: Attack,
    val initiative: Int,
    val weaknesses: Set<Attack>,
    val immunities: Set<Attack>
) {

    companion object {
        private const val weaknessesPrefix = "weak to "
        private const val immunitiesPrefix = "immune to "
        private val regex =
            """(\d+) units each with (\d+) hit points(?: \((.*)\))? with an attack that does (\d+) (\w+) damage at initiative (\d+)""".toRegex()

        fun create(good: Boolean, rawGroup: String, boost: Int = 0): Group {
            val groups = regex.matchEntire(rawGroup)!!.groupValues.tail()
            var index = 0

            val size = groups[index++].toInt()
            val unitHp = groups[index++].toInt()

            val (weaknesses, immunities) = if (groups.size == 6) {
                parseOptionalAttrs(groups[index++])
            } else {
                Pair(emptySet(), emptySet())
            }

            val attackDamage = groups[index++].toInt()
            val attackType = Attack.valueOf(groups[index++].toUpperCase())
            val initiative = groups[index].toInt()

            return Group(
                good = good,
                size = size,
                unitHp = unitHp,
                attackDamage = attackDamage + boost,
                attack = attackType,
                initiative = initiative,
                weaknesses = weaknesses,
                immunities = immunities
            )
        }

        private fun parseOptionalAttrs(s: String): Pair<Set<Attack>, Set<Attack>> {
            val separated = s.split("; ")

            val weaknesses = separated
                .firstOrNull { it.startsWith(weaknessesPrefix) }
                ?.removePrefix(weaknessesPrefix)
                ?.let(Attack.Companion::setFromString) ?: emptySet()

            val immunities = separated
                .firstOrNull { it.startsWith(immunitiesPrefix) }
                ?.removePrefix(immunitiesPrefix)
                ?.let(Attack.Companion::setFromString) ?: emptySet()

            return Pair(weaknesses, immunities)
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other as? Group)?.let { it.id == this.id } ?: false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    // Returns true if any unit died as a result of this attack; false otherwise
    fun attack(other: Group): Boolean {
        return other.takeDamage(attackDamageTo(other))
    }

    fun bestTarget(candidates: List<Group>): Group? {
        val highestDamageTargets = candidates
            .filter { this.isEnemyOf(it) }
            .groupBy { this.attackDamageTo(it) }
            .filter { (damage, _) -> damage > 0 }
            .maxBy { (damage, _) -> damage }
            ?.value ?: return null

        return highestDamageTargets
            .sortedWith(compareByDescending(Group::effectivePower).thenByDescending(Group::initiative))
            .first()
    }

    fun effectivePower() = size * attackDamage

    private fun attackDamageTo(other: Group): Int {
        return this.effectivePower() * when {
            other.immunities.contains(this.attack) -> 0
            other.weaknesses.contains(this.attack) -> 2
            else -> 1
        }
    }

    private fun isEnemyOf(other: Group) = this.good != other.good

    // Returns true if any unit died as a result of damage received; false otherwise
    private fun takeDamage(damage: Int): Boolean {
        val oldSize = size
        size = max(0, size - damage / unitHp)
        return size != oldSize
    }

}
