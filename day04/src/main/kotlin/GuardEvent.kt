sealed class GuardEvent {
    //@formatter:off
    data class StartsShift(val guardId: Int) : GuardEvent()
    data class FallsAsleep(val minute: Int)  : GuardEvent()
    data class WakesUp(val minute: Int)      : GuardEvent()
    //@formatter:on

    companion object {
        private val guardIdRegex = "#(\\d+)".toRegex()
        private val minuteRegex = ":(\\d{2})".toRegex()

        fun fromString(s: String): GuardEvent {
            return when {
                //@formatter:off
                       "Guard" in s -> StartsShift(guardId = guardIdRegex.find(s)!!.groupValues[1].toInt())
                "falls asleep" in s -> FallsAsleep( minute =  minuteRegex.find(s)!!.groupValues[1].toInt())
                    "wakes up" in s ->     WakesUp( minute =  minuteRegex.find(s)!!.groupValues[1].toInt())
                               else -> throw IllegalStateException("Unrecognized input string")
                //@formatter:on
            }
        }
    }
}
