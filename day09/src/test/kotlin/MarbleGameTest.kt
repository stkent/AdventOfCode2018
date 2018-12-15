import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class MarbleGameTest : StringSpec({

    "High score" {
        forall(
            // @formatter:off
            row( 9,   25,     32),
            row(10, 1618,   8317),
            row(13, 7999, 146373),
            row(17, 1104,   2764),
            row(21, 6111,  54718),
            row(30, 5807,  37305)
            // @formatter:on
        ) { inputPlayerCount, inputMarbleCount, expected ->
            MarbleGame(inputPlayerCount, inputMarbleCount).highScore shouldBe expected
        }
    }

})
