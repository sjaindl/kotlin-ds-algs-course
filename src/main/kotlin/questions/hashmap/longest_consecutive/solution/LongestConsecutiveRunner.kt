package questions.hashmap.longest_consecutive.solution

private val examples = listOf(
    intArrayOf(100, 4, 200, 1, 3, 2) to 4,
    intArrayOf(0, 3, 7, 2, 5, 8, 4, 6, 0, 1) to 9,
    intArrayOf(1, 0, 1, 2) to 3
)

private val approaches = listOf<Pair<String, (IntArray) -> Int>>(
    "Bruteforce" to ::longestConsecutiveBruteforce,
    "Sorting" to ::longestConsecutiveSorting,
    "TwoSets" to ::longestConsecutiveTwoSets
)

fun main() {
    examples.forEach { (nums, expected) ->
        println("Input:      ${nums.contentToString()}")
        println("Expected:   $expected")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                solution(nums.copyOf()).toString()
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(length = 12)}$result")
        }

        println()
    }
}
