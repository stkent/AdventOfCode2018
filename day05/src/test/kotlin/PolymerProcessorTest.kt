import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class PolymerProcessorTest : StringSpec({

    "Fully react" {
        forall(
            // @formatter:off
            row(PolymerProcessor(),               "aA", ""),
            row(PolymerProcessor(),             "abBA", ""),
            row(PolymerProcessor(),             "abAB", "abAB"),
            row(PolymerProcessor(),           "aabAAB", "aabAAB"),
            row(PolymerProcessor(), "dabAcCaCBAcCcaDA", "dabCBAcaDA")
            // @formatter:on
        ) { polymerProcessor, input, expected ->
            polymerProcessor.fullyReact(polymer = input) shouldBe expected
        }
    }

    "Optimize and fully react" {
        val input = "dabAcCaCBAcCcaDA"
        val expected = "daDA"

        PolymerProcessor().optimizeAndFullyReact(polymer = input) shouldBe expected
    }

})
