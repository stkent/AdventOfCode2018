import extensions.nonNegativeRem

fun main() {
    val playerCount = 405
    val lastMarble = 70953

    println(MarbleGame(playerCount, lastMarble).highScore)
//    println("${MarbleGame(9, 25).highScore}, 32")
//    println("${MarbleGame(10, 1618).highScore}, 8317")
//    println("${MarbleGame(13, 7999).highScore}, 146373")
//    println("${MarbleGame(17, 1104).highScore}, 2764")
//    println("${MarbleGame(21, 6111).highScore}, 54718")
//    println("${MarbleGame(30, 5807).highScore}, 37305")


}

class MarbleGame(private val playerCount: Int, private val marbleCount: Int) {

    val highScore: Int by lazy {
        val scores = mutableMapOf<Int, Int>()
        val circle = mutableListOf(0)
        var currentMarbleIndex = 0
        var nextPlayer = 1
        var nextMarble = 1

        while (nextMarble <= marbleCount) {
            if (nextMarble % 23 == 0) {
                var playerScore = scores.getOrDefault(nextPlayer, 0)

                playerScore += nextMarble

                val removeIndex = (currentMarbleIndex - 7).nonNegativeRem(circle.size)
                playerScore += circle[removeIndex]
                circle.removeAt(removeIndex)

                scores[nextPlayer] = playerScore
                currentMarbleIndex = removeIndex % circle.size
            } else {
                val insertMarbleIndex = ((currentMarbleIndex + 1) % circle.size) + 1
                circle.add(index = insertMarbleIndex, element = nextMarble)
                currentMarbleIndex = insertMarbleIndex
            }

            nextPlayer = ((nextPlayer - 1) % playerCount) + 2
            nextMarble++
        }

        println(scores)

        return@lazy scores.values.max()!!
    }

}

class MutableCircle<T>(private val backingList: MutableList<T>) : MutableList<T> by backingList {

    override fun get(index: Int): T {
        return backingList[index % size]
    }

    override fun add(index: Int, element: T) {
        add(index % size, element)
    }

}
