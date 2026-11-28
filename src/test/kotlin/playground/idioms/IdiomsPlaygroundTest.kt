package playground.idioms

import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class IdiomsPlaygroundTest {

    @Test
    fun `length or zero`() {
        assertEquals(0, lengthOrZero(null))
        assertEquals(0, lengthOrZero(""))
        assertEquals(3, lengthOrZero("abc"))
    }

    @Test
    fun `positive or null`() {
        assertEquals(42, positiveOrNull("42"))
        assertNull(positiveOrNull("-3"))
        assertNull(positiveOrNull("0")) // 0 is not positive
        assertNull(positiveOrNull("x"))
    }

    @Test
    fun `first with prefix`() {
        assertEquals("BANANA", firstWithPrefix(listOf("apple", "banana", "band"), "ba"))
        assertEquals("none", firstWithPrefix(listOf("apple"), "x"))
        assertEquals("none", firstWithPrefix(emptyList(), ""))
    }

    @Test
    fun `linked list with apply`() {
        assertEquals(listOf(1, 2, 3), linkedListOf(1, 2, 3).values())
        assertEquals(listOf(7), linkedListOf(7).values())
        assertNull(linkedListOf())
        assertEquals(emptyList(), linkedListOf().values()) // null list
    }

    @Test
    fun `swap with also`() {
        val numbers = intArrayOf(1, 2, 3)
        numbers.swapWithAlso(0, 2)
        assertEquals(listOf(3, 2, 1), numbers.toList())
        numbers.swapWithAlso(1, 1)
        assertEquals(listOf(3, 2, 1), numbers.toList()) // same index
        var a = 1
        var b = 2
        a = b.also {
            b = a
        }
        assertEquals(2 to 1, a to b)
    }

    @Test
    fun `summary with with`() {
        assertEquals("size=3 min=1 max=7", summary(listOf(4, 1, 7)))
        assertEquals("empty", summary(emptyList()))
    }

    @Test
    fun `data class equality`() {
        assertEquals(Point(1, 2), Point(1, 2))
        assertEquals(1, hashSetOf(Point(1, 2), Point(1, 2)).size) // equal points collapse in a set
        val (x, y) = Point(5, 6)
        assertEquals(5 to 6, x to y)
        assertNotEquals(ListNode(1), ListNode(1)) // a plain class compares by reference
    }

    @Test
    fun `grid neighbours`() {
        assertEquals(listOf(Point(0, -1), Point(0, 1), Point(-1, 0), Point(1, 0)), neighbours(Point(0, 0)))
    }

    @Test
    fun `min max pair`() {
        assertEquals(1 to 7, minMax(listOf(4, 1, 7)))
        assertEquals(3 to 3, minMax(listOf(3)))
        assertNull(minMax(emptyList()))
    }

    @Test
    fun `grade with when`() {
        assertEquals('A', grade(100))
        assertEquals('A', grade(90)) // lower bound inclusive
        assertEquals('B', grade(89))
        assertEquals('C', grade(50))
        assertEquals('F', grade(0))
        assertFailsWith<IllegalArgumentException> {
            grade(101)
        }
        assertFailsWith<IllegalArgumentException> {
            grade(-1)
        }
    }

    @Test
    fun `find with labeled break`() {
        val grid = arrayOf(intArrayOf(1, 2), intArrayOf(3, 3))
        assertEquals(1 to 0, find(grid, 3)) // the first one
        assertNull(find(grid, 9))
        assertNull(find(emptyArray(), 1))
    }

    @Test
    fun `in bounds`() {
        assertTrue(inBounds(0, 0, 2, 3))
        assertTrue(inBounds(1, 2, 2, 3))
        assertFalse(inBounds(2, 0, 2, 3)) // row == rows
        assertFalse(inBounds(0, -1, 2, 3))
        assertFalse(inBounds(0, 0, 0, 0)) // empty grid
    }

    @Test
    fun `countdown and empty ranges`() {
        assertEquals(listOf(4, 3, 2, 1, 0), countdown(5))
        assertEquals(listOf(9, 6, 3, 0), countdown(10, 3))
        assertEquals(emptyList(), countdown(0))
        assertEquals(emptyList(), (5..0).toList()) // not a countdown
        assertEquals(emptyList(), (0 until 0).toList())
    }

    @Test
    fun `clamp into range`() {
        assertEquals(9, clamp(15, 0, 9))
        assertEquals(0, clamp(-4, 0, 9))
        assertEquals(5, clamp(5, 0, 9))
        assertFailsWith<IllegalArgumentException> {
            clamp(1, 9, 0) // low > high
        }
    }

    @Test
    fun `overflow and division`() {
        assertEquals(Int.MIN_VALUE, Int.MAX_VALUE + 1) // silent overflow
        assertEquals(Int.MIN_VALUE, abs(Int.MIN_VALUE)) // still negative
        assertEquals(-3, -7 / 2) // rounds toward 0
        assertEquals(-1, -7 % 3)
        assertEquals(2, (-7).mod(3))
        assertEquals(4_000_000_000L, 2_000_000_000.toLong() * 2)
        assertNotEquals(4_000_000_000L, (2_000_000_000 * 2).toLong()) // converted too late
    }

    @Test
    fun `middle without overflow`() {
        assertEquals(2_050_000_000, middle(2_000_000_000, 2_100_000_000))
        assertTrue((2_000_000_000 + 2_100_000_000) / 2 < 0) // the naive way overflows
        assertEquals(2, middle(2, 3)) // rounds down
        assertEquals(5, middle(5, 5))
    }

    @Test
    fun `power of two`() {
        assertTrue(isPowerOfTwo(1))
        assertTrue(isPowerOfTwo(1 shl 30))
        assertFalse(isPowerOfTwo(0))
        assertFalse(isPowerOfTwo(6))
        assertFalse(isPowerOfTwo(Int.MIN_VALUE)) // one bit set, but negative
    }

    @Test
    fun `single number`() {
        assertEquals(4, singleNumber(intArrayOf(4, 1, 2, 1, 2)))
        assertEquals(-5, singleNumber(intArrayOf(-5)))
        assertEquals(0, singleNumber(IntArray(0))) // xor of nothing
    }

    @Test
    fun `distance as long`() {
        assertEquals(3L, distance(1, 4))
        assertEquals(3L, distance(4, 1))
        assertEquals(4_294_967_295L, distance(Int.MIN_VALUE, Int.MAX_VALUE)) // no overflow
    }

    @Test
    fun `ceiling division`() {
        assertEquals(3, ceilDiv(7, 3))
        assertEquals(2, ceilDiv(6, 3)) // exact
        assertEquals(0, ceilDiv(0, 3))
        assertEquals(1, ceilDiv(1, 100))
    }

    @Test
    fun `safe divide with run catching`() {
        assertEquals("3", safeDivide(7, 2))
        assertEquals("error: / by zero", safeDivide(7, 0))
        assertNull(runCatching {
            "x".toInt()
        }.getOrNull())
    }

    @Test
    fun `non negative indices with return at label`() {
        assertEquals(listOf(0, 2), nonNegativeIndices(listOf(3, -1, 4, -5)))
        assertEquals(emptyList(), nonNegativeIndices(listOf(-1)))
        assertEquals(emptyList(), nonNegativeIndices(emptyList()))
        assertEquals(listOf(0), nonNegativeIndices(listOf(0))) // 0 is kept
    }

    @Test
    fun `has negative with return from forEach`() {
        assertTrue(hasNegative(listOf(3, -1, 4)))
        assertFalse(hasNegative(listOf(0, 1)))
        assertFalse(hasNegative(emptyList()))
    }

    @Test
    fun `pick a random element`() {
        val items = listOf(1, 2, 3)
        repeat(100) {
            assertTrue(pick(items)!! in items)
        }
        assertNull(pick(emptyList<Int>()))
        assertEquals(pick(items, Random(7)), pick(items, Random(7))) // same seed, same result
        assertEquals(setOf(1, 2, 3), List(300) {
            pick(items, Random(it))
        }.toSet()) // every element comes up
    }

    @Test
    fun `random between`() {
        val random = Random(1)
        val values = List(1000) {
            between(1, 6, random)
        }
        assertEquals((1..6).toSet(), values.toSet()) // both ends reachable
        assertEquals(5, between(5, 5)) // a single value
        assertFailsWith<IllegalArgumentException> {
            Random.nextInt(0)
        }
    }

    @Test
    fun `reservoir sample`() {
        val items = (1..10).toList()
        val picked = sample(items, 3, Random(3))
        assertEquals(3, picked.size)
        assertEquals(3, picked.toSet().size) // distinct
        assertTrue(items.containsAll(picked))
        assertEquals(listOf(1, 2), sample(listOf(1, 2), 5)) // k larger than the list: everything
        assertEquals(emptyList(), sample(items, 0))
        val counts = IntArray(11)
        val random = Random(11)
        repeat(10_000) {
            for (x in sample(items, 3, random)) {
                counts[x]++
            }
        }
        for (x in items) {
            assertTrue(counts[x] in 2_700..3_300) // about 3 / 10 of the runs each
        }
    }
}
