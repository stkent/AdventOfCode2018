enum class Attack {

    BLUDGEONING,
    COLD,
    FIRE,
    RADIATION,
    SLASHING;

    companion object {
        fun setFromString(s: String): Set<Attack> {
            return s
                .split(", ")
                .map { Attack.valueOf(it.toUpperCase()) }
                .toSet()
        }
    }

}
