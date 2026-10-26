package toolbox.array

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ArrayToolboxTest {

    @Test
    fun `circular index`() {
        assertEquals(0, circularIndex(0, 5)) // inside
        assertEquals(0, circularIndex(5, 5)) // one past the end
        assertEquals(2, circularIndex(12, 5)) // several rounds
        assertEquals(4, circularIndex(-1, 5)) // negative, where % would give -1
        assertEquals(0, circularIndex(7, 1)) // single element
        assertEquals(1, circularIndex(10, 3)) // shift k > n
        assertEquals(0, circularIndex(6, 3)) // shift that is a multiple of n
        assertEquals(2, circularIndex(-1, 3)) // negative shift = rotate the other way
    }

    @Test
    fun `next indices circular`() {
        assertEquals(listOf(4, 0, 1, 2), nextIndicesCircular(3, 5)) // wraps around
        assertEquals(listOf(1, 2), nextIndicesCircular(0, 3)) // no wrap needed
        assertEquals(emptyList(), nextIndicesCircular(0, 1)) // single element has no other index
    }

    @Test
    fun `doubled array`() {
        assertContentEquals(intArrayOf(1, 2, 1, 1, 2, 1), doubled(intArrayOf(1, 2, 1)))
        assertContentEquals(intArrayOf(4, 1, 2), circularSlice(intArrayOf(1, 2, 3, 4), 3, 3)) // wraps
        assertContentEquals(intArrayOf(2, 3), circularSlice(intArrayOf(1, 2, 3, 4), 1, 2)) // no wrap needed
        assertContentEquals(intArrayOf(3, 4, 1, 2), circularSlice(intArrayOf(1, 2, 3, 4), 2, 4)) // full round
        assertContentEquals(intArrayOf(5), circularSlice(intArrayOf(5), 0, 1)) // single element
    }

    @Test
    fun presence() {
        val seen = presence(intArrayOf(3, 4, -1, 1, 3, 100), 4) // duplicates and out-of-range values
        assertContentEquals(booleanArrayOf(false, true, false, true, true), seen)
    }

    @Test
    fun `place at`() {
        val numbers = intArrayOf(1, 2, 3, 4, 5)
        val rotated = placeAt(numbers) { index ->
            (index + 2) % numbers.size
        }
        assertContentEquals(intArrayOf(4, 5, 1, 2, 3), rotated) // rotate right by 2
        assertContentEquals(intArrayOf(1, 2, 3, 4, 5), numbers) // input unchanged
    }

    @Test
    fun swap() {
        val numbers = intArrayOf(1, 2, 3)
        numbers.swap(0, 2)
        assertContentEquals(intArrayOf(3, 2, 1), numbers)
        numbers.swap(1, 1) // same index
        assertContentEquals(intArrayOf(3, 2, 1), numbers)
    }

    @Test
    fun `sign marking`() {
        val numbers = intArrayOf(3, 4, -1, 1, 3) // duplicate 3, -1 out of range
        replaceOutOfRange(numbers, numbers.size, 1)
        numbers.copyOf().forEach { value ->
            markPresent(numbers, value)
        }

        val present = (1..numbers.size).filter { value ->
            isPresent(numbers, value)
        }
        assertEquals(listOf(1, 3, 4), present)
        assertFalse(isPresent(numbers, 2))
    }

    @Test
    fun `reverse range`() {
        val numbers = intArrayOf(1, 2, 3, 4, 5)
        numbers.reverseRange(1, 3)
        assertContentEquals(intArrayOf(1, 4, 3, 2, 5), numbers)
        numbers.reverseRange(2, 2) // single element
        numbers.reverseRange(3, 2) // empty range
        assertContentEquals(intArrayOf(1, 4, 3, 2, 5), numbers)
        numbers.reverseRange(0, numbers.size - 1) // whole array
        assertContentEquals(intArrayOf(5, 2, 3, 4, 1), numbers)
    }

    @Test
    fun `rotate with reverse trick`() {
        val cases = listOf(
            Triple(intArrayOf(1, 2, 3, 4, 5, 6, 7), 3, intArrayOf(5, 6, 7, 1, 2, 3, 4)),
            Triple(intArrayOf(-1, -100, 3, 99), 2, intArrayOf(3, 99, -1, -100)),
            Triple(intArrayOf(1, 2, 3), 10, intArrayOf(3, 1, 2)), // k > n
            Triple(intArrayOf(1, 2, 3), 0, intArrayOf(1, 2, 3)), // k = 0
            Triple(intArrayOf(7), 4, intArrayOf(7)) // single element
        )
        cases.forEach { (numbers, k, expected) ->
            val actual = numbers.copyOf()
            rotateRight(actual, k)
            assertContentEquals(expected, actual, "rotateRight(${numbers.contentToString()}, $k)")
            rotateLeft(actual, k)
            assertContentEquals(numbers, actual, "rotateLeft undoes rotateRight, k = $k")
        }
    }

    @Test
    fun `bound the answer`() {
        assertTrue(inRange(1, 3))
        assertTrue(inRange(3, 3))
        assertFalse(inRange(0, 3))
        assertFalse(inRange(4, 3))

        val nums = intArrayOf(Int.MIN_VALUE, 2, 0, Int.MAX_VALUE, 3)
        assertEquals(3, replaceOutOfRange(nums, nums.size, 1))
        assertContentEquals(intArrayOf(1, 2, 1, 1, 3), nums)
    }
}
