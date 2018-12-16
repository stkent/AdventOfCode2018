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




    // new tests!




    // ███████
    // █..@..█
    // █.@E@.█
    // █..+..█
    // █.....█
    // █..G..█
    // ███████
    //
    "Warrior moves directly up towards nearest foe if unobstructed" {
        val inputWarriorLocation = GridPoint2d(x = 3, y = 5)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 3, y = 1),
            GridPoint2d(x = 2, y = 2),
            GridPoint2d(x = 4, y = 2),
            GridPoint2d(x = 3, y = 3)
        )

        val inputCavern = listOf(
            "███████",
            "█.....█",
            "█.....█",
            "█.....█",
            "█.....█",
            "█.....█",
            "███████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe GridPoint2d(x = 3, y = 4)
    }

    // ███████
    // █.....█
    // █.@...█
    // █@E+.G█
    // █.@...█
    // █.....█
    // ███████
    //
    "Warrior moves directly left towards nearest foe if unobstructed" {
        val inputWarriorLocation = GridPoint2d(x = 5, y = 3)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 2, y = 2),
            GridPoint2d(x = 1, y = 3),
            GridPoint2d(x = 3, y = 3),
            GridPoint2d(x = 2, y = 4)
        )

        val inputCavern = listOf(
            "███████",
            "█.....█",
            "█.....█",
            "█.....█",
            "█.....█",
            "█.....█",
            "███████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe GridPoint2d(x = 4, y = 3)
    }

    // ███████
    // █.....█
    // █...@.█
    // █G.+E@█
    // █...@.█
    // █.....█
    // ███████
    //
    "Warrior moves directly right towards nearest foe if unobstructed" {
        val inputWarriorLocation = GridPoint2d(x = 1, y = 3)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 4, y = 2),
            GridPoint2d(x = 3, y = 3),
            GridPoint2d(x = 5, y = 3),
            GridPoint2d(x = 4, y = 4)
        )

        val inputCavern = listOf(
            "███████",
            "█.....█",
            "█.....█",
            "█.....█",
            "█.....█",
            "█.....█",
            "███████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe GridPoint2d(x = 2, y = 3)
    }

    // ███████
    // █..G..█
    // █.....█
    // █..+..█
    // █.@E@.█
    // █..@..█
    // ███████
    //
    "Warrior moves directly down towards nearest foe if unobstructed" {
        val inputWarriorLocation = GridPoint2d(x = 3, y = 1)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 3, y = 3),
            GridPoint2d(x = 2, y = 4),
            GridPoint2d(x = 4, y = 4),
            GridPoint2d(x = 3, y = 5)
        )

        val inputCavern = listOf(
            "███████",
            "█.....█",
            "█.....█",
            "█.....█",
            "█.....█",
            "█.....█",
            "███████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe GridPoint2d(x = 3, y = 2)
    }

    // ██████
    // █G█E?█
    // ██████
    //
    "Warrior does not move if obstructed by walls" {
        val inputWarriorLocation = GridPoint2d(x = 1, y = 1)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 3, y = 1)
        )

        val inputCavern = listOf(
            "██████",
            "█.█..█",
            "██████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe null
    }

    // ██████
    // █GGE?█
    // ██████
    //
    "Warrior does not move if obstructed by comrade" {
        val inputWarriorLocation = GridPoint2d(x = 1, y = 1)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 3, y = 1)
        )

        val inputCavern = listOf(
            "██████",
            "█....█",
            "██████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe null
    }

    // ███████
    // █...+.█
    // █G█!E@█
    // █...!.█
    // ███████
    //
    "Warrior prefers moving up rather than down if the routes are the same length" {
        val inputWarriorLocation = GridPoint2d(x = 1, y = 2)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 4, y = 1),
            GridPoint2d(x = 3, y = 2),
            GridPoint2d(x = 5, y = 2),
            GridPoint2d(x = 4, y = 3)
        )

        val inputCavern = listOf(
            "███████",
            "█.....█",
            "█.█...█",
            "█.....█",
            "███████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe GridPoint2d(x = 1, y = 1)
    }

    // ███████
    // █.....█
    // █.█.@.█
    // █G█+E@█
    // █...!.█
    // ███████
    //
    "Warrior prefers moving down if the route when traveling up is longer" {
        val inputWarriorLocation = GridPoint2d(x = 1, y = 3)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 4, y = 2),
            GridPoint2d(x = 3, y = 3),
            GridPoint2d(x = 5, y = 3),
            GridPoint2d(x = 4, y = 4)
        )

        val inputCavern = listOf(
            "███████",
            "█.....█",
            "█.█...█",
            "█.█...█",
            "█.....█",
            "███████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe GridPoint2d(x = 1, y = 4)
    }

    // █████
    // █.G.█
    // █.█.█
    // █.+.█
    // █!E!█
    // █.@.█
    // █████
    //
    "Warrior prefers moving left rather than right if the routes are the same length" {
        val inputWarriorLocation = GridPoint2d(x = 2, y = 1)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 2, y = 3),
            GridPoint2d(x = 1, y = 4),
            GridPoint2d(x = 3, y = 4),
            GridPoint2d(x = 2, y = 5)
        )

        val inputCavern = listOf(
            "█████",
            "█...█",
            "█.█.█",
            "█...█",
            "█...█",
            "█...█",
            "█████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe GridPoint2d(x = 1, y = 1)
    }

    // ██████
    // █..G.█
    // █.██.█
    // █..+.█
    // █.@E!█
    // █..@.█
    // ██████
    //
    "Warrior prefers moving right if the route when traveling left is longer" {
        val inputWarriorLocation = GridPoint2d(x = 3, y = 1)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 3, y = 3),
            GridPoint2d(x = 2, y = 4),
            GridPoint2d(x = 4, y = 4),
            GridPoint2d(x = 3, y = 5)
        )

        val inputCavern = listOf(
            "██████",
            "█....█",
            "█.██.█",
            "█....█",
            "█....█",
            "█....█",
            "██████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe GridPoint2d(x = 4, y = 1)
    }

    // ████████
    // █......█
    // █.█..+.█
    // █.G█!E@█
    // █.█..!.█
    // █......█
    // ████████
    //
    "Warrior moves away from target if necessary" {
        val inputWarriorLocation = GridPoint2d(x = 2, y = 3)

        val inputAttackLocations = listOf(
            GridPoint2d(x = 5, y = 2),
            GridPoint2d(x = 4, y = 3),
            GridPoint2d(x = 6, y = 3),
            GridPoint2d(x = 5, y = 4)
        )

        val inputCavern = listOf(
            "████████",
            "█......█",
            "█.█....█",
            "█..█...█",
            "█.█....█",
            "█......█",
            "████████"
        )

        Battle.findNextLocation(
            warriorLocation = inputWarriorLocation,
            attackLocations = inputAttackLocations,
            cavern = inputCavern
        ) shouldBe GridPoint2d(x = 1, y = 3)
    }

})
