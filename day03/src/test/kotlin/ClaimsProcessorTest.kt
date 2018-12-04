import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class ClaimsProcessorTest : StringSpec({

    "Overlapped square count" {
        val input = listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")
        val expected = 4

        ClaimsProcessor().computeOverlappedSquareCount(claimStrings = input) shouldBe expected
    }

    "Isolated claim id" {
        val input = listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2")
        val expected = 3

        ClaimsProcessor().computeIsolatedClaimId(claimStrings = input) shouldBe expected
    }

})
