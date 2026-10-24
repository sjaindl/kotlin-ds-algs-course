package questions.array.max_profit.solution

private val examples = listOf(
    intArrayOf(7, 1, 5, 3, 6, 4) to 7,
    intArrayOf(1, 2, 3, 4, 5) to 4,
    intArrayOf(7, 6, 4, 3, 1) to 0
)

private val approaches = listOf<Pair<String, (IntArray) -> Int>>(
    "Bruteforce" to ::maxProfitBruteforce,
    "PeakValley" to ::maxProfitPeakValley,
    "Greedy" to ::maxProfitGreedy
)

fun main() {
    examples.forEach { (prices, expected) ->
        println("Input:      ${prices.contentToString()}")
        println("Expected:   $expected")

        approaches.forEach { (name, solution) ->
            val result = runCatching {
                solution(prices).toString()
            }.getOrElse {
                if (it is NotImplementedError) "not implemented yet" else throw it
            }
            println("${"$name:".padEnd(12)}$result")
        }

        println()
    }
}
