fun main() {
    val input = resourceFile("input.txt").readLines()

    val battle = Battle(rawMap = input)

    val result = battle.execute()
    println(result)
}
