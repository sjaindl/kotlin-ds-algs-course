package questions.array.next_greater_element.solution

private val examples = listOf(
    intArrayOf(1, 2, 1) to intArrayOf(2, -1, 2),
    intArrayOf(1, 2, 3, 4, 3) to intArrayOf(2, 3, 4, -1, 4)
)

private val approaches = listOf<Pair<String, (IntArray) -> IntArray>>(
    "Bruteforce" to ::nextGreaterElementsBruteforce,
    "DoubleArray" to ::nextGreaterElementsDoubleArray,
    "Stack" to ::nextGreaterElementsMonotonicStack
)

fun main() {
    examples.forEach { (nums, expected) ->
        println("Input:       ${nums.contentToString()}")
        println("Expected:    ${expected.contentToString()}")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                solution(nums).contentToString()
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(13)}$result")
        }

        println()
    }
}
