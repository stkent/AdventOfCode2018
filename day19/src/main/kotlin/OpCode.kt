import java.util.Locale.US

enum class OpCode {

    ADDR, ADDI,
    MULR, MULI,
    BANR, BANI,
    BORR, BORI,
    SETR, SETI,
    GTIR, GTRI, GTRR,
    EQIR, EQRI, EQRR;

    companion object {
        fun fromString(s: String): OpCode {
            return valueOf(s.toUpperCase(US))
        }
    }

    fun applyTo(registers: MutableList<Int>, a: Int, b: Int, c: Int) {
        registers[c] = when (this) {
            ADDR -> registers[a] + registers[b]
            ADDI -> registers[a] + b
            MULR -> registers[a] * registers[b]
            MULI -> registers[a] * b
            BANR -> registers[a] and registers[b]
            BANI -> registers[a] and b
            BORR -> registers[a] or registers[b]
            BORI -> registers[a] or b
            SETR -> registers[a]
            SETI -> a
            GTIR -> if (a > registers[b]) 1 else 0
            GTRI -> if (registers[a] > b) 1 else 0
            GTRR -> if (registers[a] > registers[b]) 1 else 0
            EQIR -> if (a == registers[b]) 1 else 0
            EQRI -> if (registers[a] == b) 1 else 0
            EQRR -> if (registers[a] == registers[b]) 1 else 0
        }
    }

}
