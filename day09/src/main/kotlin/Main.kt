fun main() {
    val playerCount = 405
    val lastMarble = 70953

    println("Part 1 solution: ${MarbleGame(playerCount, marbleCount = lastMarble).highScore}")
    println("Part 2 solution: ${MarbleGame(playerCount, marbleCount = lastMarble * 100).highScore}")
}
