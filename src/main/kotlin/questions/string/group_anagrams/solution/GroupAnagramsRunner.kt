package questions.string.group_anagrams.solution

private val examples = listOf(
    arrayOf("eat", "tea", "tan", "ate", "nat", "bat") to listOf(listOf("bat"), listOf("nat", "tan"), listOf("ate", "eat", "tea")),
    arrayOf("") to listOf(listOf("")),
    arrayOf("a") to listOf(listOf("a"))
)

private val approaches = listOf<Pair<String, (Array<String>) -> List<List<String>>>>(
    "Bruteforce" to ::groupAnagramsBruteforce,
    "Sorting" to ::groupAnagramsSorting,
    "CharCount" to ::groupAnagramsCharCount
)

// the groups may come in any order, so sort each group and then the groups to compare them
private fun normalize(groups: List<List<String>>) = groups.map {
    it.sorted()
}.sortedBy {
    it.joinToString(separator = ",")
}

private fun format(groups: List<List<String>>) = normalize(groups).joinToString(
    separator = ", ",
    prefix = "[",
    postfix = "]"
) { group ->
    group.joinToString(separator = ", ", prefix = "[", postfix = "]") { "\"$it\"" }
}

fun main() {
    examples.forEach { (strs, expected) ->
        println("Input:      ${strs.joinToString(", ", "[", "]") { "\"$it\"" }}")
        println("Expected:   ${format(expected)}")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                format(solution(strs))
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(length = 12)}$result")
        }

        println()
    }
}
