fun main() {
    val input = resourceFile("input.txt").readLines()

    val claimsProcessor = ClaimsProcessor()

    println("Part 1 solution: ${claimsProcessor.computeOverlappedSquareCount(claimStrings = input)}")
    println("Part 2 solution: ${claimsProcessor.computeIsolatedClaimId(claimStrings = input)}")
}
