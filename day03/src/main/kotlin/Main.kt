import extensions.elementCounts

fun main() {
    val input = resourceFile("input.txt").readLines()
        .map {
            // #1372 @ 865,426: 27x15
            val vals = it.replace("[^\\d]+".toRegex(), "/").split("/").filter { it.isNotEmpty() }.map(String::toInt)
            Claim(vals[0], vals[1], vals[2], vals[3], vals[4])
        }

    println(input.flatMap { it.coveredGridPoints() }
        .elementCounts()
        .count { it.value > 1 })

    for (claim in input) {
        val otherClaims = input - claim

        if (otherClaims.none { it.intersects(claim) }) {
            println(claim)
            break
        }
    }

}

data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int)

fun Claim.coveredGridPoints(): List<GridPoint2d> {
    val result = mutableListOf<GridPoint2d>()

    for (x in left until (left + width)) {
        for (y in top until (top + height)) {
            result.add(GridPoint2d(x, y))
        }
    }

    return result
}

fun Claim.intersects(other: Claim): Boolean {
    return coveredGridPoints().intersect(other.coveredGridPoints()).isNotEmpty()
}