sealed class Warrior(open var id: Int, open var hp: Int, open var location: GridPoint2d) {

    companion object {
        fun from(char: Char, id: Int, location: GridPoint2d): Warrior? {
            return when (char) {
                //@formatter:off
                 'E' -> Elf(id = id, location = location)
                 'G' -> Goblin(id = id, location = location)
                else -> null
                //@formatter:on
            }
        }
    }

    class Elf(
        override var id: Int,
        override var hp: Int = 200,
        override var location: GridPoint2d
    ) : Warrior(id, hp, location)

    class Goblin(
        override var id: Int,
        override var hp: Int = 200,
        override var location: GridPoint2d
    ) : Warrior(id, hp, location)

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
        foe.hp -= 3
    }

    fun isFoeOf(other: Warrior): Boolean {
        return when (this) {
            is Elf -> other is Goblin
            is Goblin -> other is Elf
        }
    }

}
