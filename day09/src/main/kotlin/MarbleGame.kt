import extensions.rotate
import java.util.*

class MarbleGame(private val playerCount: Int, private val marbleCount: Int) {

    val highScore: Long by lazy {
        val scores = mutableMapOf<Int, Long>()
        val circle = LinkedList<Int>().apply { add(0) }
        var player = 0
        var marble = 1

        while (marble <= marbleCount) {
            if (marble % 23 == 0) {
                var playerScore = scores.getOrDefault(player, 0)

                playerScore += marble.toLong()

                circle.rotate(-7)
                playerScore += circle.removeFirst()

                scores[player] = playerScore
            } else {
                circle.rotate(2)
                circle.addFirst(marble)
            }

            player = (player + 1) % playerCount
            marble++
        }

        return@lazy scores.values.max()!!
    }

}
