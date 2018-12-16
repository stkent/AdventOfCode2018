import extensions.uniquePairings

class Device(rawSamples: List<String>) {

    private val samples: List<Sample> by lazy {
        return@lazy rawSamples
            .chunked(4)
            .map(Sample.Companion::from)
    }

    private val opCodeMap: Map<Int, OpCode> by lazy {
        val allPairs = mutableMapOf<Int, List<OpCode>>()

        samples.forEach { sample ->
            val opCodeInt = sample.instruction.opCodeInt
            allPairs[opCodeInt] = allPairs
                .getOrPut(opCodeInt) { OpCode.values().toList() }
                .intersect(OpCode.opCodesConsistentWith(sample))
                .toList()
        }

        return@lazy allPairs.uniquePairings()!!
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
