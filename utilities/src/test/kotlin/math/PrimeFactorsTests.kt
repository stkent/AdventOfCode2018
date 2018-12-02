package math

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import primeFactors

class PrimeFactorsTests : BehaviorSpec({

    Given("the integer 2") {
        val integer = 2

        When("we compute the prime factors of that integer") {
            val factors = primeFactors(integer)

            Then("the result should be [2]") {
                factors shouldBe listOf(2)
            }
        }
    }

    Given("the integer 3") {
        val integer = 3

        When("we compute the prime factors of that integer") {
            val factors = primeFactors(integer)

            Then("the result should be [3]") {
                factors shouldBe listOf(3)
            }
        }
    }

    Given("the integer 4") {
        val integer = 4

        When("we compute the prime factors of that integer") {
            val factors = primeFactors(integer)

            Then("the result should be [2, 2]") {
                factors shouldBe listOf(2, 2)
            }
        }
    }

    Given("the integer 5") {
        val integer = 5

        When("we compute the prime factors of that integer") {
            val factors = primeFactors(integer)

            Then("the result should be [5]") {
                factors shouldBe listOf(5)
            }
        }
    }

    Given("the integer 6") {
        val integer = 6

        When("we compute the prime factors of that integer") {
            val factors = primeFactors(integer)

            Then("the result should be [2, 3]") {
                factors shouldBe listOf(2, 3)
            }
        }
    }

    Given("the integer 8") {
        val integer = 8

        When("we compute the prime factors of that integer") {
            val factors = primeFactors(integer)

            Then("the result should be [2, 2, 2]") {
                factors shouldBe listOf(2, 2, 2)
            }
        }
    }

})
