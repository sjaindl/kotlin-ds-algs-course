package questions.array.first_missing_positive

import questions.array.first_missing_positive.solution.firstMissingPositiveBruteforce
import questions.array.first_missing_positive.solution.firstMissingPositiveExtraSpace
import questions.array.first_missing_positive.solution.firstMissingPositiveSignMarking
import kotlin.test.Test
import kotlin.test.assertEquals

class FirstMissingPositiveTest {

    private val cases = listOf(
        intArrayOf(1, 2, 0) to 3, // example 1
        intArrayOf(3, 4, -1, 1) to 2, // example 2
        intArrayOf(7, 8, 9, 11, 12) to 1, // example 3
        intArrayOf(1) to 2, // single element: 1
        intArrayOf(2) to 1, // single element: not 1
        intArrayOf(-1) to 1, // single element: negative
        intArrayOf(0) to 1, // single element: zero
        intArrayOf(1, 1, 1) to 2, // all equal: 1
        intArrayOf(2, 2, 2) to 1, // all equal: not 1
        intArrayOf(3, 1, 2) to 4, // all of 1..n present: answer n + 1
        intArrayOf(2, 1) to 3, // value n is marked at index 0
        intArrayOf(1, 2, 4) to 3, // answer n
        intArrayOf(5, 6, 7) to 1, // no 1, values > n would be replaced with 1
        intArrayOf(1, 1, 2, 2) to 3, // duplicates
        intArrayOf(Int.MAX_VALUE, Int.MIN_VALUE, 1) to 2 // constraint bounds
    )

    private fun verify(solution: (IntArray) -> Int) {
        cases.forEach { (nums, expected) ->
            assertEquals(expected, solution(nums.copyOf()), "nums = ${nums.contentToString()}")
        }
    }

    @Test
    fun `starter solution`() {
        verify(::firstMissingPositive)
    }

    @Test
    fun `bruteforce solution`() {
        verify(::firstMissingPositiveBruteforce)
    }

    @Test
    fun `extra space solution`() {
        verify(::firstMissingPositiveExtraSpace)
    }

    @Test
    fun `sign marking solution`() {
        verify(::firstMissingPositiveSignMarking)
    }
}
