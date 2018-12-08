fun main() {
    val input = resourceFile("input.txt").readLines().first()

    val licenseFile = LicenseFile(contents = input)

    println("Part 1 solution: ${licenseFile.metadataSum}")
    println("Part 2 solution: ${licenseFile.rootNodeValue}")
}
