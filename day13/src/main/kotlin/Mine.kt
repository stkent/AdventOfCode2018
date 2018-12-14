import Direction.*
import Turn.LEFT
import Turn.RIGHT

class Mine(rawMap: List<String>) {

    data class CrashData(val crashes: List<GridPoint2d>, val lastCart: GridPoint2d?)

    private val tracks: List<List<Char>> by lazy {
        return@lazy rawMap.map { row ->
            //@formatter:off
            row.replace("[<>]".toRegex(), "-")
               .replace("[v^]".toRegex(), "|")
               .toList()
            //@formatter:on
        }
    }

    private val crashes = mutableListOf<GridPoint2d>()

    private val allCarts = rawMap
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
        while (allCarts.filterNot(Cart::hasCrashed).size > 1) {
            advance()
        }

        return CrashData(
            crashes = crashes.map(GridPoint2d::flipY),
            lastCart = allCarts.filterNot(Cart::hasCrashed).firstOrNull()?.position?.flipY()
        )
    }

    private fun advance() {
        val cartsToAdvance = allCarts
            .filterNot(Cart::hasCrashed)
            .sortedWith(compareByDescending<Cart> { it.position.y }.thenBy { it.position.x })

        for (cart in cartsToAdvance) {
            cart.advance()

            val hitCart = (cartsToAdvance - cart).firstOrNull { it.position == cart.position }
            if (hitCart != null) {
                cart.didCrash()
                hitCart.didCrash()
                crashes.add(cart.position)
                continue
            }

            rotateCart(cart, track = tracks[-cart.position.y][cart.position.x])
        }
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

                cart.didPassThroughIntersection()
            }
        }
    }

}
