import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class SleighKitTest : StringSpec({

    "Step order" {
        val input = listOf(
            "Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin."
        )

        val expected = "CABDFE"

        SleighKit(instructions = input).stepOrder shouldBe expected
    }

    "Isolated claim id" {
        val input = listOf(
            "Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin."
        )

        val expected = 15

        SleighKit(instructions = input).timeToComplete(workerCount = 2, stepTime = { it - 'A' + 1 }) shouldBe expected
    }

})
