package questions.string.close_strings.solution

private val examples = listOf(
    ("abc" to "bca") to true,
    ("a" to "aa") to false,
    ("cabbba" to "abbccc") to true
)

private val approaches = listOf<Pair<String, (String, String) -> Boolean>>(
    "HashMap" to ::closeStringsHashMap,
    "FrequencyArray" to ::closeStringsFrequencyArray,
    "BitMask" to ::closeStringsBitMask
)

fun main() {
    examples.forEach { (words, expected) ->
        val (word1, word2) = words
        println("Input:          word1 = \"$word1\", word2 = \"$word2\"")
        println("Expected:       $expected")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                solution(word1, word2).toString()
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(16)}$result")
        }

        println()
    }
}
