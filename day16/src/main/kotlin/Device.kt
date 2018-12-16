class Device(rawSamples: List<String>) {

    private val samples: List<Sample> by lazy {
        return@lazy rawSamples
            .chunked(4)
            .map(Sample.Companion::from)
    }

    private val opCodeMap: Map<Int, OpCode> by lazy {
        val result = mutableMapOf<Int, OpCode>()
        val possiblePairings = mutableMapOf<Int, MutableList<OpCode>>()

        samples.forEach { sample ->
            val opCodeInt = sample.instruction.opCodeInt

            possiblePairings[opCodeInt] = possiblePairings
                .getOrPut(opCodeInt) { OpCode.values().toMutableList() }
                .intersect(OpCode.opCodesConsistentWith(sample))
                .toMutableList()
        }

        while (possiblePairings.isNotEmpty()) {
            val (opCodeInt, opCode) = possiblePairings
                .filter { (_, opCodes) -> opCodes.count() == 1 }
                .mapValues { (_, opCodes) -> opCodes.single() }
                .entries
                .first()

            result += opCodeInt to opCode
            possiblePairings.remove(opCodeInt)
            possiblePairings.values.forEach { it.remove(opCode) }
        }

        return@lazy result
    }

    fun mostAmbiguousSamples(): Int {
        return samples.count { sample ->
            OpCode.opCodesConsistentWith(sample).count() >= 3
        }
    }

    fun runProgram(rawProgram: List<String>): Int {
        val program = rawProgram.map(Instruction.Companion::fromString)

        var registers = listOf(0, 0, 0, 0)

        for (instruction in program) {
            registers = opCodeMap[instruction.opCodeInt]!!.applyTo(
                registers,
                instruction.a,
                instruction.b,
                instruction.c
            )
        }

        return registers[0]
    }

}
