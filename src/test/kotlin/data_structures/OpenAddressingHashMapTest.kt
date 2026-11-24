package data_structures

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class OpenAddressingHashMapTest {

    // Every instance has the same home slot
    private class CollidingKey(val id: Int) {
        override fun equals(other: Any?): Boolean = other is CollidingKey && other.id == id
        override fun hashCode(): Int = 1
    }

    @Test
    fun `new map is empty with default capacity`() {
        val map = OpenAddressingHashMap<String, Int>()

        assertTrue(map.isEmpty())
        assertEquals(0, map.size)
        assertEquals(OpenAddressingHashMap.DEFAULT_CAPACITY, map.capacity)
    }

    @Test
    fun `non-positive initial capacity is rejected`() {
        assertFailsWith<IllegalArgumentException> {
            OpenAddressingHashMap<String, Int>(0)
        }
    }

    @Test
    fun `put and get`() {
        val map = OpenAddressingHashMap<String, Int>()
        map.put("cat", 1)
        map.put("dog", 2)

        assertEquals(1, map["cat"])
        assertEquals(2, map["dog"])
        assertEquals(2, map.size)
    }

    @Test
    fun `get of a missing key returns null`() {
        val map = OpenAddressingHashMap<String, Int>()
        map.put("cat", 1)

        assertNull(map["dog"])
        assertFalse("dog" in map)
    }

    @Test
    fun `put of an existing key replaces the value`() {
        val map = OpenAddressingHashMap<String, Int>()
        map.put("cat", 1)
        map.put("cat", 2)

        assertEquals(2, map["cat"])
        assertEquals(1, map.size)
    }

    @Test
    fun `remove returns the value and deletes the key`() {
        val map = OpenAddressingHashMap<String, Int>()
        map.put("cat", 1)
        map.put("dog", 2)

        assertEquals(1, map.remove("cat"))
        assertNull(map["cat"])
        assertEquals(2, map["dog"])
        assertEquals(1, map.size)
    }

    @Test
    fun `remove of a missing key returns null`() {
        val map = OpenAddressingHashMap<String, Int>()

        assertNull(map.remove("cat"))
        assertEquals(0, map.size)
    }

    @Test
    fun `keys with the same home slot probe to the next free slot`() {
        val map = OpenAddressingHashMap<Int, String>()
        map.put(17, "a")
        map.put(1, "b")
        map.put(33, "c")

        assertEquals("a", map[17])
        assertEquals("b", map[1])
        assertEquals("c", map[33])

        map.remove(1)
        assertEquals("a", map[17])
        assertEquals("c", map[33])
    }

    @Test
    fun `colliding hash codes are told apart by equals`() {
        val map = OpenAddressingHashMap<CollidingKey, Int>()
        for (id in 0 until 100) {
            map.put(CollidingKey(id), id)
        }

        assertEquals(100, map.size)
        for (id in 0 until 100) {
            assertEquals(id, map[CollidingKey(id)])
        }
    }

    @Test
    fun `negative hash codes are mapped to a valid slot`() {
        val map = OpenAddressingHashMap<Int, Int>()
        map.put(-3, 3)

        assertEquals(3, map[-3])
    }

    @Test
    fun `capacity doubles when the load factor is exceeded`() {
        val map = OpenAddressingHashMap<Int, Int>()
        for (i in 1..8) {
            map.put(i, i)
        }
        assertEquals(16, map.capacity)

        map.put(9, 9)
        assertEquals(32, map.capacity)
    }

    @Test
    fun `all entries are found after many resizes`() {
        val map = OpenAddressingHashMap<Int, Int>(initialCapacity = 1)
        for (i in 0 until 1000) {
            map.put(i, i * i)
        }

        assertEquals(1000, map.size)
        for (i in 0 until 1000) {
            assertEquals(i * i, map[i])
        }
    }

    @Test
    fun `a key behind a removed key is still found`() {
        val map = OpenAddressingHashMap<Int, String>()
        map.put(17, "a")
        map.put(1, "b")
        map.put(33, "c")

        map.remove(1)

        assertEquals("c", map[33])
        assertNull(map[1])
    }

    @Test
    fun `put of a key after a tombstone does not create a duplicate`() {
        val map = OpenAddressingHashMap<Int, String>()
        map.put(17, "a")
        map.put(1, "b")
        map.put(33, "c")
        map.remove(1)

        map.put(33, "d")
        map.remove(33)

        assertNull(map[33])
        assertEquals(1, map.size)
    }

    @Test
    fun `a removed key can be put again`() {
        val map = OpenAddressingHashMap<Int, String>()
        map.put(17, "a")
        map.put(1, "b")
        map.remove(1)

        map.put(1, "c")

        assertEquals("c", map[1])
        assertEquals(2, map.size)
    }

    @Test
    fun `repeated put and remove does not grow the table`() {
        val map = OpenAddressingHashMap<Int, Int>()
        for (i in 0 until 1000) {
            map.put(i, i)
            map.remove(i)
        }

        assertTrue(map.isEmpty())
        assertEquals(16, map.capacity)
    }
}
