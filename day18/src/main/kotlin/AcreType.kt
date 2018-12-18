enum class AcreType {

    OPEN, TREES, YARD;

    companion object {
        fun fromChar(char: Char): AcreType {
            return when (char) {
                '.' -> OPEN
                '|' -> TREES
                '#' -> YARD
                else -> throw IllegalStateException("Unknown acre type")
            }
        }
    }

    fun evolve(neighborTypes: List<AcreType>): AcreType {
        return when (this) {
            OPEN -> {
                if (neighborTypes.count { it == TREES } >= 3) TREES else OPEN
            }

            TREES -> {
                if (neighborTypes.count { it == YARD } >= 3) YARD else TREES
            }

            YARD -> {
                if (neighborTypes.count { it == YARD } >= 1 &&
                    neighborTypes.count { it == TREES } >= 1) YARD else OPEN
            }
        }
    }

}
