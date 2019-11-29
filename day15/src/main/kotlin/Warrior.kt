sealed class Warrior(open var id: Int, open var hp: Int, open var location: GridPoint2d, open var attackPower: Int = 3) {

    companion object {
        fun from(char: Char, id: Int, location: GridPoint2d): Warrior? {
            return when (char) {
                 'E' -> Elf(id = id, hp = 200, location = location)
                 'G' -> Goblin(id = id, hp = 200, location = location)
                else -> null
            }
        }
    }

    class Elf(
        override var id: Int,
        override var hp: Int,
        override var location: GridPoint2d
    ) : Warrior(id, hp, location) {

        override fun toString(): String {
            return "E"
        }
    }

    class Goblin(
        override var id: Int,
        override var hp: Int,
        override var location: GridPoint2d
    ) : Warrior(id, hp, location) {

        override fun toString(): String {
            return "G"
        }
    }

    val isAlive: Boolean
        get() = this.hp > 0

    override fun equals(other: Any?): Boolean {
        if (other !is Warrior) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }

    fun attack(foe: Warrior) {
        foe.hp -= attackPower
    }

    fun isFoeOf(other: Warrior): Boolean {
        return when (this) {
            is Elf -> other is Goblin
            is Goblin -> other is Elf
        }
    }

}
