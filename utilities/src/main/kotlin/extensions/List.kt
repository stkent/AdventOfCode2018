@file:Suppress("unused")

package extensions

// Rotates a List to the left.
fun <E> List<E>.rotate(steps: Int): List<E> {
    val nSteps = steps.nonNegativeRem(size)
    return takeLast(size - nSteps) + take(nSteps)
}
