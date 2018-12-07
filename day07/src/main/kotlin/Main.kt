import com.google.common.graph.GraphBuilder

fun main() {
    val input = resourceFile("input.txt").readLines()

    val input2 = listOf(
        "Step C must be finished before step A can begin.",
        "Step C must be finished before step F can begin.",
        "Step A must be finished before step B can begin.",
        "Step A must be finished before step D can begin.",
        "Step B must be finished before step E can begin.",
        "Step D must be finished before step E can begin.",
        "Step F must be finished before step E can begin."
    )

    val steps = input.map {
        val regex = "Step (.) must be finished before step (.) can begin.".toRegex()
        val (step1, step2) = regex.matchEntire(it)!!.destructured
        return@map step1 to step2
    }

    val graph = GraphBuilder.directed().build<String>()
    steps.forEach { (step1, step2) -> graph.putEdge(step1, step2) }

    // part 1
    val processedSteps = mutableListOf<String>()
    val stepsToProcess = mutableSetOf(graph.nodes().first { step -> graph.predecessors(step).isEmpty() })

    while (stepsToProcess.isNotEmpty()) {
        val nextStep = stepsToProcess.sorted().first()
        stepsToProcess.remove(nextStep)

        processedSteps.add(nextStep)

        val dependentSteps = graph.successors(nextStep)
        val c = dependentSteps.filter { step -> processedSteps.containsAll(graph.predecessors(step)) }
        stepsToProcess.addAll(c)
    }

    println(processedSteps.joinToString(separator = ""))
}
