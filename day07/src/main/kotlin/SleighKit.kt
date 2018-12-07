import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder
import kotlin.math.min

class SleighKit(instructions: List<String>) {

    private val instructionGraph: Graph<Char> by lazy {
        val steps = instructions.map {
            val regex = "Step (\\w) must be finished before step (\\w) can begin.".toRegex()
            val (step1, step2) = regex.matchEntire(it)!!.destructured
            return@map step1.first() to step2.first()
        }

        val graph = GraphBuilder.directed().build<Char>()
        steps.forEach { (step1, step2) -> graph.putEdge(step1, step2) }
        return@lazy graph
    }

    private val firstInstruction: Char by lazy {
        instructionGraph.nodes().first { step -> instructionGraph.predecessors(step).isEmpty() }
    }

    val stepOrder: String by lazy {
        // State

        val doneSteps = mutableListOf<Char>()
        val queuedSteps = sortedSetOf(firstInstruction)

        // Algorithm components

        fun processStep(step: Char) {
            doneSteps.add(step)

            val dependentSteps = instructionGraph.successors(step)

            val readySteps = dependentSteps.filter { dependentStep ->
                val prerequisites = instructionGraph.predecessors(dependentStep)
                doneSteps.containsAll(prerequisites)
            }

            queuedSteps.addAll(readySteps)
        }

        // Algorithm

        while (queuedSteps.isNotEmpty()) {
            processStep(queuedSteps.pollFirst())
        }

        return@lazy doneSteps.joinToString(separator = "")
    }

    fun timeToComplete(workerCount: Int): Int {
        // State

        val doneSteps = mutableListOf<Char>()
        val inProgressSteps = mutableMapOf<Char, Int>()
        val queuedSteps = sortedSetOf(firstInstruction)

        // Algorithm components

        fun advanceInProgressSteps() {
            inProgressSteps.forEach { (step, remainingTime) ->
                inProgressSteps[step] = remainingTime - 1
            }
        }

        fun processNewlyDoneStep(newlyDoneStep: Char) {
            doneSteps.add(newlyDoneStep)
            val dependentSteps = instructionGraph.successors(newlyDoneStep)

            val readySteps = dependentSteps.filter { dependentStep ->
                val prerequisites = instructionGraph.predecessors(dependentStep)
                doneSteps.containsAll(prerequisites)
            }

            queuedSteps.addAll(readySteps)
        }

        fun processNewlyDoneSteps() {
            val iterator = inProgressSteps.iterator()

            iterator.forEach { (inProgressStep, remainingTime) ->
                if (remainingTime == 0) {
                    processNewlyDoneStep(inProgressStep)
                    iterator.remove()
                }
            }
        }

        fun assignQueuedStepsToFreeWorkers() {
            val freeWorkerCount = workerCount - inProgressSteps.size
            val newAssignmentCount = min(freeWorkerCount, queuedSteps.size)

            repeat(newAssignmentCount) {
                val queuedStep = queuedSteps.pollFirst()
                val queuedStepDuration = (60 + queuedStep.toInt() - 'A'.toInt() + 1)
                inProgressSteps[queuedStep] = queuedStepDuration
            }
        }

        // Algorithm

        var clockTime = -1

        while (inProgressSteps.isNotEmpty() || queuedSteps.isNotEmpty()) {
            clockTime++

            advanceInProgressSteps()
            processNewlyDoneSteps()
            assignQueuedStepsToFreeWorkers()
        }

        return clockTime
    }

}
