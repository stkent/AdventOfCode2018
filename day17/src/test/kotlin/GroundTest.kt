import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class GroundTest : StringSpec({

    "No trapped water" {
        Ground.fromSlice(
            listOf(
                ".+.",
                "█.█",
                "█.█"
            )
        ).wettableArea shouldBe 2
    }

//    "Lopsided bucket 1" {
//        Ground.fromSlice(
//            listOf(
//                "...+..",
//                "█....█",
//                "......",
//                ".█....",
//                ".█..█.",
//                ".████.",
//                "......"
//            )
//        ).wettableArea shouldBe 11
//    }
//
//    "Lopsided bucket 2" {
//        Ground.fromSlice(
//            listOf(
//                "..+...",
//                "█....█",
//                "......",
//                "....█.",
//                ".█..█.",
//                ".████.",
//                "......"
//            )
//        ).wettableArea shouldBe 11
//    }

//    "Symmetrical bucket" {
//        Ground.fromSlice(
//            listOf(
//                "..+...",
//                "█....█",
//                "......",
//                ".█..█.",
//                ".████.",
//                "......"
//            )
//        ).wettableArea shouldBe 15
//    }
//
//    "Lopsided bucket 2" {
//        Ground.fromSlice(
//            listOf(
//                "...+..",
//                "█....█",
//                "......",
//                "....█.",
//                ".█..█.",
//                ".████.",
//                "......"
//            )
//        ).wettableArea shouldBe 11
//    }
//
//    "Submerged block 1" {
//        Ground.fromSlice(
//            listOf(
//                "..+......",
//                "█.......█",
//                ".........",
//                ".█.....█.",
//                ".█.███.█.",
//                ".█.█.█.█.",
//                ".█.███.█.",
//                ".█.....█.",
//                ".███████.",
//                "........."
//            )
//        ).wettableArea shouldBe 41
//    }
//
//    "Submerged block 2" {
//        Ground.fromSlice(
//            listOf(
//                "...+.....",
//                "█.......█",
//                ".........",
//                ".█.....█.",
//                ".█.███.█.",
//                ".█.█.█.█.",
//                ".█.███.█.",
//                ".█.....█.",
//                ".███████.",
//                "........."
//            )
//        ).wettableArea shouldBe 41
//    }
//
//    "Submerged block 3" {
//        Ground.fromSlice(
//            listOf(
//                "....+....",
//                "█.......█",
//                ".........",
//                ".█.....█.",
//                ".█.███.█.",
//                ".█.█.█.█.",
//                ".█.███.█.",
//                ".█.....█.",
//                ".███████.",
//                "........."
//            )
//        ).wettableArea shouldBe 41
//    }
//
//    "Submerged symmetrical bucket 1" {
//        Ground.fromSlice(
//            listOf(
//                "..+......",
//                "█.......█",
//                ".........",
//                ".█.....█.",
//                ".█.█.█.█.",
//                ".█.█.█.█.",
//                ".█.███.█.",
//                ".█.....█.",
//                ".███████.",
//                "........."
//            )
//        ).wettableArea shouldBe 43
//    }
//
//    "Submerged symmetrical bucket 2" {
//        Ground.fromSlice(
//            listOf(
//                "...+.....",
//                "█.......█",
//                ".........",
//                ".█.....█.",
//                ".█.█.█.█.",
//                ".█.█.█.█.",
//                ".█.███.█.",
//                ".█.....█.",
//                ".███████.",
//                "........."
//            )
//        ).wettableArea shouldBe 43
//    }
//
//    "Submerged symmetrical bucket 3" {
//        Ground.fromSlice(
//            listOf(
//                "....+....",
//                "█.......█",
//                ".........",
//                ".█.....█.",
//                ".█.█.█.█.",
//                ".█.█.█.█.",
//                ".█.███.█.",
//                ".█.....█.",
//                ".███████.",
//                "........."
//            )
//        ).wettableArea shouldBe 43
//    }
//
//    "Partially submerged symmetrical bucket 1" {
//        Ground.fromSlice(
//            listOf(
//                "..+......",
//                "█.......█",
//                ".........",
//                "...█.█...",
//                ".█.█.█.█.",
//                ".█.███.█.",
//                ".█.....█.",
//                ".█.....█.",
//                ".█.....█.",
//                ".███████.",
//                "........."
//            )
//        ).wettableArea shouldBe 29
//    }
//
//    "Partially submerged symmetrical bucket 2" {
//        Ground.fromSlice(
//            listOf(
//                "...+.....",
//                "█.......█",
//                ".........",
//                "...█.█...",
//                ".█.█.█.█.",
//                ".█.███.█.",
//                ".█.....█.",
//                ".█.....█.",
//                ".█.....█.",
//                ".███████.",
//                "........."
//            )
//        ).wettableArea shouldBe 47
//    }
//
//    "Partially submerged symmetrical bucket 3" {
//        Ground.fromSlice(
//            listOf(
//                "....+....",
//                "█.......█",
//                ".........",
//                "...█.█...",
//                ".█.█.█.█.",
//                ".█.███.█.",
//                ".█.....█.",
//                ".█.....█.",
//                ".█.....█.",
//                ".███████.",
//                "........."
//            )
//        ).wettableArea shouldBe 47
//    }

})
