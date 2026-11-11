package questions.string.frequency_sort.solution

private val examples = listOf(
    "tree" to "eert",
    "cccaaa" to "aaaccc",
    "Aabb" to "bbAa"
)

private val approaches = listOf<Pair<String, (String) -> String>>(
    "Bruteforce" to ::frequencySortBruteforce,
    "HashMap" to ::frequencySortHashMap,
    "Bucket" to ::frequencySortBucket
)

// ties may come in any order, so check the rules instead of comparing with one expected string:
// same characters as s, equal characters together, counts never increasing
private fun isValid(s: String, result: String): Boolean {
    if (result.toList().sorted() != s.toList().sorted()) return false

    val blocks = mutableListOf<Pair<Char, Int>>()
    for (char in result) {
        if (blocks.isNotEmpty() && blocks.last().first == char) {
            blocks[blocks.size - 1] = char to blocks.last().second + 1
        } else {
            blocks.add(char to 1)
        }
    }

    return blocks.map { it.first }.toSet().size == blocks.size &&
        blocks.zipWithNext().all { (a, b) -> a.second >= b.second }
}

fun main() {
    examples.forEach { (s, expected) ->
        println("Input:      \"$s\"")
        println("Expected:   \"$expected\" (or any other valid order)")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                val sorted = solution(s)
                "\"$sorted\" ${if (isValid(s, sorted)) "✓" else "✗"}"
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(12)}$result")
        }

        println()
    }
}
