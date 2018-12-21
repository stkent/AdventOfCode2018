data class Instruction(val opCode: OpCode, val a: Int, val b: Int, val c: Int) {

    companion object {
        fun fromString(s: String): Instruction {
            val (opCodeString, aString, bString, cString) =
                    """(\w+) (\d+) (\d+) (\d+)""".toRegex().matchEntire(s)!!.destructured

            return Instruction(
                opCode = OpCode.fromString(opCodeString),
                a = aString.toInt(),
                b = bString.toInt(),
                c = cString.toInt()
            )
        }
    }

    fun applyTo(registers: MutableList<Int>) {
        opCode.applyTo(registers, a, b, c)
    }

}
