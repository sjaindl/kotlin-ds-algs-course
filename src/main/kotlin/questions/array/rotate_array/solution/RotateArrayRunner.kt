package questions.array.rotate_array.solution

private val examples = listOf(
    Triple(intArrayOf(1, 2, 3, 4, 5, 6, 7), 3, intArrayOf(5, 6, 7, 1, 2, 3, 4)),
    Triple(intArrayOf(-1, -100, 3, 99), 2, intArrayOf(3, 99, -1, -100))
)

private val approaches = listOf<Pair<String, (IntArray, Int) -> Unit>>(
    "Bruteforce" to ::rotateBruteforce,
    "ExtraArray" to ::rotateExtraArray,
    "Cyclic" to ::rotateCyclicReplacements,
    "Reverse" to ::rotateReverse
)

fun main() {
    examples.forEach { (nums, k, expected) ->
        println("Input:      ${nums.contentToString()}, k = $k")
        println("Expected:   ${expected.contentToString()}")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                nums.copyOf().also { solution(it, k) }.contentToString()
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(12)}$result")
        }

        println()
    }
}
