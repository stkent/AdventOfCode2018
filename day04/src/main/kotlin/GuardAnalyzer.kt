import extensions.elementCounts

class GuardAnalyzer(rawGuardEvents: List<String>) {

    private val allGuardSleepData: Map<Int, List<Int>> by lazy {
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
        val (mostSleepyGuardId, mostSleepyGuardData) =
                allGuardSleepData.maxBy { (_, sleptMinutes) -> sleptMinutes.count() }!!

        val targetMinute = mostSleepyGuardData
            .elementCounts()
            .maxBy { countedMinute -> countedMinute.value }!!
            .key

        return mostSleepyGuardId to targetMinute
    }

    fun sleepiestGuard2(): Pair<Int, Int> {
        val (mostSleepyGuardId, mostSleptMinuteData) =
                allGuardSleepData
                    .mapValues { (_, sleptMinutes) ->
                        sleptMinutes
                            .elementCounts()
                            .maxBy { countedMinute -> countedMinute.value }!!
                    }
                    .maxBy { (_, countedMinute) -> countedMinute.value }!!

        val (targetMinute, _) = mostSleptMinuteData

        return mostSleepyGuardId to targetMinute
    }

}
