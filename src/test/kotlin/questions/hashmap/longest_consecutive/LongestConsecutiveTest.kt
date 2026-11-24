package questions.hashmap.longest_consecutive

import questions.hashmap.longest_consecutive.solution.longestConsecutiveBruteforce
import questions.hashmap.longest_consecutive.solution.longestConsecutiveSorting
import questions.hashmap.longest_consecutive.solution.longestConsecutiveTwoSets
import kotlin.test.Test
import kotlin.test.assertEquals

class LongestConsecutiveTest {

    private val cases = listOf(
        intArrayOf(100, 4, 200, 1, 3, 2) to 4, // example 1
        intArrayOf(0, 3, 7, 2, 5, 8, 4, 6, 0, 1) to 9, // example 2
        intArrayOf(1, 0, 1, 2) to 3, // example 3
        intArrayOf() to 0, // empty array
        intArrayOf(5) to 1, // single element
        intArrayOf(7, 7, 7) to 1, // all equal
        intArrayOf(-1, -3, -2, 0) to 4, // negative numbers
        intArrayOf(2, -1, 1, 0, -2) to 5, // sequence crosses zero
        intArrayOf(1, 2, 3, 10, 11, 12) to 3, // two sequences of equal length
        intArrayOf(5, 4, 3, 2, 1) to 5, // sorted descending
        intArrayOf(1, 2, 2, 3) to 3, // duplicate inside a sequence must not end the streak
        intArrayOf(9, 1, 4, 7, 3, 2, 8, 5, 6) to 9, // one sequence, visited from the middle first
        intArrayOf(-1_000_000_000, 1_000_000_000) to 1, // constraint bounds, far apart
        intArrayOf(999_999_999, 1_000_000_000) to 2 // constraint bound, adjacent
    )

    private fun verify(solution: (IntArray) -> Int) {
        cases.forEach { (nums, expected) ->
            assertEquals(expected, solution(nums.copyOf()), "nums = ${nums.contentToString()}")
        }
    }

    @Test
    fun `starter solution`() {
        verify(::longestConsecutive)
    }

    @Test
    fun `bruteforce solution`() {
        verify(::longestConsecutiveBruteforce)
    }

    @Test
    fun `sorting solution`() {
        verify(::longestConsecutiveSorting)
    }

    @Test
    fun `two sets solution`() {
        verify(::longestConsecutiveTwoSets)
    }
}
