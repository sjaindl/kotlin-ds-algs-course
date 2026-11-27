package questions.hashmap.naming_a_company.solution

private val examples = listOf(
    arrayOf("coffee", "donuts", "time", "toffee") to 6L,
    arrayOf("lack", "back") to 0L
)

private val approaches = listOf<Pair<String, (Array<String>) -> Long>>(
    "Bruteforce" to ::distinctNamesBruteforce,
    "SuffixGroups" to ::distinctNamesSuffixGroups
)

fun main() {
    examples.forEach { (ideas, expected) ->
        println("Input:         ${ideas.joinToString(", ", "[", "]") { "\"$it\"" }}")
        println("Expected:      $expected")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                solution(ideas).toString()
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(length = 15)}$result")
        }

        println()
    }
}
