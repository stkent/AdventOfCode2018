import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class CaveTest : StringSpec({

    "Risk" {
        val inputDepth = 510
        val inputTarget = GridPoint2d(x = 10, y = 10)
        val expectedRisk = 114

        Cave(scanDepth = inputDepth, scanTarget = inputTarget).risk() shouldBe expectedRisk
    }

    "Rescue time" {
        val inputDepth = 510
        val inputTarget = GridPoint2d(x = 10, y = 10)
        val expectedRescueTime = 45

        Cave(scanDepth = inputDepth, scanTarget = inputTarget).rescueTime() shouldBe expectedRescueTime
    }

})