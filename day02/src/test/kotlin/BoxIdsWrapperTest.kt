
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class BoxIdsWrapperTest : StringSpec({

    "Checksum" {
        val input = listOf("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab")
        val expected = 12

        BoxIdsWrapper(boxIds = input).checksum shouldBe expected
    }

    "Prototype box ids common chars" {
        val input = listOf("abcde", "fghij", "klmno", "pqrst", "fguij", "axcye", "wvxyz")
        val expected = "fgij"

        BoxIdsWrapper(boxIds = input).prototypeBoxIdsCommonChars shouldBe expected
    }

})
