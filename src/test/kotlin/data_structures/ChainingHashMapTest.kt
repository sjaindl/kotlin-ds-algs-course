package data_structures

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ChainingHashMapTest {

    // Every instance lands in the same bucket
    private class CollidingKey(val id: Int) {
        override fun equals(other: Any?): Boolean = other is CollidingKey && other.id == id
        override fun hashCode(): Int = 1
    }

    @Test
    fun `new map is empty with default capacity`() {
        val map = ChainingHashMap<String, Int>()

        assertTrue(map.isEmpty())
        assertEquals(0, map.size)
        assertEquals(ChainingHashMap.DEFAULT_CAPACITY, map.capacity)
    }

    @Test
    fun `non-positive initial capacity is rejected`() {
        assertFailsWith<IllegalArgumentException> {
            ChainingHashMap<String, Int>(0)
        }
    }

    @Test
    fun `put and get`() {
        val map = ChainingHashMap<String, Int>()
        map.put("cat", 1)
        map.put("dog", 2)

        assertEquals(1, map["cat"])
        assertEquals(2, map["dog"])
        assertEquals(2, map.size)
    }

    @Test
    fun `get of a missing key returns null`() {
        val map = ChainingHashMap<String, Int>()
        map.put("cat", 1)

        assertNull(map["dog"])
        assertFalse("dog" in map)
    }

    @Test
    fun `put of an existing key replaces the value`() {
        val map = ChainingHashMap<String, Int>()
        map.put("cat", 1)
        map.put("cat", 2)

        assertEquals(2, map["cat"])
        assertEquals(1, map.size)
    }

    @Test
    fun `remove returns the value and deletes the key`() {
        val map = ChainingHashMap<String, Int>()
        map.put("cat", 1)
        map.put("dog", 2)

        assertEquals(1, map.remove("cat"))
        assertNull(map["cat"])
        assertEquals(2, map["dog"])
        assertEquals(1, map.size)
    }

    @Test
    fun `remove of a missing key returns null`() {
        val map = ChainingHashMap<String, Int>()

        assertNull(map.remove("cat"))
        assertEquals(0, map.size)
    }

    @Test
    fun `keys with the same index share a bucket`() {
        val map = ChainingHashMap<Int, String>()
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
        val map = ChainingHashMap<CollidingKey, Int>()
        for (id in 0 until 100) {
            map.put(CollidingKey(id), id)
        }

        assertEquals(100, map.size)
        for (id in 0 until 100) {
            assertEquals(id, map[CollidingKey(id)])
        }
    }

    @Test
    fun `negative hash codes are mapped to a valid bucket`() {
        val map = ChainingHashMap<Int, Int>()
        map.put(-3, 3)

        assertEquals(3, map[-3])
    }

    @Test
    fun `capacity doubles when the load factor is exceeded`() {
        val map = ChainingHashMap<Int, Int>()
        for (i in 1..12) {
            map.put(i, i)
        }
        assertEquals(16, map.capacity)

        map.put(13, 13)
        assertEquals(32, map.capacity)
    }

    @Test
    fun `all entries are found after many resizes`() {
        val map = ChainingHashMap<Int, Int>(initialCapacity = 1)
        for (i in 0 until 1000) {
            map.put(i, i * i)
        }

        assertEquals(1000, map.size)
        for (i in 0 until 1000) {
            assertEquals(i * i, map[i])
        }
    }
}
