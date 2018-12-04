import extensions.elementCounts

class ClaimsProcessor {

    fun computeOverlappedSquareCount(claimStrings: List<String>): Int {
        return claimStrings
            .map(Claim.Companion::fromString)
            .flatMap { claim -> claim.squares.toList() }
            .elementCounts()
            .count { it.value > 1 }
    }

    fun computeIsolatedClaimId(claimStrings: List<String>): Int {
        val claims = claimStrings.map(Claim.Companion::fromString)
        val isolatedClaimIds = claims.map(Claim::id).toMutableSet()

        val coverageMap = mutableMapOf<GridPoint2d, Set<Int>>()

        claims.forEach { claim ->
            claim.squares.forEach { gridPoint ->
                val coveringClaimIds = coverageMap[gridPoint].orEmpty()

                if (coveringClaimIds.isNotEmpty()) {
                    isolatedClaimIds.remove(claim.id)
                    isolatedClaimIds.removeAll(coveringClaimIds)
                }

                coverageMap[gridPoint] = coveringClaimIds + claim.id
            }
        }

        // Problem states that input will produce exactly one isolated claim.
        return isolatedClaimIds.first()
    }

}
