package playground.collections

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CollectionsPlaygroundTest {

    @Test
    fun `pop or null`() {
        val stack = mutableListOf(1, 2)
        assertEquals(2, stack.popOrNull())
        assertEquals(1, stack.popOrNull())
        assertNull(stack.popOrNull()) // empty
    }

    @Test
    fun `move an element`() {
        val list = mutableListOf("a", "b", "c", "d")
        list.move(0, 3)
        assertEquals(listOf("b", "c", "d", "a"), list)
        list.move(3, 0)
        assertEquals(listOf("a", "b", "c", "d"), list) // back
        list.move(1, 1)
        assertEquals(listOf("a", "b", "c", "d"), list) // same place
    }

    @Test
    fun `at with default`() {
        val list = listOf(1, 2)
        assertEquals(2, list.at(1, -1))
        assertEquals(-1, list.at(2, -1)) // past the end
        assertEquals(-1, list.at(-1, -1)) // negative index
    }

    @Test
    fun `remove value vs index`() {
        val list = mutableListOf(10, 20, 1)
        list.remove(1) // the value 1
        assertEquals(listOf(10, 20), list)
        list.removeAt(0) // the index 0
        assertEquals(listOf(20), list)
    }

    @Test
    fun `neighbour differences`() {
        assertEquals(listOf(3, 5), differences(listOf(1, 4, 9)))
        assertEquals(emptyList(), differences(listOf(7))) // no neighbours
        assertEquals(emptyList(), differences(emptyList()))
    }

    @Test
    fun `even odd`() {
        assertEquals(listOf(2, 4) to listOf(1, 3), evenOdd(listOf(1, 2, 3, 4)))
        assertEquals(listOf(-2) to listOf(-3), evenOdd(listOf(-3, -2))) // -3 % 2 is -1, still odd
        assertEquals(emptyList<Int>() to emptyList(), evenOdd(emptyList()))
    }

    @Test
    fun `word lengths`() {
        assertEquals(mapOf("hi" to 2, "you" to 3), lengths(listOf("hi", "you")))
        assertEquals(mapOf("a" to 1), lengths(listOf("a", "a"))) // duplicates collapse
        assertEquals(emptyMap(), lengths(emptyList()))
    }

    @Test
    fun `transpose a matrix`() {
        assertEquals(listOf(listOf(1, 4), listOf(2, 5), listOf(3, 6)), transpose(listOf(listOf(1, 2, 3), listOf(4, 5, 6))))
        assertEquals(listOf(listOf(1), listOf(2)), transpose(listOf(listOf(1, 2))))
        assertEquals(emptyList(), transpose(emptyList()))
    }

    @Test
    fun `count frequencies`() {
        assertEquals(mapOf("a" to 2, "b" to 1), frequencies(listOf("a", "b", "a")))
        assertEquals(emptyMap(), frequencies(emptyList<Int>()))
    }

    @Test
    fun `most frequent`() {
        assertEquals("b", mostFrequent(listOf("b", "a", "b")))
        assertEquals(1, mostFrequent(listOf(1, 2, 2, 1))) // tie: the one seen first
        assertNull(mostFrequent(emptyList<Int>()))
    }

    @Test
    fun `by first letter`() {
        assertEquals(mapOf('a' to listOf("ant", "ape"), 'b' to listOf("bee")), byFirstLetter(listOf("ant", "bee", "ape")))
        assertEquals(mapOf('x' to listOf("x")), byFirstLetter(listOf("", "x"))) // empty word skipped
    }

    @Test
    fun `product and empty aggregations`() {
        assertEquals(24L, product(listOf(1, 2, 3, 4)))
        assertEquals(1L, product(emptyList())) // fold starts at 1
        val twos = List(40) {
            2
        }
        assertEquals(1L shl 40, product(twos)) // no Int overflow
        val allOfNothing = emptyList<Int>().all {
            false
        }
        assertTrue(allOfNothing) // vacuously true
        assertFailsWith<UnsupportedOperationException> {
            emptyList<Int>().reduce { a, b ->
                a + b
            }
        }
        assertFailsWith<NoSuchElementException> {
            emptyList<Int>().max()
        }
    }

    @Test
    fun `oldest first`() {
        val people = listOf(Person("Bo", 30), Person("Al", 25), Person("Ann", 30))
        assertEquals(listOf(Person("Ann", 30), Person("Bo", 30), Person("Al", 25)), oldestFirst(people))
        assertEquals(emptyList(), oldestFirst(emptyList()))
    }

    @Test
    fun `sort intervals`() {
        val intervals = arrayOf(intArrayOf(3, 4), intArrayOf(1, 5), intArrayOf(1, 2))
        sortIntervals(intervals)
        assertContentEquals(intArrayOf(1, 2), intervals[0])
        assertContentEquals(intArrayOf(1, 5), intervals[1])
        assertContentEquals(intArrayOf(3, 4), intervals[2])
        val extremes = arrayOf(intArrayOf(Int.MAX_VALUE, 0), intArrayOf(Int.MIN_VALUE, 0))
        sortIntervals(extremes)
        assertEquals(Int.MIN_VALUE, extremes[0][0]) // no overflow in the comparison
    }

    @Test
    fun `subtraction comparator overflows`() {
        val broken = Comparator<Int> { a, b ->
            a - b
        }
        assertTrue(broken.compare(Int.MIN_VALUE, 1) > 0) // claims MIN_VALUE > 1
        val correct = compareBy<Int> {
            it
        }
        assertTrue(correct.compare(Int.MIN_VALUE, 1) < 0)
    }

    @Test
    fun `by length then alphabet`() {
        assertEquals(listOf("fig", "kiwi", "pear", "apple"), byLengthThenAlphabet(listOf("pear", "fig", "kiwi", "apple")))
        assertEquals(listOf("a", "a"), byLengthThenAlphabet(listOf("a", "a")))
    }

    @Test
    fun `top k`() {
        assertEquals(listOf(9, 5), topK(listOf(5, 1, 9, 3), 2))
        assertEquals(listOf(2, 1), topK(listOf(1, 2), 5)) // k larger than the list
        assertEquals(emptyList(), topK(listOf(1, 2), 0))
    }

    @Test
    fun `powers of two`() {
        assertEquals(listOf(1L, 2L, 4L, 8L), powersOfTwo(4))
        assertEquals(emptyList(), powersOfTwo(0))
        assertEquals(1L shl 62, powersOfTwo(63).last())
    }

    @Test
    fun `collatz steps`() {
        assertEquals(listOf(6, 3, 10, 5, 16, 8, 4, 2, 1), collatz(6))
        assertEquals(listOf(1), collatz(1)) // already done
    }

    @Test
    fun `first square above`() {
        assertEquals(9, firstSquareAbove(listOf(1, 2, 3, 4, 5), 5))
        assertNull(firstSquareAbove(listOf(1, 2), 100)) // none
        assertNull(firstSquareAbove(emptyList(), 0))
        var mapped = 0
        listOf(1, 2, 3, 4, 5).asSequence().map {
            mapped++
            it * it
        }.first {
            it > 5
        }
        assertEquals(3, mapped) // lazy: stops after 3
    }
}
