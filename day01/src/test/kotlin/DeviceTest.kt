import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class DeviceTest : StringSpec({

    "Net frequency change" {
        forall(
            // @formatter:off
            row(Device(), listOf("+1", "-2", "+3", "+1"),  3),
            row(Device(), listOf("+1", "+1", "+1"),        3),
            row(Device(), listOf("+1", "+1", "-2"),        0),
            row(Device(), listOf("-1", "-2", "-3"),       -6)
            // @formatter:on
        ) { device, input, expected ->
            device.netFrequencyChange(frequencyChangeStrings = input) shouldBe expected
        }
    }

    "First repeated frequency" {
        forall(
            // @formatter:off
            row(Device(), listOf("+1", "-2", "+3", "+1"),        2),
            row(Device(), listOf("+1", "-1"),                    0),
            row(Device(), listOf("+3", "+3", "+4", "-2", "-4"), 10),
            row(Device(), listOf("-6", "+3", "+8", "+5", "-6"),  5),
            row(Device(), listOf("+7", "+7", "-2", "-7", "-4"), 14)
            // @formatter:on
        ) { device, input, expected ->
            device.firstRepeatedFrequency(frequencyChangeStrings = input) shouldBe expected
        }
    }

})
