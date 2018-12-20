import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class DeviceTest : StringSpec({

    "Simple program result" {
        val inputIpRegister = 0
        val inputRawProgram = listOf(
            "seti 5 0 1",
            "seti 6 0 2",
            "addi 0 1 0",
            "addr 1 2 3",
            "setr 1 0 0",
            "seti 8 0 4",
            "seti 9 0 5"
        )

        val inputR0Value = 0

        Device(ipRegister = inputIpRegister).runProgram(
            rawProgram = inputRawProgram,
            r0Value = inputR0Value
        ) shouldBe 6
    }

})
