import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class BattleTest : StringSpec({

    "Winning evil army live units" {
        val inputGoodGroups = listOf(
            "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
            "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3"
        )

        val inputEvilGroups = listOf(
            "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1",
            "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4"
        )

        val expectedLiveUnits = 5216

        val result = Battle(
            rawGoodGroups = inputGoodGroups,
            rawEvilGroups = inputEvilGroups
        ).execute()

        (result as Battle.Result.Win).liveUnits shouldBe expectedLiveUnits
    }

    "Winning good live units" {
        val inputGoodGroups = listOf(
            "17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
            "989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3"
        )

        val inputEvilGroups = listOf(
            "801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1",
            "4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4"
        )

        val inputBoost = 1570
        val expectedLiveUnits = 51

        val result = Battle(
            rawGoodGroups = inputGoodGroups,
            rawEvilGroups = inputEvilGroups,
            goodBoost = inputBoost
        ).execute()

        (result as Battle.Result.Win).liveUnits shouldBe expectedLiveUnits
    }

})
