import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PotTunnelTest : StringSpec({

    "Message" {
        val inputPotState = "#..#.#..##......###...###"
        val inputSpreadPatterns = listOf(
            "...##",
            "..#..",
            ".#...",
            ".#.#.",
            ".#.##",
            ".##..",
            ".####",
            "#.#.#",
            "#.###",
            "##.#.",
            "##.##",
            "###..",
            "###.#",
            "####."
        )

        val inputGenerations = 20

        val expectedTunnelState = TunnelState(pots = "#....##....#####...#######....#.#..##", firstPotIndex = -2)

        val tunnel = PotTunnel(
            initialPotState = inputPotState,
            spreadPatterns = inputSpreadPatterns
        )

        tunnel.advanceByGenerations(inputGenerations)
        tunnel.state shouldBe expectedTunnelState
    }

})
