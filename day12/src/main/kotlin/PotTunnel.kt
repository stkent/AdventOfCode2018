import extensions.repeated

class PotTunnel(initialPotState: String, private val spreadPatterns: List<String>) {

    private val spreadPatternWidth = spreadPatterns.first().length

    var state = TunnelState(pots = initialPotState, firstPotIndex = 0)

    fun advanceByGenerations(generations: Int) {
        repeat(generations) { spread(); }
    }

    private fun spread() {
        val paddedState = state.padded(spreadPatternWidth)

        val newPots = paddedState.pots
            .windowed(size = spreadPatternWidth, step = 1)
            .map { pattern -> if (spreadPatterns.contains(pattern)) '#' else '.' }
            .joinToString("")

        val newState = TunnelState(
            pots = newPots,
            firstPotIndex = paddedState.firstPotIndex + ((spreadPatternWidth - 1) / 2)
        )

        state = newState.trimmed()
    }

}

data class TunnelState(val pots: String, val firstPotIndex: Int) {

    val plantIndexSum: Int by lazy {
        return@lazy pots
            .withIndex()
            .filter { (_, pot) -> pot == '#' }
            .sumBy { (index, _) -> firstPotIndex + index }
    }

    fun padded(spreadPatternWidth: Int): TunnelState {
        val padLength = (spreadPatternWidth - 1)
        val padding = '.'.repeated(times = spreadPatternWidth - 1)

        return TunnelState(
            pots = "$padding$pots$padding",
            firstPotIndex = firstPotIndex - padLength
        )
    }

    fun trimmed(): TunnelState {
        val firstPlantIndex = pots.indexOf('#')

        return TunnelState(
            pots = pots.drop(firstPlantIndex).dropLastWhile { it == '.' },
            firstPotIndex = firstPotIndex + firstPlantIndex
        )
    }

}
