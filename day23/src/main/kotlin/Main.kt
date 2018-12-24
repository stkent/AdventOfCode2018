import extensions.extractInts

fun main() {
    val input = resourceFile("input.txt").readLines()

    val bots = input.map(Bot.Companion::fromString)
    val strongestBot = bots.maxBy(Bot::signalRadius)!!

    println("Part 1 solution: ${bots.count { bot -> strongestBot.signalReaches(bot.location) }}")
    println("Part 2 not solved; no general algorithm apparently available.")
}

data class Bot(val location: GridPoint3d, val signalRadius: Int) {

    companion object {
        fun fromString(s: String): Bot {
            val ints = s.extractInts()
            return Bot(
                location = GridPoint3d(ints[0], ints[1], ints[2]),
                signalRadius = ints[3]
            )
        }
    }

    fun signalReaches(target: GridPoint3d) = location.l1DistanceTo(target) <= signalRadius

}
