class Device(private val ipRegister: Int) {

    fun runProgram(rawProgram: List<String>, r0Value: Int): Int {
        val program = rawProgram.map(Instruction.Companion::fromString)
        val registers = mutableListOf(r0Value, 0, 0, 0, 0, 0)
        var ip = 0

        while (ip < program.size) {
            registers[ipRegister] = ip
            program[ip].applyTo(registers)
            ip = registers[ipRegister] + 1
        }

        return registers[0]
    }

}
