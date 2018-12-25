import GridDirection.*
import Turn.LEFT
import Turn.RIGHT

class Mine(rawMap: List<String>) {

    data class CrashData(val crashes: List<GridPoint2d>, val lastCart: GridPoint2d?)
    private data class State(val carts: List<Cart>, val crashes: List<GridPoint2d>)

    private val tracks: List<List<Char>> by lazy {
        return@lazy rawMap.map { row ->
            //@formatter:off
            row.replace("[<>]".toRegex(), "-")
               .replace("[v^]".toRegex(), "|")
               .toList()
            //@formatter:on
        }
    }

    private val cartsInitial = rawMap
        .withIndex()
        .flatMap { (y, row) ->
            //@formatter:off
            row.withIndex()
               .mapNotNull { (x, char) ->
                   Cart.direction(char)?.let { direction ->
                       Cart(position = GridPoint2d(x, y).flipY(), direction = direction)
                   }
               }
            //@formatter:on
        }

    fun computeCrashData(): CrashData {
        var state = State(carts = cartsInitial.map { it.copy() }, crashes = emptyList())

        while (state.carts.filterNot(Cart::crashed).size > 1) {
            state = advance(state)
        }

        return CrashData(
            crashes = state.crashes.map(GridPoint2d::flipY),
            lastCart = state.carts.filterNot(Cart::crashed).firstOrNull()?.position?.flipY()
        )
    }

    private fun advance(state: State): State {
        val carts = state
            .carts
            .filterNot(Cart::crashed)
            .sortedWith(compareByDescending<Cart> { it.position.y }.thenBy { it.position.x })

        val crashes = state.crashes.toMutableList()

        for (cart in carts) {
            cart.advance()

            val hitCart = (carts - cart).firstOrNull { it.position == cart.position }
            if (hitCart != null) {
                cart.crashed = true
                hitCart.crashed = true
                crashes.add(cart.position)
                continue
            }

            rotateCart(cart, track = tracks[-cart.position.y][cart.position.x])
        }

        return State(carts, crashes)
    }

    private fun rotateCart(cart: Cart, track: Char) {
        when (track) {
            '\\' -> {
                cart.direction = when (cart.direction) {
                    N, S -> cart.direction.left90()
                    E, W -> cart.direction.right90()
                }
            }

            '/' -> {
                cart.direction = when (cart.direction) {
                    N, S -> cart.direction.right90()
                    E, W -> cart.direction.left90()
                }
            }

            '+' -> {
                cart.direction = when (cart.nextIntersectionTurn) {
                    //@formatter:off
                        LEFT  -> cart.direction.left90()
                        RIGHT -> cart.direction.right90()
                        else  -> cart.direction
                        //@formatter:on
                }

                cart.intersectionCount++
            }
        }
    }

}
