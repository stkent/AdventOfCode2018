import Ground.WetAreas
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class GroundTest : StringSpec({

    // .+.
    // █|█
    // █|█
    // .|.
    "No trapped water" {
        Ground.fromSlice(
            listOf(
                "...",
                "█.█",
                "█.█",
                "..."
            )
        ).computeWetAreas(spring = GridPoint2d(x = 1, y = 0)) shouldBe WetAreas(settled = 0, running = 2)
    }

    // ..+...
    // █.|..█
    // ..|...
    // .█||||
    // .█~~█|
    // .████|
    // .....|
    "Lopsided bucket 1" {
        Ground.fromSlice(
            listOf(
                "......",
                "█....█",
                "......",
                ".█....",
                ".█..█.",
                ".████.",
                "......"
            )
        ).computeWetAreas(spring = GridPoint2d(x = 2, y = 0)) shouldBe WetAreas(settled = 2, running = 8)
    }

    // ...+..
    // █..|.█
    // ...|..
    // .█||||
    // .█~~█|
    // .████|
    // .....|
    "Lopsided bucket 2" {
        Ground.fromSlice(
            listOf(
                "......",
                "█....█",
                "......",
                ".█....",
                ".█..█.",
                ".████.",
                "......"
            )
        ).computeWetAreas(spring = GridPoint2d(x = 3, y = 0)) shouldBe WetAreas(settled = 2, running = 8)
    }

    // ....+.
    // █...|█
    // ....|.
    // .█||||
    // .█~~█|
    // .████|
    // .....|
    "Lopsided bucket 3" {
        Ground.fromSlice(
            listOf(
                "......",
                "█....█",
                "......",
                ".█....",
                ".█..█.",
                ".████.",
                "......"
            )
        ).computeWetAreas(spring = GridPoint2d(x = 4, y = 0)) shouldBe WetAreas(settled = 2, running = 8)
    }

    // ..+...
    // █.|..█
    // ||||||
    // |█~~█|
    // |████|
    // |....|
    "Symmetrical bucket" {
        Ground.fromSlice(
            listOf(
                "......",
                "█....█",
                "......",
                ".█..█.",
                ".████.",
                "......"
            )
        ).computeWetAreas(spring = GridPoint2d(x = 2, y = 0)) shouldBe WetAreas(settled = 2, running = 11)
    }

    // ..+......
    // █.|.....█
    // |||||||||
    // |█~~~~~█|
    // |█~███~█|
    // |█~█.█~█|
    // |█~███~█|
    // |█~~~~~█|
    // |███████|
    // |.......|
    "Submerged block 1" {
        Ground.fromSlice(
            listOf(
                "..+......",
                "█.......█",
                ".........",
                ".█.....█.",
                ".█.███.█.",
                ".█.█.█.█.",
                ".█.███.█.",
                ".█.....█.",
                ".███████.",
                "........."
            )
        ).computeWetAreas(spring = GridPoint2d(x = 2, y = 0)) shouldBe WetAreas(settled = 16, running = 22)
    }

    // ...+.....
    // █..|....█
    // |||||||||
    // |█~~~~~█|
    // |█~███~█|
    // |█~█.█~█|
    // |█~███~█|
    // |█~~~~~█|
    // |███████|
    // |.......|
    "Submerged block 2" {
        Ground.fromSlice(
            listOf(
                "...+.....",
                "█.......█",
                ".........",
                ".█.....█.",
                ".█.███.█.",
                ".█.█.█.█.",
                ".█.███.█.",
                ".█.....█.",
                ".███████.",
                "........."
            )
        ).computeWetAreas(spring = GridPoint2d(x = 3, y = 0)) shouldBe WetAreas(settled = 16, running = 22)
    }

    // ....+....
    // █...|...█
    // |||||||||
    // |█~~~~~█|
    // |█~███~█|
    // |█~█.█~█|
    // |█~███~█|
    // |█~~~~~█|
    // |███████|
    // |.......|
    "Submerged block 3" {
        Ground.fromSlice(
            listOf(
                ".........",
                "█.......█",
                ".........",
                ".█.....█.",
                ".█.███.█.",
                ".█.█.█.█.",
                ".█.███.█.",
                ".█.....█.",
                ".███████.",
                "........."
            )
        ).computeWetAreas(spring = GridPoint2d(x = 4, y = 0)) shouldBe WetAreas(settled = 16, running = 22)
    }

    // ..+......
    // █.|.....█
    // |||||||||
    // |█~~~~~█|
    // |█~█~█~█|
    // |█~█~█~█|
    // |█~███~█|
    // |█~~~~~█|
    // |███████|
    // |.......|
    "Submerged symmetrical bucket 1" {
        Ground.fromSlice(
            listOf(
                ".........",
                "█.......█",
                ".........",
                ".█.....█.",
                ".█.█.█.█.",
                ".█.█.█.█.",
                ".█.███.█.",
                ".█.....█.",
                ".███████.",
                "........."
            )
        ).computeWetAreas(spring = GridPoint2d(x = 2, y = 0)) shouldBe WetAreas(settled = 18, running = 22)
    }

    // ...+.....
    // █..|....█
    // |||||||||
    // |█~~~~~█|
    // |█~█~█~█|
    // |█~█~█~█|
    // |█~███~█|
    // |█~~~~~█|
    // |███████|
    // |.......|
    "Submerged symmetrical bucket 2" {
        Ground.fromSlice(
            listOf(
                ".........",
                "█.......█",
                ".........",
                ".█.....█.",
                ".█.█.█.█.",
                ".█.█.█.█.",
                ".█.███.█.",
                ".█.....█.",
                ".███████.",
                "........."
            )
        ).computeWetAreas(spring = GridPoint2d(x = 3, y = 0)) shouldBe WetAreas(settled = 18, running = 22)
    }

    // ....+....
    // █...|...█
    // |||||||||
    // |█~~~~~█|
    // |█~█~█~█|
    // |█~█~█~█|
    // |█~███~█|
    // |█~~~~~█|
    // |███████|
    // |.......|
    "Submerged symmetrical bucket 3" {
        Ground.fromSlice(
            listOf(
                ".........",
                "█.......█",
                ".........",
                ".█.....█.",
                ".█.█.█.█.",
                ".█.█.█.█.",
                ".█.███.█.",
                ".█.....█.",
                ".███████.",
                "........."
            )
        ).computeWetAreas(spring = GridPoint2d(x = 4, y = 0)) shouldBe WetAreas(settled = 18, running = 22)
    }

    // ..+......
    // █.|.....█
    // ..|......
    // |||█.█...
    // |█~█.█.█.
    // |█~███.█.
    // |█~~~~~█.
    // |█~~~~~█.
    // |█~~~~~█.
    // |███████.
    // |........
    "Partially submerged symmetrical bucket 1" {
        Ground.fromSlice(
            listOf(
                ".........",
                "█.......█",
                ".........",
                "...█.█...",
                ".█.█.█.█.",
                ".█.███.█.",
                ".█.....█.",
                ".█.....█.",
                ".█.....█.",
                ".███████.",
                "........."
            )
        ).computeWetAreas(spring = GridPoint2d(x = 2, y = 0)) shouldBe WetAreas(settled = 17, running = 11)
    }

    // ...+.....
    // █..|....█
    // ..|||||..
    // |||█~█|||
    // |█~█~█~█|
    // |█~███~█|
    // |█~~~~~█|
    // |█~~~~~█|
    // |█~~~~~█|
    // |███████|
    // |.......|
    "Partially submerged symmetrical bucket 2" {
        Ground.fromSlice(
            listOf(
                ".........",
                "█.......█",
                ".........",
                "...█.█...",
                ".█.█.█.█.",
                ".█.███.█.",
                ".█.....█.",
                ".█.....█.",
                ".█.....█.",
                ".███████.",
                "........."
            )
        ).computeWetAreas(spring = GridPoint2d(x = 3, y = 0)) shouldBe WetAreas(settled = 21, running = 24)
    }

    // ....+....
    // █...|...█
    // ..|||||..
    // |||█~█|||
    // |█~█~█~█|
    // |█~███~█|
    // |█~~~~~█|
    // |█~~~~~█|
    // |█~~~~~█|
    // |███████|
    // |.......|
    "Partially submerged symmetrical bucket 3" {
        Ground.fromSlice(
            listOf(
                ".........",
                "█.......█",
                ".........",
                "...█.█...",
                ".█.█.█.█.",
                ".█.███.█.",
                ".█.....█.",
                ".█.....█.",
                ".█.....█.",
                ".███████.",
                "........."
            )
        ).computeWetAreas(spring = GridPoint2d(x = 4, y = 0)) shouldBe WetAreas(settled = 21, running = 24)
    }

})
