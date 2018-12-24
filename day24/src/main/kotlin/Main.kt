import extensions.tail

fun main() {
    val immuneSystemInput = resourceFile("immune_system_input.txt").readLines()
    val infectionInput = resourceFile("infection_input.txt").readLines()

    val immuneSystem = immuneSystemInput.map(Group.Companion::fromString)
    println(immuneSystem.joinToString("\n"))

    val infection = infectionInput.map(Group.Companion::fromString)
    println(infection.joinToString("\n"))
//    949 units each with 3117 hit points with an attack that does 29 fire damage at initiative 10
//    5776 units each with 5102 hit points (weak to cold; immune to slashing) with an attack that does 8 radiation damage at initiative 15

}

data class Group(
    var size: Int,
    val unitHp: Int,
    val attackDamage: Int,
    val attackType: AttackType,
    val initiative: Int,
    val weaknesses: Set<AttackType>,
    val immunities: Set<AttackType>
) {

    companion object {
        private const val weaknessesPrefix = "weak to "
        private const val immunitiesPrefix = "immune to "
        private val regex =
            """(\d+) units each with (\d+) hit points(?: \((.*)\))? with an attack that does (\d+) (\w+) damage at initiative (\d+)""".toRegex()

        fun fromString(s: String): Group {
            val groups = regex.matchEntire(s)!!.groupValues.tail()
            var groupIndex = 0

            val size = groups[groupIndex++].toInt()
            val unitHp = groups[groupIndex++].toInt()

            val (weaknesses, immunities) = if (groups.size == 6) {
                parseOptionalAttributes(groups[groupIndex++])
            } else {
                Pair(emptySet(), emptySet())
            }

            val attackDamage = groups[groupIndex++].toInt()
            val attackType = AttackType.valueOf(groups[groupIndex++].toUpperCase())
            val initiative = groups[groupIndex].toInt()

            return Group(
                size = size,
                unitHp = unitHp,
                attackDamage = attackDamage,
                attackType = attackType,
                initiative = initiative,
                weaknesses = weaknesses,
                immunities = immunities
            )
        }

        private fun parseOptionalAttributes(s: String): Pair<Set<AttackType>, Set<AttackType>> {
            val separated = s.split("; ")

            val weaknesses = separated
                .firstOrNull { it.startsWith(weaknessesPrefix) }
                ?.removePrefix(weaknessesPrefix)
                ?.let(AttackType.Companion::setFromString) ?: emptySet()

            val immunities = separated
                .firstOrNull { it.startsWith(immunitiesPrefix) }
                ?.removePrefix(immunitiesPrefix)
                ?.let(AttackType.Companion::setFromString) ?: emptySet()

            return Pair(weaknesses, immunities)
        }
    }
}

enum class AttackType {
    BLUDGEONING,
    COLD,
    FIRE,
    RADIATION,
    SLASHING;

    companion object {
        fun setFromString(s: String): Set<AttackType> {
            return s
                .split(", ")
                .map { AttackType.valueOf(it.toUpperCase()) }
                .toSet()
        }
    }

}
