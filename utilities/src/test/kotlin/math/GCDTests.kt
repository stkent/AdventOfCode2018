package math

import gcd
import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

class GCDTests : BehaviorSpec({

    Given("any positive integer") {
        val integer = 72

        When("we compute the greatest common divisor of that integer and itself") {
            val gcd = gcd(integer, integer)

            Then("the result should be the same as the original number") {
                gcd shouldBe integer
            }
        }
    }

    Given("the coprime integers 8 and 9") {
        val left = 8
        val right = 9

        When("we compute their greatest common divisor") {
            val gcd = gcd(left, right)

            Then("the result should be 1") {
                gcd shouldBe 1
            }
        }
    }

    Given("the non-coprime integers 54 and 24") {
        val left = 54
        val right = 24

        When("we compute their greatest common divisor") {
            val gcd = gcd(left, right)

            Then("the result should be 6") {
                gcd shouldBe 6
            }
        }
    }

    Given("a positive integer") {
        val integer = 9

        When("we compute the greatest common divisor of that integer and 0") {
            val gcd = gcd(integer, 0)

            Then("the result should be 0") {
                gcd shouldBe 0
            }
        }
    }

})
