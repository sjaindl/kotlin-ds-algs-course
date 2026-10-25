package questions.array.next_greater_element

import questions.array.next_greater_element.solution.nextGreaterElementsBruteforce
import questions.array.next_greater_element.solution.nextGreaterElementsDoubleArray
import questions.array.next_greater_element.solution.nextGreaterElementsMonotonicStack
import kotlin.test.Test
import kotlin.test.assertContentEquals

class NextGreaterElementTest {

    private val cases = listOf(
        intArrayOf(1, 2, 1) to intArrayOf(2, -1, 2), // example 1
        intArrayOf(1, 2, 3, 4, 3) to intArrayOf(2, 3, 4, -1, 4), // example 2
        intArrayOf(5) to intArrayOf(-1), // single element
        intArrayOf(3, 3, 3) to intArrayOf(-1, -1, -1), // all equal: equal is not greater
        intArrayOf(1, 2, 3, 4) to intArrayOf(2, 3, 4, -1), // increasing
        intArrayOf(5, 4, 3, 2, 1) to intArrayOf(-1, 5, 5, 5, 5), // decreasing: all answers wrap around
        intArrayOf(3, 1, 2) to intArrayOf(-1, 2, 3), // answer found after wrapping
        intArrayOf(2, 1, 2) to intArrayOf(-1, 2, -1), // maximum appears twice
        intArrayOf(5, 4, 3, 2, 1, 6) to intArrayOf(6, 6, 6, 6, 6, -1), // one greater element at the end
        intArrayOf(-1_000_000_000, 1_000_000_000) to intArrayOf(1_000_000_000, -1) // constraint bounds
    )

    private fun verify(solution: (IntArray) -> IntArray) {
        cases.forEach { (nums, expected) ->
            assertContentEquals(expected, solution(nums.copyOf()), "nums = ${nums.contentToString()}")
        }
    }

    @Test
    fun `starter solution`() {
        verify(::nextGreaterElements)
    }

    @Test
    fun `bruteforce solution`() {
        verify(::nextGreaterElementsBruteforce)
    }

    @Test
    fun `double array solution`() {
        verify(::nextGreaterElementsDoubleArray)
    }

    @Test
    fun `monotonic stack solution`() {
        verify(::nextGreaterElementsMonotonicStack)
    }
}
