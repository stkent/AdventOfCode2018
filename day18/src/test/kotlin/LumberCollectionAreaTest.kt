import LumberCollectionArea.AcreTypeCounts
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class LumberCollectionAreaTest : StringSpec({

    "Small example" {
        val input = listOf(
            ".#.#...|#.",
            ".....#|##|",
            ".|..|...#.",
            "..|#.....#",
            "#.#|||#|#|",
            "...#.||...",
            ".|....|...",
            "||...#|.#|",
            "|.||||..|.",
            "...#.|..|."
        )

        val expected = AcreTypeCounts(trees = 37, yard = 31)

        LumberCollectionArea(rawMap = input).countsAtTime(10) shouldBe expected
    }

})
