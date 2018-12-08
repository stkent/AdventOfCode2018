class LicenseFile(contents: String) {

    private val numbers = contents.split(" ").map(String::toInt)

    val metadataSum: Int by lazy {
        var result = 0
        var index = 0

        fun processNode() {
            val childCount = numbers[index]
            val metadataCount = numbers[index + 1]
            index += 2

            repeat(childCount) { processNode() }

            val metadata = numbers.slice(index until index + metadataCount)
            result += metadata.sum()
            index += metadataCount
        }

        processNode()

        return@lazy result
    }

    val rootNodeValue: Int by lazy {
        var index = 0

        fun computeNodeValue(): Int {
            val childCount = numbers[index]
            val metadataCount = numbers[index + 1]
            index += 2

            val childValues = (1..childCount).map { computeNodeValue() }
            val metadata = numbers.slice(index until index + metadataCount)
            index += metadataCount

            return if (childCount == 0) {
                metadata.sum()
            } else {
                metadata
                    .map { child -> childValues.getOrElse(child - 1) { 0 } }
                    .sum()
            }
        }

        return@lazy computeNodeValue()
    }

}
