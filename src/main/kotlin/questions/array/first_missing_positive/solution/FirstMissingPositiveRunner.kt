package questions.array.first_missing_positive.solution

private val examples = listOf(
    intArrayOf(1, 2, 0) to 3,
    intArrayOf(3, 4, -1, 1) to 2,
    intArrayOf(7, 8, 9, 11, 12) to 1
)

private val approaches = listOf<Pair<String, (IntArray) -> Int>>(
    "Bruteforce" to ::firstMissingPositiveBruteforce,
    "ExtraSpace" to ::firstMissingPositiveExtraSpace,
    "SignMarking" to ::firstMissingPositiveSignMarking
)

fun main() {
    examples.forEach { (nums, expected) ->
        println("Input:       ${nums.contentToString()}")
        println("Expected:    $expected")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                solution(nums.copyOf()).toString()
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(13)}$result")
        }

        println()
    }
}
