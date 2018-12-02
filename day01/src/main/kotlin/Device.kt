import extensions.accumulate
import extensions.firstRepeat
import extensions.repeatIndefinitely

class Device {

    fun netFrequencyChange(frequencyChangeStrings: Iterable<String>): Int {
        return frequencyChangeStrings.sumBy(String::toInt)
    }

    fun firstRepeatedFrequency(frequencyChangeStrings: Iterable<String>): Int? {
        return frequencyChangeStrings
            .map(String::toInt)
            .repeatIndefinitely()
            .accumulate(initial = 0, operation = Int::plus)
            .firstRepeat()
    }

}
