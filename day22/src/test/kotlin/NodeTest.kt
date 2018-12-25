import Tool.CLIMBING_GEAR
import Tool.TORCH
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class NodeTest : StringSpec({

    "Valid neighbor costs, corner" {
        val cave = Cave(scanDepth = 510, scanTarget = GridPoint2d(x = 10, y = 10))

        val startNode = Node(point = GridPoint2d.origin, tool = TORCH)

        val expectedValidNeighborCosts = mutableMapOf(
            Node(point = GridPoint2d.origin, tool = CLIMBING_GEAR) to 7,
            Node(point = GridPoint2d(x = 0, y = 1), tool = TORCH) to 1
        )

        startNode.validNeighborCosts(cave) shouldBe expectedValidNeighborCosts
    }

    "Valid neighbor costs, center" {
        val cave = Cave(scanDepth = 510, scanTarget = GridPoint2d(x = 10, y = 10))

        val startNode = Node(point = GridPoint2d(x = 4, y = 2), tool = CLIMBING_GEAR)

        val expectedValidNeighborCosts = mutableMapOf(
            Node(point = GridPoint2d(x = 4, y = 2), tool = TORCH) to 7,
            Node(point = GridPoint2d(x = 4, y = 1), tool = CLIMBING_GEAR) to 1,
            Node(point = GridPoint2d(x = 5, y = 2), tool = CLIMBING_GEAR) to 1,
            Node(point = GridPoint2d(x = 4, y = 3), tool = CLIMBING_GEAR) to 1
        )

        startNode.validNeighborCosts(cave) shouldBe expectedValidNeighborCosts
    }

})
