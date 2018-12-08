import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class LicenseFileTest : StringSpec({

    "Metadata sum" {
        val input = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"
        val expected = 138

        LicenseFile(contents = input).metadataSum shouldBe expected
    }

    "Root node value" {
        forall(
            // @formatter:off
            row("0 3 10 11 12", 33),                       // Node B
            row("0 1 99", 99),                             // Node D
            row("1 1 0 1 99 2", 0),                        // Node C
            row("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2", 66) // Node A
            // @formatter:on
        ) { input, expected ->
            LicenseFile(contents = input).rootNodeValue shouldBe expected
        }
    }

})
