typealias Cell = GridPoint2d

data class Result(val corner: Cell, val size: Int, val power: Int)

class Device(private val serialNumber: Int) {

    companion object {
        private const val GRID_SIZE = 300
    }

    private val summedAreaTable: Map<Cell, Int> = run {
        val result = mutableMapOf<Cell, Int>()

        val allPoints = (1..GRID_SIZE).flatMap { y -> (1..GRID_SIZE).map { x -> Cell(x, y) } }

        allPoints.forEach { cell ->
            //@formatter:off
            val current  = cellPower(cell)
            val leftSum  = result.getOrDefault(cell.offsetBy(dx = -1), 0)
            val aboveSum = result.getOrDefault(cell.offsetBy(dy = -1), 0)
            val dupeSum  = result.getOrDefault(cell.offsetBy(dx = -1, dy = -1), 0)
            //@formatter:on

            result[cell] = current + leftSum + aboveSum - dupeSum
        }

        return@run result
    }

    fun cellPower(cell: Cell): Int {
        val rackId = cell.x + 10
        var powerLevel = rackId * cell.y
        powerLevel += serialNumber
        powerLevel *= rackId
        powerLevel = (powerLevel / 100) % 10
        powerLevel -= 5
        return powerLevel
    }

    fun maxSquarePower(minSize: Int, maxSize: Int = GRID_SIZE): Result {
        return (minSize..maxSize).flatMap { size ->
            (1..GRID_SIZE - size + 1).flatMap { y ->
                (1..GRID_SIZE - size + 1).map { x ->
                    val corner = Cell(x, y)
                    val power = squarePower(x, y, size)
                    return@map Result(corner, size, power)
                }
            }
        }.maxBy(Result::power)!!
    }

    private fun squarePower(x: Int, y: Int, size: Int): Int {
        return summedAreaTable[Cell(x + size - 1, y + size - 1)]!! -
                summedAreaTable.getOrDefault(Cell(x + size - 1, y - 1), 0) -
                summedAreaTable.getOrDefault(Cell(x - 1, y + size - 1), 0) +
                summedAreaTable.getOrDefault(Cell(x - 1, y - 1), 0)
    }

}
