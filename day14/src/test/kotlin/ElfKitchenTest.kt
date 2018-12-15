import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class ElfKitchenTest : StringSpec({

    "Ten scores after recipe count" {
        forall(
            // @formatter:off
            row(   9, "5158916779"),
            row(   5, "0124515891"),
            row(  18, "9251071085"),
            row(2018, "5941429882")
            // @formatter:on
        ) { input, expected ->
            ElfKitchen().tenScoresAfterRecipeCount(count = input) shouldBe expected
        }
    }

    "Recipe count before score sequence" {
        forall(
            row(51589, 9),
            row(92510, 18),
            row(59414, 2018)
        ) { input, expected ->
            ElfKitchen().recipeCountBeforeScoreSequence(sequence = input) shouldBe expected
        }
    }

})
