
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class RouterTest : StringSpec({

    "Furthest point" {
        forall(
            // @formatter:off
            row(Router(), "WNE",                                                              3),
            row(Router(), "ENWWW(NEEE|SSE(EE|N))",                                           10),
            row(Router(), "ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN",                         18),
            row(Router(), "ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))",               23),
            row(Router(), "WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))", 31)
            // @formatter:on
        ) { router, input, expected ->
            router.computeBestPathLengths(regex = input).values.max() shouldBe expected
        }
    }

})
