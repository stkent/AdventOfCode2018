class Device(private val ipRegister: Int) {

    data class Result(val earliest: Int, val latest: Int)

    fun computeHaltingR0s(rawProgram: List<String>, r0Value: Int): Result {
        val program = rawProgram.map(Instruction.Companion::fromString)
        val registers = mutableListOf(r0Value, 0, 0, 0, 0, 0)
        var ip = 0

        val seen = sortedSetOf<Int>().toMutableSet()

        while (ip < program.size) {
            registers[ipRegister] = ip
            program[ip].applyTo(registers)
            ip = registers[ipRegister] + 1

            if (ip == 28) {
                if (!seen.add(registers[4])) {
                    return Result(
                        earliest = seen.first(),
                        latest = seen.last()
                    )
                }
            }
        }

        throw IllegalStateException("Function should terminate within loop.")
    }

}
