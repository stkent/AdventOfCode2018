import extensions.charCounts
import extensions.hammingDistanceFrom
import extensions.unorderedPairs
import extensions.valuesMatch

class BoxIdsWrapper(private val boxIds: List<String>) {

    val checksum: Int by lazy {
        val doubleCharCount = boxIds.count { 2 in it.charCounts().values }
        val tripleCharCount = boxIds.count { 3 in it.charCounts().values }
        return@lazy doubleCharCount * tripleCharCount
    }

    val prototypeBoxIdsCommonChars: String by lazy {
        val prototypeBoxIds = boxIds.unorderedPairs()
            .keys
            .first { idPair -> idPair.first.hammingDistanceFrom(idPair.second) == 1 }

        return@lazy prototypeBoxIds.first.zip(prototypeBoxIds.second)
            .filter(Pair<Char, Char>::valuesMatch)
            .map(Pair<Char, Char>::first)
            .joinToString(separator = "")
    }

}
