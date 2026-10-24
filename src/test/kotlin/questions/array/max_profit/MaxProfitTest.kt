package questions.array.max_profit

import questions.array.max_profit.solution.maxProfitBruteforce
import questions.array.max_profit.solution.maxProfitGreedy
import questions.array.max_profit.solution.maxProfitPeakValley
import kotlin.test.Test
import kotlin.test.assertEquals

class MaxProfitTest {

    private val cases = listOf(
        intArrayOf(7, 1, 5, 3, 6, 4) to 7, // example 1
        intArrayOf(1, 2, 3, 4, 5) to 4, // example 2
        intArrayOf(7, 6, 4, 3, 1) to 0, // example 3
        intArrayOf(5) to 0, // single day
        intArrayOf(3, 3, 3, 3) to 0, // all prices equal
        intArrayOf(1, 5, 1, 5, 1, 5) to 12, // zig-zag
        intArrayOf(0, 10000) to 10000 // constraint bounds
    )

    private fun verify(solution: (IntArray) -> Int) {
        cases.forEach { (prices, expected) ->
            assertEquals(expected, solution(prices), "prices = ${prices.contentToString()}")
        }
    }

    @Test
    fun `starter solution`() {
        verify(::maxProfit)
    }

    @Test
    fun `bruteforce solution`() {
        verify(::maxProfitBruteforce)
    }

    @Test
    fun `peak valley solution`() {
        verify(::maxProfitPeakValley)
    }

    @Test
    fun `greedy solution`() {
        verify(::maxProfitGreedy)
    }
}
