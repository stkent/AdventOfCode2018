package extensions.pair

import extensions.flip
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

class FlipTests : BehaviorSpec({

    Given("a pair (a, a)") {
        val a = 1
        val pair = Pair(a, a)

        When("we extensions.flip the pair") {
            val flippedPair = pair.flip()

            Then("the result should be equal to the original pair") {
                flippedPair shouldBe pair
            }
        }
    }

    Given("a pair (a, b) with a != b") {
        val a = 1
        val b = "2"

        val pair = Pair(a, b)

        When("we extensions.flip the pair") {
            val flippedPair = pair.flip()

            Then("the result should be the pair (b, a)") {
                flippedPair shouldBe Pair(b, a)
            }
        }
    }

})
