data class Instruction(val opCodeInt: Int, val a: Int, val b: Int, val c: Int) {

    companion object {
        fun fromString(s: String): Instruction {
            val ints = s.split(" ").map(String::toInt)
            return Instruction(
                opCodeInt = ints[0],
                a = ints[1],
                b = ints[2],
                c = ints[3]
            )
        }
    }

}
