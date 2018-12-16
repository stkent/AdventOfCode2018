import Warrior.Elf
import Warrior.Goblin
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class WarriorTest : StringSpec({

    "Attacked warrior with more than 3 HP loses exactly 3 HP and remains alive" {
        val dummyLocation = GridPoint2d(x = 0, y = 0)
        val attackingWarrior = Goblin(id = 0, location = dummyLocation)

        val initialHp = 5
        val attackedWarrior = Elf(id = 1, hp = initialHp, location = dummyLocation)

        attackingWarrior.attack(attackedWarrior)

        attackedWarrior.hp shouldBe initialHp - 3
        attackedWarrior.isAlive shouldBe true
    }

    "Attacked warrior with less than 3 HP dies" {
        val dummyLocation = GridPoint2d(x = 0, y = 0)
        val attackingWarrior = Goblin(id = 0, location = dummyLocation)

        val initialHp = 2
        val attackedWarrior = Elf(id = 1, hp = initialHp, location = dummyLocation)

        attackingWarrior.attack(attackedWarrior)

        attackedWarrior.isAlive shouldBe false
    }

})
