package math

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import primes

class PrimesTest : BehaviorSpec({

    Given("the limit 1") {
        val limit = 1

        When("we compute all primes up to and including that limit") {
            val primes = primes().takeWhile { it <= limit }.toList()

            Then("the result should be an empty list") {
                primes shouldBe emptyList()
            }
        }
    }

    Given("the limit 2") {
        val limit = 2

        When("we compute all primes up to and including that limit") {
            val primes = primes().takeWhile { it <= limit }.toList()

            Then("the result should be the list [2]") {
                primes shouldBe listOf(2)
            }
        }
    }

    Given("the limit 3") {
        val limit = 3

        When("we compute all primes up to and including that limit") {
            val primes = primes().takeWhile { it <= limit }.toList()

            Then("the result should be the list [2, 3]") {
                primes shouldBe listOf(2, 3)
            }
        }
    }

    Given("the limit 4") {
        val limit = 4

        When("we compute all primes up to and including that limit") {
            val primes = primes().takeWhile { it <= limit }.toList()

            Then("the result should be the list [2, 3]") {
                primes shouldBe listOf(2, 3)
            }
        }
    }

    Given("the limit 5") {
        val limit = 5

        When("we compute all primes up to and including that limit") {
            val primes = primes().takeWhile { it <= limit }.toList()

            Then("the result should be the list [2, 3, 5]") {
                primes shouldBe listOf(2, 3, 5)
            }
        }
    }

    Given("the limit 30") {
        val limit = 30

        When("we compute all primes up to and including that limit") {
            val primes = primes().takeWhile { it <= limit }.toList()

            Then("the result should be the list [2, 3, 5, 7, 11, 13, 17, 19, 23, 29]") {
                primes shouldBe listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
            }
        }
    }

})
