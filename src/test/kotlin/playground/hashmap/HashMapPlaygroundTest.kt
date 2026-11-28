package playground.hashmap

import java.util.TreeMap
import java.util.TreeSet
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class HashMapPlaygroundTest {

    @Test
    fun `word count`() {
        assertEquals(mapOf("to" to 2, "be" to 1), wordCount(listOf("to", "be", "to")))
        assertEquals(emptyMap(), wordCount(emptyList()))
    }

    @Test
    fun `missing keys`() {
        val map = mapOf("a" to 1)
        assertNull(map["x"])
        assertEquals(0, map.getOrDefault("x", 0))
        assertFailsWith<NoSuchElementException> {
            map.getValue("x")
        }
    }

    @Test
    fun `positions of each value`() {
        assertEquals(mapOf("b" to listOf(0, 2), "a" to listOf(1)), positions(listOf("b", "a", "b")))
        assertEquals(emptyMap(), positions(emptyList<Int>()))
    }

    @Test
    fun `decrement a count`() {
        val counts = mutableMapOf("a" to 2, "b" to 1)
        counts.decrement("a")
        assertEquals(mapOf("a" to 1, "b" to 1), counts)
        counts.decrement("b")
        assertEquals(mapOf("a" to 1), counts) // reached 0: removed
        counts.decrement("x")
        assertEquals(mapOf("a" to 1), counts) // missing key: nothing happens
    }

    @Test
    fun `inverted map`() {
        assertEquals(mapOf(1 to "a", 2 to "b"), mapOf("a" to 1, "b" to 2).inverted())
        assertEquals(mapOf(1 to "b"), mapOf("a" to 1, "b" to 1).inverted()) // duplicate value: the later key wins
        assertEquals(emptyMap(), emptyMap<String, Int>().inverted())
    }

    @Test
    fun `modify while iterating`() {
        val map = hashMapOf(1 to "a", 2 to "b")
        assertFailsWith<ConcurrentModificationException> {
            for (key in map.keys) {
                map.remove(key)
            }
        }
        val safe = hashMapOf(1 to "a", 2 to "b", 3 to "c")
        safe.entries.removeIf {
            it.key % 2 == 1
        }
        assertEquals(mapOf(2 to "b"), safe)
    }

    @Test
    fun `first repeated`() {
        assertEquals(1, firstRepeated(listOf(3, 1, 4, 1, 5, 3)))
        assertNull(firstRepeated(listOf(1, 2, 3))) // all distinct
        assertNull(firstRepeated(emptyList<Int>()))
        assertEquals("a", firstRepeated(listOf("a", "a")))
    }

    @Test
    fun `common values`() {
        assertEquals(listOf(2, 3), common(listOf(1, 2, 2, 3), listOf(3, 2, 4)))
        assertEquals(emptyList(), common(listOf(1), listOf(2)))
        assertEquals(emptyList(), common(emptyList(), listOf(2)))
    }

    @Test
    fun `only in one`() {
        assertEquals(setOf(1, 4), onlyInOne(setOf(1, 2, 3), setOf(2, 3, 4)))
        assertEquals(emptySet(), onlyInOne(setOf(1), setOf(1)))
        assertEquals(setOf(1), onlyInOne(setOf(1), emptySet()))
    }

    @Test
    fun `is subset`() {
        assertTrue(isSubset(listOf(1, 2), listOf(3, 2, 1)))
        assertFalse(isSubset(listOf(1, 5), listOf(1, 2)))
        assertTrue(isSubset(emptyList(), listOf(1))) // the empty set is a subset of everything
        assertTrue(isSubset(listOf(1, 1), listOf(1))) // counts do not matter
    }

    @Test
    fun `next larger`() {
        val set = TreeSet(listOf(10, 20, 30))
        assertEquals(30, nextLarger(set, 20)) // strictly larger
        assertEquals(10, nextLarger(set, -5))
        assertNull(nextLarger(set, 30)) // nothing above the largest
        assertNull(nextLarger(TreeSet(), 1))
    }

    @Test
    fun `values between`() {
        val map = TreeMap(mapOf(1 to "a", 5 to "b", 9 to "c"))
        assertEquals(listOf("b", "c"), valuesBetween(map, 2, 9)) // both ends inclusive
        assertEquals(listOf("a", "b", "c"), valuesBetween(map, 1, 9))
        assertEquals(emptyList(), valuesBetween(map, 6, 8))
        assertEquals(emptyList(), valuesBetween(map, 9, 1)) // from > to
    }

    @Test
    fun `value at a time`() {
        val history = TreeMap(mapOf(1 to "v1", 5 to "v2"))
        assertEquals("v1", valueAt(history, 4))
        assertEquals("v2", valueAt(history, 5)) // exact time
        assertEquals("v2", valueAt(history, 100))
        assertNull(valueAt(history, 0)) // before the first entry
    }

    @Test
    fun `take smallest`() {
        val set = TreeSet(listOf(5, 1, 3))
        assertEquals(listOf(1, 3), takeSmallest(set, 2))
        assertEquals(setOf(5), set) // removed
        assertEquals(listOf(5), takeSmallest(set, 10)) // fewer than k left
        assertEquals(emptyList(), takeSmallest(set, 1)) // empty
    }

    @Test
    fun `comparator decides equality in a tree set`() {
        val byLength = TreeSet(compareBy<String> {
            it.length
        })
        byLength.addAll(listOf("ab", "cd", "e"))
        assertEquals(listOf("e", "ab"), byLength.toList()) // "cd" counts as a duplicate of "ab"
    }
}
