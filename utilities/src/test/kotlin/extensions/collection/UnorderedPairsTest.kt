package extensions.collection

import extensions.unorderedPairs
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

class UnorderedPairsTest : BehaviorSpec({

    Given("an empty collection") {
        val empty: Collection<Int> = emptyList()

        When("we ask for the unordered pairs of elements in that collection") {
            val unorderedPairs = empty.unorderedPairs()

            Then("the result should be empty") {
                unorderedPairs.isEmpty() shouldBe true
            }
        }
    }

    Given("a collection of size 1") {
        val collection: Collection<Int> = listOf(9)

        When("we ask for the unordered pairs of elements in that collection") {
            val unorderedPairs = collection.unorderedPairs()

            Then("the result should be empty") {
                unorderedPairs.isEmpty() shouldBe true
            }
        }
    }

    Given("a collection of size 2 with distinct elements") {
        val collection: Collection<Int> = listOf(4, 7)

        When("we ask for the unordered pairs of elements in that collection") {
            val unorderedPairs = collection.unorderedPairs()

            Then("the result should contain a single unordered pair containing the elements in the original collection") {
                unorderedPairs shouldBe mapOf((4 to 7) to 1)
            }
        }
    }

    Given("a collection of size 3") {
        val collection: Collection<Int> = listOf(2, 5, 5)

        When("we ask for the unordered pairs of elements in that collection") {
            val unorderedPairs = collection.unorderedPairs()

            Then("the result should contain all 3 expected pairs") {
                unorderedPairs shouldBe mapOf(
                    (2 to 5) to 2,
                    (5 to 5 to 1)
                )
            }
        }
    }

    Given("a collection of size 4") {
        val collection: Collection<Int> = listOf(3, 6, 9, 9)

        When("we ask for the unordered pairs of elements in that collection") {
            val unorderedPairs = collection.unorderedPairs()

            Then("the result should contain all 6 expected pairs") {
                unorderedPairs shouldBe mapOf(
                    (3 to 6) to 1,
                    (3 to 9) to 2,
                    (6 to 9) to 2,
                    (9 to 9) to 1
                )
            }
        }
    }

})
