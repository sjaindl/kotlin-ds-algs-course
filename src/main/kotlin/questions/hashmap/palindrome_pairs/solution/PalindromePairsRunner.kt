package questions.hashmap.palindrome_pairs.solution

private val examples = listOf(
    arrayOf("abcd", "dcba", "lls", "s", "sssll") to listOf(listOf(0, 1), listOf(1, 0), listOf(3, 2), listOf(2, 4)),
    arrayOf("bat", "tab", "cat") to listOf(listOf(0, 1), listOf(1, 0)),
    arrayOf("a", "") to listOf(listOf(0, 1), listOf(1, 0))
)

private val approaches = listOf<Pair<String, (Array<String>) -> List<List<Int>>>>(
    "Bruteforce" to ::palindromePairsBruteforce,
    "HashMap" to ::palindromePairsHashMap
)

// the pairs may come in any order, so sort them to compare them
private fun format(pairs: List<List<Int>>) = pairs.sortedWith(compareBy({ it[0] }, { it[1] })).joinToString(
    separator = ", ",
    prefix = "[",
    postfix = "]"
) { pair ->
    pair.joinToString(separator = ", ", prefix = "[", postfix = "]")
}

fun main() {
    examples.forEach { (words, expected) ->
        println("Input:      ${words.joinToString(", ", "[", "]") { "\"$it\"" }}")
        println("Expected:   ${format(expected)}")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                format(solution(words))
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(length = 12)}$result")
        }

        println()
    }
}
