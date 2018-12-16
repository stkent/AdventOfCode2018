import extensions.extractInts

fun main() {
    val input = resourceFile("input_samples.txt").readLines().chunked(4)

    val samples = input.map { Sample.from(it) }.also { println(it.joinToString("\n")) }

    // part 1
//    samples.count { sample ->
//        OpCode
//            .values()
//            .count { it.app(sample.beforeRegisters, sample.instruction.a, sample.instruction.b, sample.instruction.c) == sample.afterRegisters } >= 3
//    }.also { println(it) }

    // part 2
    samples.map { sample ->
        val num = sample.instruction.opCodeInt
        val opCodes = OpCode
            .values()
            .filter { it.app(sample.beforeRegisters, sample.instruction.a, sample.instruction.b, sample.instruction.c) == sample.afterRegisters }
        num to opCodes
    }.groupBy { it.first }
        .mapValues { it.value.map { it.second } }
        .mapValues { it.value.reduce { a, b -> a.intersect(b).toList() } }
        .map { it.key to it.value }
        .sortedBy { it.second.size }
        .also { println(it.joinToString("\n")) }

//
//    val input = listOf(
//        "Before: [3, 2, 1, 1]",
//        "9 2 1 2",
//        "After:  [3, 2, 2, 1]"
//    )
//
//    val sample = Sample.from(input)
//
//    println(sample)

//    OpCode
//        .values()
//        .filter { it.app(sample.beforeRegisters, sample.instruction.a, sample.instruction.b, sample.instruction.c) == sample.afterRegisters }
//        .forEach { println(it) }
//        .map { it to it.app(sample.beforeRegisters, sample.instruction.a, sample.instruction.b, sample.instruction.c) }
//        .forEach { println(it) }

    val inprog = resourceFile("input_program.txt").readLines()
    var regs = listOf(0, 0, 0, 0)

    for (rawins in inprog) {
        val ints = rawins.extractInts()
        val opCode = when (ints[0]) {
            0 -> OpCode.BORI
                1 -> OpCode.BORR
            2 -> OpCode.SETI
                3 -> OpCode.MULR
            4 -> OpCode.SETR
                5 -> OpCode.ADDR
            6 -> OpCode.GTIR
                7 -> OpCode.EQIR
            8 -> OpCode.GTRI
                9 -> OpCode.BANI
            10 -> OpCode.MULI
                11 -> OpCode.GTRR
            12 -> OpCode.BANR
                13 -> OpCode.EQRI
            14 -> OpCode.ADDI
                else -> OpCode.EQRR

        }
        regs = opCode.app(regs, ints[1], ints[2], ints[3])!!
    }

    println(regs[0])
}

enum class OpCode {
    ADDR, ADDI,
    MULR, MULI,
    BANR, BANI,
    BORR, BORI,
    SETR, SETI,
    GTIR, GTRI, GTRR,
    EQIR, EQRI, EQRR;

    // [3, 2, 1, 2], a=2, b=1, c=2
    fun app(registers: List<Int>, a: Int, b: Int, c: Int): List<Int>? {
        val result = registers.toMutableList()

        when (this) {
            ADDR -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = registers.getOrNull(b) ?: return null

                result[c] = s1 + s2
            }

            ADDI -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = b

                result[c] = s1 + s2
            }

            MULR -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = registers.getOrNull(b) ?: return null

                result[c] = s1 * s2
            }

            MULI -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = b

                result[c] = s1 * s2
            }

            BANR -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = registers.getOrNull(b) ?: return null

                result[c] = s1 and s2
            }

            BANI -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = b

                result[c] = s1 and s2
            }

            BORR -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = registers.getOrNull(b) ?: return null

                result[c] = s1 or s2
            }

            BORI -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = b

                result[c] = s1 or s2
            }

            SETR -> {
                val s1 = registers.getOrNull(a) ?: return null

                result[c] = s1
            }

            SETI -> {
                val s1 = a

                result[c] = s1
            }

            GTIR -> {
                val s1 = a
                val s2 = registers.getOrNull(b) ?: return null

                result[c] = if (s1 > s2) 1 else 0
            }

            GTRI -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = b

                result[c] = if (s1 > s2) 1 else 0
            }

            GTRR -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = registers.getOrNull(b) ?: return null

                result[c] = if (s1 > s2) 1 else 0
            }

            EQIR -> {
                val s1 = a
                val s2 = registers.getOrNull(b) ?: return null

                result[c] = if (s1 == s2) 1 else 0
            }

            EQRI -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = b

                result[c] = if (s1 == s2) 1 else 0
            }

            EQRR -> {
                val s1 = registers.getOrNull(a) ?: return null
                val s2 = registers.getOrNull(b) ?: return null

                result[c] = if (s1 == s2) 1 else 0
            }
        }

        return result
    }
}

data class Sample(val beforeRegisters: List<Int>, val instruction: Instruction, val afterRegisters: List<Int>) {

    companion object {
        fun from(rawSample: List<String>): Sample {
            val rawBeforeRegisters = rawSample[0].extractInts()
            val rawInstruction = rawSample[1].extractInts()
            val rawAfterRegisters = rawSample[2].extractInts()

            return Sample(
                beforeRegisters = listOf(
                    rawBeforeRegisters[0],
                    rawBeforeRegisters[1],
                    rawBeforeRegisters[2],
                    rawBeforeRegisters[3]
                ),
                instruction = Instruction(rawInstruction[0], rawInstruction[1], rawInstruction[2], rawInstruction[3]),
                afterRegisters = listOf(
                    rawAfterRegisters[0],
                    rawAfterRegisters[1],
                    rawAfterRegisters[2],
                    rawAfterRegisters[3]
                )
            )
        }
    }

}

data class Instruction(val opCodeInt: Int, val a: Int, val b: Int, val c: Int)
