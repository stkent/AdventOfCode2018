fun main() {
//    val input = resourceFile("input.txt").readLines()

    val input = listOf(
        "███████",
        "█.G.E.█",
        "█E.G.E█",
        "█.GGE.█",
        "███████"
    )

    val noMovementInput = listOf(
        "███████",
        "█G....█",
        "█....G█",
        "█...GE█",
        "███████"
    )

    val topLeftWouldMoveOnceOnly = listOf(
        "███████",
        "█G...G█",
        "█.....█",
        "█...GE█",
        "███████"
    )

    val battle = Battle(rawMap = input)

    val result = battle.execute()
    println(result)
}
