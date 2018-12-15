import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class MineTest: StringSpec({

    "Head on collision type 1" {
        val input = listOf(">-<")
        val expectedFirstCrash = GridPoint2d(x = 1, y = 0)
        val expectedLastCart = null

        val (crashes, lastCart) = Mine(rawMap = input).computeCrashData()
        crashes.first() shouldBe expectedFirstCrash
        lastCart shouldBe expectedLastCart
    }

    "Head on collision type 2" {
        val input = listOf("><")
        val expectedFirstCrash = GridPoint2d(x = 1, y = 0)
        val expectedLastCart = null

        val (crashes, lastCart) = Mine(rawMap = input).computeCrashData()
        crashes.first() shouldBe expectedFirstCrash
        lastCart shouldBe expectedLastCart
    }

    "Complex example 1" {
        val input = listOf(
            """/->-\        """,
            """|   |  /----\""",
            """| /-+--+-\  |""",
            """| | |  | v  |""",
            """\-+-/  \-+--/""",
            """\------/     """
        )

        val expectedFirstCrash = GridPoint2d(x = 7, y = 3)
        val expectedLastCart = null

        val (crashes, lastCart) = Mine(rawMap = input).computeCrashData()
        crashes.first() shouldBe expectedFirstCrash
        lastCart shouldBe expectedLastCart
    }

    "Complex example 2" {
        val input = listOf(
            """/>-<\  """,
            """|   |  """,
            """| /<+-\""",
            """| | | v""",
            """\>+</ |""",
            """  |   ^""",
            """  \<->/"""
        )

        val expectedFirstCrash = GridPoint2d(x = 2, y = 0)
        val expectedLastCart = GridPoint2d(x = 6, y = 4)

        val (crashes, lastCart) = Mine(rawMap = input).computeCrashData()
        crashes.first() shouldBe expectedFirstCrash
        lastCart shouldBe expectedLastCart
    }

})
