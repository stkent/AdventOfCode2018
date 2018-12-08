import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class LandingAreaCalculatorTest : StringSpec({

    "Safe area 1" {
        val input = listOf(
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
        )

        val expected = 17

        LandingAreaCalculator(rawTargetPoints = input).safeArea1 shouldBe expected
    }

    "Safe area 2" {
        val input = listOf(
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
        )

        val expected = 16

        LandingAreaCalculator(rawTargetPoints = input).safeArea2(distanceThreshold = 32) shouldBe expected
    }

})
