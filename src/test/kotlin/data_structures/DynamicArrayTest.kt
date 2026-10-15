package data_structures

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DynamicArrayTest {

    @Test
    fun `new array is empty with default capacity`() {
        val array = DynamicArray<Int>()

        assertTrue(array.isEmpty())
        assertEquals(0, array.size)
        assertEquals(DynamicArray.DEFAULT_CAPACITY, array.capacity)
    }

    @Test
    fun `non-positive initial capacity is rejected`() {
        assertFailsWith<IllegalArgumentException> {
            DynamicArray<Int>(0)
        }
    }

    @Test
    fun `add appends elements in order`() {
        val array = DynamicArray<String>()
        array.add("a")
        array.add("b")
        array.add("c")

        assertEquals(listOf("a", "b", "c"), array.toList())
        assertEquals("[a, b, c]", array.toString())
    }

    @Test
    fun `capacity doubles when full`() {
        val array = DynamicArray<Int>(initialCapacity = 2)

        array.add(1)
        array.add(2)
        assertEquals(2, array.capacity)

        array.add(3)
        assertEquals(4, array.capacity)

        array.add(4)
        array.add(5)
        assertEquals(8, array.capacity)
        assertEquals(listOf(1, 2, 3, 4, 5), array.toList())
    }

    @Test
    fun `capacity halves when a quarter full but not below initial capacity`() {
        val array = DynamicArray<Int>(initialCapacity = 2)
        for (i in 1..9) array.add(i) // capacity 16

        assertEquals(16, array.capacity)
        repeat(4) {
            array.removeLast()
        } // size 5
        assertEquals(16, array.capacity)

        array.removeLast() // size 4 = 16 / 4
        assertEquals(8, array.capacity)

        array.removeLast()
        array.removeLast() // size 2 = 8 / 4
        assertEquals(4, array.capacity)

        array.removeLast() // size 1 = 4 / 4
        assertEquals(2, array.capacity)

        array.removeLast() // size 0, keeps minimum capacity
        assertEquals(2, array.capacity)
        assertTrue(array.isEmpty())
    }

    @Test
    fun `alternating add and remove at the boundary does not thrash`() {
        val array = DynamicArray<Int>(initialCapacity = 2)
        for (i in 1..4) array.add(i) // full at capacity 4

        array.add(5) // grows to 8
        assertEquals(8, array.capacity)
        array.removeLast() // size 4 is half full, no shrink
        assertEquals(8, array.capacity)
        array.add(5)
        assertEquals(8, array.capacity)
    }

    @Test
    fun `add at index shifts elements right`() {
        val array = DynamicArray<Int>()
        array.add(1)
        array.add(3)

        array.add(index = 1, element = 2)
        array.add(index = 0, element = 0)
        array.add(index = array.size, element = 4)

        assertEquals(listOf(0, 1, 2, 3, 4), array.toList())
    }

    @Test
    fun `get and set access elements by index`() {
        val array = DynamicArray<Int>()
        array.add(10)
        array.add(20)

        array[1] = 25

        assertEquals(10, array[0])
        assertEquals(25, array[1])
    }

    @Test
    fun `removeAt shifts elements left and returns the removed element`() {
        val array = DynamicArray<Int>()
        for (i in 1..4) array.add(i)

        assertEquals(2, array.removeAt(1))
        assertEquals(listOf(1, 3, 4), array.toList())
        assertEquals(3, array.size)
    }

    @Test
    fun `indexOf and contains find elements including null`() {
        val array = DynamicArray<String?>()
        array.add("a")
        array.add(null)
        array.add("b")

        assertEquals(2, array.indexOf("b"))
        assertEquals(1, array.indexOf(null))
        assertEquals(-1, array.indexOf("z"))
        assertTrue("a" in array)
        assertFalse("z" in array)
    }

    @Test
    fun `out of bounds access throws`() {
        val array = DynamicArray<Int>()
        array.add(1)

        assertFailsWith<IndexOutOfBoundsException> {
            array[1]
        }
        assertFailsWith<IndexOutOfBoundsException> {
            array[-1]
        }
        assertFailsWith<IndexOutOfBoundsException> {
            array[1] = 5
        }
        assertFailsWith<IndexOutOfBoundsException> {
            array.removeAt(1)
        }
        assertFailsWith<IndexOutOfBoundsException> {
            array.add(index = 2, element = 5)
        }
    }

    @Test
    fun `removeLast on empty array throws`() {
        assertFailsWith<NoSuchElementException> {
            DynamicArray<Int>().removeLast()
        }
    }
}
