package questions.string.unique_letter_string.solution

private val examples = listOf(
    "ABC" to 10,
    "ABA" to 8,
    "LEETCODE" to 92
)

private val approaches = listOf<Pair<String, (String) -> Int>>(
    "Bruteforce" to ::uniqueLetterStringBruteforce,
    "NextOccurrences" to ::uniqueLetterStringNextOccurrences,
    "CharSplit" to ::uniqueLetterStringCharSplit
)

fun main() {
    examples.forEach { (s, expected) ->
        println("Input:           \"$s\"")
        println("Expected:        $expected")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                solution(s).toString()
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(17)}$result")
        }

        println()
    }
}
