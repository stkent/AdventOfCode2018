import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder

fun main() {
    val input = resourceFile("input.txt").readLines()

    val steps = input.map {
        val regex = "Step (\\w) must be finished before step (\\w) can begin.".toRegex()
        val (step1, step2) = regex.matchEntire(it)!!.destructured
        return@map step1.first() to step2.first()
    }

    val graph = GraphBuilder.directed().build<Char>()
    steps.forEach { (step1, step2) -> graph.putEdge(step1, step2) }

    part1(graph)
    part2(graph, 5)
}

fun part1(graph: Graph<Char>) {
    // part 1
    val doneSteps = mutableListOf<Char>()
    val queuedSteps = mutableSetOf(graph.nodes().first { step -> graph.predecessors(step).isEmpty() })

    while (queuedSteps.isNotEmpty()) {
        val nextStep = queuedSteps.sorted().first()
        queuedSteps.remove(nextStep)

        doneSteps.add(nextStep)

        val dependentSteps = graph.successors(nextStep)
        val readySteps = dependentSteps.filter { step -> doneSteps.containsAll(graph.predecessors(step)) }
        queuedSteps.addAll(readySteps)
    }

    println(doneSteps.joinToString(separator = ""))
}

fun part2(graph: Graph<Char>, workerCount: Int) {
    val doneSteps = mutableListOf<Char>()
    val inProgressSteps = mutableMapOf<Char, Int>()
    val queuedSteps = mutableSetOf(graph.nodes().first { step -> graph.predecessors(step).isEmpty() })

    var second = -1

    while (inProgressSteps.isNotEmpty() || queuedSteps.isNotEmpty()) {
        second++

        inProgressSteps.forEach { (step, remainingTime) -> inProgressSteps[step] = remainingTime - 1 }

        val inProgressStepsIterator = inProgressSteps.iterator()

        inProgressStepsIterator.forEach { (inProgressStep, remainingTime) ->
            if (remainingTime == 0) {
                val doneStep = inProgressStep
                doneSteps.add(doneStep)
                inProgressStepsIterator.remove()

                val dependentSteps = graph.successors(doneStep)
                val readySteps = dependentSteps.filter { step -> doneSteps.containsAll(graph.predecessors(step)) }
                queuedSteps.addAll(readySteps)
            }
        }

        val availableWorkers = workerCount - inProgressSteps.size

        if (availableWorkers == 0) continue

        val nextSteps = queuedSteps.sorted()

        for (worker in 0 until availableWorkers) {
            val nextStep = nextSteps.getOrNull(worker) ?: continue
            queuedSteps.remove(nextStep)

            inProgressSteps[nextStep] = (60 + nextStep.toInt() - 'A'.toInt() + 1)
        }
    }

    println(second)
}