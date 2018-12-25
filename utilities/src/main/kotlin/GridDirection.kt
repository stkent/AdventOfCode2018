enum class GridDirection {
    N, E, S, W;

    fun toVector(): GridVector2d {
        return when (this) {
            //@formatter:off
            N -> GridVector2d( 0,  1)
            E -> GridVector2d( 1,  0)
            S -> GridVector2d( 0, -1)
            W -> GridVector2d(-1,  0)
            //@formatter:on
        }
    }

    fun left90() = GridDirection.values()[(ordinal + 3) % 4]

    fun right90() = GridDirection.values()[(ordinal + 1) % 4]

    @Suppress("FunctionName")
    fun `180`() = GridDirection.values()[(ordinal + 2) % 4]
}
