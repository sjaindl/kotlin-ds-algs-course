package questions.array.rotate_array

import questions.array.rotate_array.solution.rotateBruteforce
import questions.array.rotate_array.solution.rotateCyclicReplacements
import questions.array.rotate_array.solution.rotateExtraArray
import questions.array.rotate_array.solution.rotateReverse
import kotlin.test.Test
import kotlin.test.assertContentEquals

class RotateArrayTest {

    private val cases = listOf(
        Triple(intArrayOf(1, 2, 3, 4, 5, 6, 7), 3, intArrayOf(5, 6, 7, 1, 2, 3, 4)), // example 1
        Triple(intArrayOf(-1, -100, 3, 99), 2, intArrayOf(3, 99, -1, -100)), // example 2
        Triple(intArrayOf(1, 2, 3), 0, intArrayOf(1, 2, 3)), // no rotation
        Triple(intArrayOf(1, 2, 3), 3, intArrayOf(1, 2, 3)), // k == n
        Triple(intArrayOf(1, 2, 3), 10, intArrayOf(3, 1, 2)), // k > n
        Triple(intArrayOf(42), 5, intArrayOf(42)), // single element
        Triple(intArrayOf(1, 2), 1, intArrayOf(2, 1)), // two elements
        Triple(intArrayOf(1, 2, 3, 4, 5, 6), 2, intArrayOf(5, 6, 1, 2, 3, 4)), // gcd(n, k) = 2: two cycles
        Triple(intArrayOf(1, 2, 3, 4, 5, 6), 4, intArrayOf(3, 4, 5, 6, 1, 2)), // gcd(n, k) = 2: two cycles
        Triple(intArrayOf(Int.MIN_VALUE, 0, Int.MAX_VALUE), 1, intArrayOf(Int.MAX_VALUE, Int.MIN_VALUE, 0)) // bounds
    )

    private fun verify(solution: (IntArray, Int) -> Unit) {
        cases.forEach { (nums, k, expected) ->
            val actual = nums.copyOf()
            solution(actual, k)
            assertContentEquals(expected, actual, "nums = ${nums.contentToString()}, k = $k")
        }
    }

    @Test
    fun `starter solution`() {
        verify(::rotate)
    }

    @Test
    fun `bruteforce solution`() {
        verify(::rotateBruteforce)
    }

    @Test
    fun `extra array solution`() {
        verify(::rotateExtraArray)
    }

    @Test
    fun `cyclic replacements solution`() {
        verify(::rotateCyclicReplacements)
    }

    @Test
    fun `reverse solution`() {
        verify(::rotateReverse)
    }
}
