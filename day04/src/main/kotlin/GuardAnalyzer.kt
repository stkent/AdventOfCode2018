import extensions.mode

class GuardAnalyzer(rawGuardEvents: List<String>) {

    private val allSleepData: Map<Int, List<Int>> by lazy {
        val result = mutableMapOf<Int, List<Int>>()

        var guardId = -1
        var sleepMinute = -1

        rawGuardEvents
            .sorted()
            .map(GuardEvent.Companion::fromString)
            .forEach { event ->
                when (event) {
                    is GuardEvent.StartsShift -> guardId = event.guardId
                    is GuardEvent.FallsAsleep -> sleepMinute = event.minute

                    is GuardEvent.WakesUp -> {
                        val wakeMinute = event.minute
                        val asleepMinutes = sleepMinute until wakeMinute
                        result[guardId] = result[guardId].orEmpty() + asleepMinutes
                    }
                }
            }

        return@lazy result
    }

    fun sleepiestGuard1(): Pair<Int, Int> {
        val (guardId: Int, guardSleptMinutes: List<Int>) =
                allSleepData.maxBy { (_, sleptMinutes) -> sleptMinutes.count() }!!

        val targetMinute = guardSleptMinutes.mode()!!.modalValue

        return guardId to targetMinute
    }

    fun sleepiestGuard2(): Pair<Int, Int> {
        return allSleepData
            .flatMap { (guardId, sleptMinutes) ->
                sleptMinutes.map { sleptMinute -> guardId to sleptMinute }
            }
            .mode()!!
            .modalValue
    }

}
