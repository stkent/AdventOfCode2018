import Tool.*

enum class RegionType {
    ROCKY, WET, NARROW;

    val allowedTools: Set<Tool> by lazy {
        return@lazy when (this) {
            ROCKY -> setOf(CLIMBING_GEAR, TORCH)
            WET -> setOf(CLIMBING_GEAR, NONE)
            NARROW -> setOf(TORCH, NONE)
        }
    }

}
