fun main() {
    val inputPots = "##....#.#.#...#.#..#.#####.#.#.##.#.#.#######...#.##....#..##....#.#..##.####.#..........#..#...#"
    val inputSpreadPatterns = resourceFile("cleaned_input.txt").readLines()

    val tunnel = PotTunnel(initialPotState = inputPots, spreadPatterns = inputSpreadPatterns)

    tunnel.advanceByGenerations(20)
    println("Part 1 solution: ${tunnel.state.plantIndexSum}")

    // Closed-form solution for part 2 was based on inspection of sum as a function of generation; see part2.png.

    tunnel.advanceByGenerations(279)
    val sumGeneration299 = tunnel.state.plantIndexSum
    tunnel.advanceByGenerations(1)
    val sumGeneration300 = tunnel.state.plantIndexSum

    println("Part 2 solution: ${sumGeneration299 + (50000000000 - 299) * (sumGeneration300 - sumGeneration299)}")
}
