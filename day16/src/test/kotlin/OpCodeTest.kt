import OpCode.*
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class OpCodeTest: StringSpec({

    "Op codes consistent with sample are identified correctly" {
        val input = Sample(
            registersBefore = listOf(3, 2, 1, 1),
            instruction = Instruction(
                opCodeInt = 9,
                a = 2,
                b = 1,
                c = 2
            ),
            registersAfter = listOf(3, 2, 2, 1)
        )

        val expected = listOf(ADDI, MULR, SETI)

        OpCode.opCodesConsistentWith(sample = input) shouldBe expected
    }

})
