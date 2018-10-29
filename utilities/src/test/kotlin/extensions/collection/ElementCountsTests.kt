package extensions.collection

import extensions.elementCounts
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

class ElementCountsTests : BehaviorSpec({

    Given("an empty list") {
        val list = emptyList<Int>()

        When("we count the occurrences of each element") {
            val elementCounts = list.elementCounts()

            Then("the result is an empty map") {
                elementCounts shouldBe emptyMap<Int, Int>()
            }
        }
    }

    Given("a list with one element, \"first\"") {
        val list = listOf("first")

        When("we count the occurrences of each element") {
            val elementCounts = list.elementCounts()

            Then("the result is a map containing a single entry, with key \"first\" and value 1") {
                elementCounts shouldBe mapOf("first" to 1)
            }
        }
    }

    Given("a list with two distinct elements, \"first\" and \"second\"") {
        val list = listOf("first", "second")

        When("we count the occurrences of each element") {
            val elementCounts = list.elementCounts()

            Then("the result is a map containing two entries, with keys \"first\" and \"second\" and values 1 and 1") {
                elementCounts shouldBe mapOf("first" to 1, "second" to 1)
            }
        }
    }

    Given("a list with one repeated element") {
        val list = listOf("first", "second", "first")

        When("we count the occurrences of each element") {
            val elementCounts = list.elementCounts()

            Then("the resulting map entry has value 2") {
                elementCounts["first"] shouldBe 2
            }
        }
    }

    Given("a non-empty list") {
        val list = listOf("first", "second", "third")

        When("we query the number of occurrences of an element not in the list") {
            val elementCounts = list.elementCounts()
            val missingElementCount = elementCounts["four"]

            Then("the result should be null") {
                missingElementCount shouldBe null
            }
        }
    }

})
