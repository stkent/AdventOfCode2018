import extensions.extractInts

fun main() {
    val input = resourceFile("input.txt")
        .readLines()
        .map {
            val coords = it.extractInts()
            GridPoint2d(x = coords.first(), y = coords.last())
        }

    val minX = input.map { it.x }.min()!!
    val maxX = input.map { it.x }.max()!!
    val minY = input.map { it.y }.min()!!
    val maxY = input.map { it.y }.max()!!

    val countMap = mutableMapOf<GridPoint2d, List<GridPoint2d>>()

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            val target = GridPoint2d(x = x, y = y)
            val distances = input.map { it to it.l1DistanceTo(target) }
                .sortedBy { it.second }

            val closest = distances[0]
            val secondClosest = distances[1]

            if (closest.second < secondClosest.second) {
                countMap[closest.first] = countMap[closest.first].orEmpty() + target
            }
        }
    }

    val s = countMap
        .filterNot { it.value.any { it.x == minX || it.x == maxY || it.y == minY || it.y == maxY } }
        .mapValues { (_, v) -> v.count() }


    println(s.maxBy { it.value })

    var regionSize = 0

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            val target = GridPoint2d(x = x, y = y)

            if (input.sumBy { it.l1DistanceTo(target) } < 10000) {
                regionSize++
            }
        }
    }

    println(regionSize)
}
