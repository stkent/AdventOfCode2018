import extensions.extractInts

data class Sample(val registersBefore: List<Int>, val instruction: Instruction, val registersAfter: List<Int>) {

    companion object {
        fun from(rawSample: List<String>): Sample {
            return Sample(
                registersBefore = rawSample[0].extractInts(),
                instruction = Instruction.fromString(rawSample[1]),
                registersAfter = rawSample[2].extractInts()
            )
        }
    }

}
