package extensions.collection

import extensions.orderedPairs
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

class OrderedPairsTest : BehaviorSpec({

    Given("an empty collection") {
        val empty: Collection<Int> = emptyList()

        When("we ask for the ordered pairs of elements in that collection") {
            val orderedPairs = empty.orderedPairs()

            Then("the result should be empty") {
                orderedPairs.isEmpty() shouldBe true
            }
        }
    }

    Given("a collection of size 1") {
        val collection: Collection<Int> = listOf(9)

        When("we ask for the ordered pairs of elements in that collection") {
            val orderedPairs = collection.orderedPairs()

            Then("the result should be empty") {
                orderedPairs.isEmpty() shouldBe true
            }
        }
    }

    Given("a collection of size 2") {
        val collection: Collection<Int> = listOf(4, 7)

        When("we ask for the ordered pairs of elements in that collection") {
            val orderedPairs = collection.orderedPairs()

            Then("the result should contain both the expected pairs") {
                orderedPairs shouldBe mapOf(
                    (4 to 7) to 1,
                    (7 to 4) to 1
                )
            }
        }
    }

    Given("a collection of size 3") {
        val collection: Collection<Int> = listOf(2, 5, 8)

        When("we ask for the ordered pairs of elements in that collection") {
            val orderedPairs = collection.orderedPairs()

            Then("the result should contain all 6 expected pairs") {
                orderedPairs shouldBe mapOf(
                    (2 to 5) to 1,
                    (2 to 8) to 1,
                    (5 to 8) to 1,
                    (5 to 2) to 1,
                    (8 to 2) to 1,
                    (8 to 5) to 1
                )
            }
        }
    }

    Given("a collection of size 4") {
        val collection: Collection<Int> = listOf(3, 6, 9, 11)

        When("we ask for the ordered pairs of elements in that collection") {
            val orderedPairs = collection.orderedPairs()

            Then("the result should contain all 12 expected pairs") {
                orderedPairs shouldBe mapOf(
                    (3 to 6) to 1,
                    (3 to 9) to 1,
                    (3 to 11) to 1,
                    (6 to 9) to 1,
                    (6 to 11) to 1,
                    (9 to 11) to 1,
                    (6 to 3) to 1,
                    (9 to 3) to 1,
                    (11 to 3) to 1,
                    (9 to 6) to 1,
                    (11 to 6) to 1,
                    (11 to 9) to 1
                )
            }
        }
    }

})
