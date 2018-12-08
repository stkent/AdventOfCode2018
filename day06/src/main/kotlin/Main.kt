fun main() {
    val input = resourceFile("input.txt").readLines()

    val landingAreaCalculator = LandingAreaCalculator(rawTargetPoints = input)

    println("Part 1 solution: ${landingAreaCalculator.safeArea1}")
    println("Part 2 solution: ${landingAreaCalculator.safeArea2(distanceThreshold = 10000)}")
}
