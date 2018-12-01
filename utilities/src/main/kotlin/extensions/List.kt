@file:Suppress("unused")

package extensions

// Rotates a List to the left.
fun <T> List<T>.rotate(steps: Int): List<T> {
    val nSteps = steps.nonNegativeRem(size)
    return takeLast(size - nSteps) + take(nSteps)
}
