package questions.array.rotate_array

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertTimeoutPreemptively
import org.junit.jupiter.api.Test
import java.time.Duration
import questions.array.rotate_array.solution.rotateReverse as rotate

class Evaluate {

    private fun check(nums: IntArray, k: Int, expected: IntArray) {
        val input = nums.contentToString()
        rotate(nums, k)
        assertArrayEquals(expected, nums, "rotate($input, k = $k) gave ${nums.contentToString()}")
    }

    @Test
    fun example1() {
        check(intArrayOf(1, 2, 3, 4, 5, 6, 7), 3, intArrayOf(5, 6, 7, 1, 2, 3, 4))
    }

    @Test
    fun example2() {
        check(intArrayOf(-1, -100, 3, 99), 2, intArrayOf(3, 99, -1, -100))
    }

    @Test
    fun kIsZero() {
        check(intArrayOf(1, 2, 3), 0, intArrayOf(1, 2, 3))
    }

    @Test
    fun kEqualsLength() {
        check(intArrayOf(1, 2, 3), 3, intArrayOf(1, 2, 3))
    }

    @Test
    fun kGreaterThanLength() {
        check(intArrayOf(1, 2, 3), 10, intArrayOf(3, 1, 2))
    }

    @Test
    fun kIsLengthMinusOne() {
        check(intArrayOf(1, 2, 3, 4), 3, intArrayOf(2, 3, 4, 1))
    }

    @Test
    fun singleElement() {
        check(intArrayOf(42), 5, intArrayOf(42))
    }

    @Test
    fun twoElements() {
        check(intArrayOf(1, 2), 1, intArrayOf(2, 1))
    }

    @Test
    fun allValuesEqual() {
        check(intArrayOf(7, 7, 7, 7), 3, intArrayOf(7, 7, 7, 7))
    }

    @Test
    fun negativeNumbersAndDuplicates() {
        check(intArrayOf(-1, -1, 2, -3), 1, intArrayOf(-3, -1, -1, 2))
    }

    // length and k share a divisor: one cycle of index jumps doesn't visit every index
    @Test
    fun kSharesDivisorWithLength() {
        check(intArrayOf(1, 2, 3, 4, 5, 6), 2, intArrayOf(5, 6, 1, 2, 3, 4))
    }

    @Test
    fun kIsMultipleOfDivisorOfLength() {
        check(intArrayOf(1, 2, 3, 4, 5, 6), 4, intArrayOf(3, 4, 5, 6, 1, 2))
    }

    @Test
    fun extremeValues() {
        check(intArrayOf(Int.MIN_VALUE, 0, Int.MAX_VALUE), 1, intArrayOf(Int.MAX_VALUE, Int.MIN_VALUE, 0))
    }

    @Test
    fun largeInput() {
        val n = 100_000

        assertTimeoutPreemptively(Duration.ofSeconds(1), {
            for (k in intArrayOf(99_999, 50_000, 33_333)) {
                val nums = IntArray(n) { it }
                val expected = IntArray(n)
                for (index in 0 until n) {
                    expected[(index + k) % n] = index
                }

                rotate(nums, k)
                assertArrayEquals(expected, nums, "rotate([0, 1, …, ${n - 1}], k = $k) gave a wrong result")
            }
        }, "rotate is too slow for n = $n, aim for O(n) time")
    }
}
