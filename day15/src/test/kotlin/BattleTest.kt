import Warrior.Elf
import Warrior.Goblin
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class BattleTest : StringSpec({

    // ███████
    // █.G.E.█
    // █E.G.E█
    // █.GGE.█
    // ███████
    //
    "Warrior finds correct foes" {
        val inputWarrior = Goblin(id = 0, location = GridPoint2d(x = 2, y = 1))

        val inputCandidates = listOf(
                Elf(id = 1, location = GridPoint2d(x = 4, y = 1)),
                Elf(id = 2, location = GridPoint2d(x = 1, y = 2)),
                Goblin(id = 3, location = GridPoint2d(x = 3, y = 2)),
                Elf(id = 4, location = GridPoint2d(x = 5, y = 2)),
                Goblin(id = 5, location = GridPoint2d(x = 2, y = 3)),
                Goblin(id = 6, location = GridPoint2d(x = 3, y = 3)),
                Elf(id = 7, location = GridPoint2d(x = 4, y = 3))
        )

        val expectedIds = listOf(1, 2, 4, 7)

        Battle.findFoes(
                warrior = inputWarrior,
                candidates = inputCandidates
        ).map(Warrior::id) shouldBe expectedIds
    }

    // ███████
    // █G....█
    // █..E..█
    // █.....█
    // ███████
    //
    "Isolated foe is attackable from all directions" {
        val inputFoeLocations = listOf(GridPoint2d(x = 3, y = 2))
        val inputLiveWarriorLocations = inputFoeLocations + GridPoint2d(x = 1, y = 1)
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.findAttackLocations(
                foeLocations = inputFoeLocations,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        ) shouldBe listOf(
                GridPoint2d(x = 3, y = 1),
                GridPoint2d(x = 2, y = 2),
                GridPoint2d(x = 4, y = 2),
                GridPoint2d(x = 3, y = 3)
        )
    }

    // ███████
    // █G....█
    // █.....█
    // █..E..█
    // ███████
    //
    "Foe against wall is not attackable from wall" {
        val inputFoeLocations = listOf(GridPoint2d(x = 3, y = 3))
        val inputLiveWarriorLocations = inputFoeLocations + GridPoint2d(x = 1, y = 1)
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.findAttackLocations(
                foeLocations = inputFoeLocations,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        ) shouldBe listOf(
                GridPoint2d(x = 3, y = 2),
                GridPoint2d(x = 2, y = 3),
                GridPoint2d(x = 4, y = 3)
        )
    }

    // ███████
    // █G....█
    // █..EG.█
    // █.....█
    // ███████
    //
    "Foe next to another warrior is not attackable from the location occupied by that warrior" {
        val inputFoeLocations = listOf(GridPoint2d(x = 3, y = 2))
        val inputLiveWarriorLocations = inputFoeLocations + GridPoint2d(x = 1, y = 1) + GridPoint2d(x = 4, y = 2)
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.findAttackLocations(
                foeLocations = inputFoeLocations,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        ) shouldBe listOf(
                GridPoint2d(x = 3, y = 1),
                GridPoint2d(x = 2, y = 2),
                GridPoint2d(x = 3, y = 3)
        )
    }

    // ███████
    // █G....█
    // █..E..█
    // █...E.█
    // ███████
    //
    "Multiple foe attack locations are all reported exactly once" {
        val inputFoeLocations = listOf(GridPoint2d(x = 3, y = 2), GridPoint2d(x = 4, y = 3))
        val inputLiveWarriorLocations = inputFoeLocations + GridPoint2d(x = 1, y = 1)
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.findAttackLocations(
                foeLocations = inputFoeLocations,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        ) shouldBe listOf(
                GridPoint2d(x = 3, y = 1),
                GridPoint2d(x = 2, y = 2),
                GridPoint2d(x = 4, y = 2),
                GridPoint2d(x = 3, y = 3),
                GridPoint2d(x = 5, y = 3)
        )
    }

    // ███████
    // █..G..█
    // █..EG.█
    // █G..G.█
    // ███████
    //
    "Correct attackable foe is identified by HP" {
        val inputWarriorLocation = GridPoint2d(x = 3, y = 2)

        val inputFoes = listOf(
                Goblin(id = 0, location = GridPoint2d(x = 3, y = 1), hp = 100),
                Goblin(id = 1, location = GridPoint2d(x = 4, y = 2), hp = 50),
                Goblin(id = 2, location = GridPoint2d(x = 1, y = 3)),
                Goblin(id = 3, location = GridPoint2d(x = 4, y = 3))
        )

        Battle.findFoeToAttack(
                warriorLocation = inputWarriorLocation,
                foes = inputFoes
        )?.id shouldBe 1
    }

    // ███████
    // █..G..█
    // █..EG.█
    // █G..G.█
    // ███████
    //
    "Correct attackable foe is identified by location ordering when HP are tied" {
        val inputWarriorLocation = GridPoint2d(x = 3, y = 2)

        val inputFoes = listOf(
                Goblin(id = 0, location = GridPoint2d(x = 3, y = 1), hp = 50),
                Goblin(id = 1, location = GridPoint2d(x = 4, y = 2), hp = 50),
                Goblin(id = 2, location = GridPoint2d(x = 1, y = 3)),
                Goblin(id = 3, location = GridPoint2d(x = 4, y = 3))
        )

        Battle.findFoeToAttack(
                warriorLocation = inputWarriorLocation,
                foes = inputFoes
        )?.id shouldBe 0
    }

    // ███████
    // █..G..█
    // █.E.G.█
    // █G..G.█
    // ███████
    //
    "No attackable foe is identified if none are available" {
        val inputWarriorLocation = GridPoint2d(x = 2, y = 2)

        val inputFoes = listOf(
                Goblin(id = 0, location = GridPoint2d(x = 3, y = 1)),
                Goblin(id = 1, location = GridPoint2d(x = 4, y = 2)),
                Goblin(id = 2, location = GridPoint2d(x = 1, y = 3)),
                Goblin(id = 3, location = GridPoint2d(x = 4, y = 3))
        )

        Battle.findFoeToAttack(
                warriorLocation = inputWarriorLocation,
                foes = inputFoes
        ) shouldBe null
    }

    // ███████
    // █E..+G█
    // █.....█
    // █.....█
    // ███████
    //
    "Unique shortest distance to given adjacent square is identified" {
        val inputStart = GridPoint2d(x = 1, y = 1)
        val inputEnd = GridPoint2d(x = 4, y = 1)
        val inputLiveWarriorLocations = listOf(GridPoint2d(x = 1, y = 1), GridPoint2d(x = 5, y = 1))
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.shortestPathBetween(
                start = inputStart,
                end = inputEnd,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        )!!.distance shouldBe 3
    }

    // ███████
    // █E..+G█
    // █.....█
    // █.....█
    // ███████
    //
    "First step with unique shortest distance to given adjacent square is identified" {
        val inputStart = GridPoint2d(x = 1, y = 1)
        val inputEnd = GridPoint2d(x = 4, y = 1)
        val inputLiveWarriorLocations = listOf(GridPoint2d(x = 1, y = 1), GridPoint2d(x = 5, y = 1))
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.shortestPathBetweenWarriorAndTarget(
                warriorLocation = inputStart,
                targetLocation = inputEnd,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        )!!.start shouldBe GridPoint2d(x = 2, y = 1)
    }

    // ███████
    // █E....█
    // █....+█
    // █....G█
    // ███████
    //
    "Non-unique shortest distance to given adjacent square is identified" {
        val inputStart = GridPoint2d(x = 1, y = 1)
        val inputEnd = GridPoint2d(x = 5, y = 2)
        val inputLiveWarriorLocations = listOf(GridPoint2d(x = 1, y = 1), GridPoint2d(x = 5, y = 3))
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.shortestPathBetween(
                start = inputStart,
                end = inputEnd,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        )!!.distance shouldBe 5
    }

    // ███████
    // █E....█
    // █....+█
    // █....G█
    // ███████
    //
    "First step of non-unique shortest distance to given adjacent square is identified" {
        val inputStart = GridPoint2d(x = 1, y = 1)
        val inputEnd = GridPoint2d(x = 5, y = 2)
        val inputLiveWarriorLocations = listOf(GridPoint2d(x = 1, y = 1), GridPoint2d(x = 5, y = 3))
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.shortestPathBetweenWarriorAndTarget(
                warriorLocation = inputStart,
                targetLocation = inputEnd,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        )!!.start shouldBe GridPoint2d(x = 2, y = 1)
    }

    // ███████
    // █E..GG█
    // █....G█
    // █.....█
    // ███████
    //
    "No shortest path identified if none are available" {
        val inputStart = GridPoint2d(x = 1, y = 1)
        val inputEnd = GridPoint2d(x = 4, y = 1)
        val inputLiveWarriorLocations = listOf(
                GridPoint2d(x = 1, y = 1),
                GridPoint2d(x = 4, y = 1),
                GridPoint2d(x = 5, y = 1),
                GridPoint2d(x = 5, y = 2))
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.shortestPathBetween(
                start = inputStart,
                end = inputEnd,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        ) shouldBe null
    }

    // ███████
    // █E...G█
    // █.....█
    // █.....█
    // ███████
    //
    "Closest location identified when shortest distance is unique" {
        val inputWarrior = Elf(id = 0, location = GridPoint2d(x = 1, y = 1))
        val inputLocations = listOf(GridPoint2d(x = 4, y = 1), GridPoint2d(x = 5, y = 2))
        val inputLiveWarriorLocations = listOf(GridPoint2d(x = 1, y = 1), GridPoint2d(x = 5, y = 1))
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.closestLocationResult(
                warrior = inputWarrior,
                locations = inputLocations,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        )!!.closestLocation shouldBe GridPoint2d(x = 4, y = 1)
    }

    // ███████
    // █E...G█
    // █.....█
    // █.....█
    // ███████
    //
    "Closest location first step identified when shortest distance is unique" {
        val inputWarrior = Elf(id = 0, location = GridPoint2d(x = 1, y = 1))
        val inputLocations = listOf(GridPoint2d(x = 4, y = 1), GridPoint2d(x = 5, y = 2))
        val inputLiveWarriorLocations = listOf(GridPoint2d(x = 1, y = 1), GridPoint2d(x = 5, y = 1))
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.closestLocationResult(
                warrior = inputWarrior,
                locations = inputLocations,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        )!!.firstStep shouldBe GridPoint2d(x = 2, y = 1)
    }

    // ███████
    // █.....█
    // █...█G█
    // █...█E█
    // █...G.█
    // ███████
    //
    "Step 28 of first example battle" {
        val inputWarrior = Goblin(id = 0, location = GridPoint2d(x = 4, y = 4))
        val inputLocations = listOf(GridPoint2d(x = 5, y = 4))
        val inputLiveWarriorLocations = listOf(GridPoint2d(x = 5, y = 3), GridPoint2d(x = 5, y = 2))
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█...█.█",
                "█...█.█",
                "█.....█",
                "███████"
        )

        Battle.closestLocationResult(
                warrior = inputWarrior,
                locations = inputLocations,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        )!!.firstStep shouldBe GridPoint2d(x = 5, y = 4)
    }

    // ███████
    // █E....█
    // █.....█
    // █....G█
    // ███████
    //
    "Closest location identified when shortest distance is not unique" {
        val inputWarrior = Elf(id = 0, location = GridPoint2d(x = 1, y = 1))
        val inputLocations = listOf(GridPoint2d(x = 5, y = 2), GridPoint2d(x = 4, y = 3))
        val inputLiveWarriorLocations = listOf(GridPoint2d(x = 1, y = 1), GridPoint2d(x = 5, y = 3))
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.closestLocationResult(
                warrior = inputWarrior,
                locations = inputLocations,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        )!!.closestLocation shouldBe GridPoint2d(x = 5, y = 2)
    }

    // ███████
    // █E.G.G█
    // █..G..█
    // █..G.G█
    // ███████
    //
    "No closest location identified if none are available" {
        val inputWarrior = Elf(id = 0, location = GridPoint2d(x = 1, y = 1))
        val inputLocations = listOf(GridPoint2d(x = 4, y = 1), GridPoint2d(x = 5, y = 2))
        val inputLiveWarriorLocations = listOf(
                GridPoint2d(x = 1, y = 1),
                GridPoint2d(x = 4, y = 1),
                GridPoint2d(x = 3, y = 1),
                GridPoint2d(x = 3, y = 2),
                GridPoint2d(x = 3, y = 3))
        val inputCavern = listOf(
                "███████",
                "█.....█",
                "█.....█",
                "█.....█",
                "███████"
        )

        Battle.closestLocationResult(
                warrior = inputWarrior,
                locations = inputLocations,
                liveWarriorLocations = inputLiveWarriorLocations,
                cavern = inputCavern
        ) shouldBe null
    }

})
