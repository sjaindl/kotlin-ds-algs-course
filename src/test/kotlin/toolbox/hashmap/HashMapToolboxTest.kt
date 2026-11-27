package toolbox.hashmap

import java.util.TreeMap
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class HashMapToolboxTest {

    @Test
    fun `set types keep their order`() {
        val words = arrayOf("pear", "fig", "apple", "kiwi")
        assertEquals(listOf("pear", "fig", "apple", "kiwi"), mutableSetOf(*words).toList()) // insertion order
        assertEquals(listOf("apple", "fig", "kiwi", "pear"), sortedSetOf(*words).toList()) // sorted
        assertEquals(words.toSet(), hashSetOf(*words)) // same elements, any order
        assertTrue(mutableMapOf<Int, Int>() is LinkedHashMap) // Kotlin's default map keeps insertion order
    }

    @Test
    fun `first unique`() {
        assertEquals(2, firstUnique(listOf(3, 1, 3, 2, 1)))
        assertEquals('l', firstUnique("leetcode".toList()))
        assertNull(firstUnique(listOf(1, 1, 2, 2))) // no unique item
        assertNull(firstUnique(emptyList<Int>())) // empty
        assertEquals(7, firstUnique(listOf(7))) // single item
    }

    @Test
    fun `nearest key`() {
        val map = TreeMap(mapOf(10 to "a", 20 to "b", 40 to "c"))
        assertEquals(40, nearestKey(map, 33))
        assertEquals(20, nearestKey(map, 25)) // tie: the smaller key
        assertEquals(20, nearestKey(map, 20)) // exact key
        assertEquals(10, nearestKey(map, -100)) // below all keys: only a ceiling
        assertEquals(40, nearestKey(map, 1000)) // above all keys: only a floor
        assertNull(nearestKey(TreeMap<Int, String>(), 5)) // empty
        val extremes = TreeMap(mapOf(Int.MIN_VALUE to "min", Int.MAX_VALUE to "max"))
        assertEquals(Int.MIN_VALUE, nearestKey(extremes, -1)) // no Int overflow in the distances
        assertEquals(Int.MAX_VALUE, nearestKey(extremes, 1))
    }

    @Test
    fun `lru cache`() {
        val cache = lruCache<String, Int>(capacity = 2)
        cache["a"] = 1
        cache["b"] = 2
        cache["a"] // a is now the most recently used
        cache["c"] = 3
        assertEquals(listOf("a", "c"), cache.keys.toList()) // b was evicted
        cache["a"] = 10 // update counts as a use
        cache["d"] = 4
        assertEquals(listOf("a", "d"), cache.keys.toList())
        assertEquals(10, cache["a"])

        val single = lruCache<Int, Int>(capacity = 1)
        single[1] = 1
        single[2] = 2
        assertEquals(setOf(2), single.keys) // capacity 1 keeps only the last one
    }

    @Test
    fun `custom class keys`() {
        assertEquals(mapOf(Point(1, 2) to 2, Point(3, 4) to 1), countPoints(listOf(Point(1, 2), Point(1, 2), Point(3, 4))))
        assertEquals(emptyMap(), countPoints(emptyList())) // empty
        assertEquals(Point(1, 2).hashCode(), Point(1, 2).hashCode()) // equal objects, equal hash codes

        // a class without equals / hashCode, and arrays, compare by identity
        class PlainPoint(val x: Int, val y: Int)
        assertNotEquals(PlainPoint(1, 2), PlainPoint(1, 2))
        assertNull(hashMapOf(intArrayOf(1, 2) to "x")[intArrayOf(1, 2)])
        assertEquals("x", hashMapOf(listOf(1, 2) to "x")[listOf(1, 2)])
    }

    @Test
    fun `point key`() {
        assertEquals(4294967298L, pointKey(1, 2))
        assertNotEquals(pointKey(1, 2), pointKey(2, 1)) // order matters
        for ((x, y) in listOf(0 to 0, -3 to 7, 7 to -3, -1 to -1, Int.MIN_VALUE to Int.MAX_VALUE, Int.MAX_VALUE to Int.MIN_VALUE)) {
            val key = pointKey(x, y)
            assertEquals(x, xOf(key), "x of ($x, $y)")
            assertEquals(y, yOf(key), "y of ($x, $y)")
        }
        assertNotEquals(pointKey(0, -1), pointKey(-1, -1)) // a negative y must not spill into x
    }

    @Test
    fun `canonical order`() {
        assertEquals(2 to 5, unorderedPair(5, 2))
        assertEquals(unorderedPair(5, 2), unorderedPair(2, 5))
        assertEquals(3 to 3, unorderedPair(3, 3)) // equal values
        assertEquals(Int.MIN_VALUE to Int.MAX_VALUE, unorderedPair(Int.MAX_VALUE, Int.MIN_VALUE))

        val input = listOf(3, 1, 2)
        assertEquals(listOf(1, 2, 3), canonical(input))
        assertEquals(listOf(3, 1, 2), input) // the input stays as it was
        assertEquals(emptyList(), canonical(emptyList())) // empty

        assertEquals(2, distinctUpToOrder(listOf(listOf(1, 2, 3), listOf(3, 1, 2), listOf(2, 2))))
        assertEquals(2, distinctUpToOrder(listOf(listOf(1, 1, 2), listOf(1, 2, 2)))) // duplicates count: multisets
        assertEquals(1, distinctUpToOrder(listOf(emptyList(), emptyList())))
        assertEquals(0, distinctUpToOrder(emptyList()))
    }

    @Test
    fun `index map`() {
        val letters = listOf("b", "a", "b")
        assertEquals(mapOf("b" to 2, "a" to 1), indexOfEach(letters)) // last index wins
        assertEquals(mapOf("b" to listOf(0, 2), "a" to listOf(1)), indicesOfEach(letters))
        assertEquals(emptyMap(), indexOfEach(emptyList<Int>()))
        assertEquals(emptyMap(), indicesOfEach(emptyList<Int>()))
        assertEquals(mapOf(5 to listOf(0, 1, 2)), indicesOfEach(listOf(5, 5, 5))) // all equal
    }

    @Test
    fun `swap remove`() {
        val list = mutableListOf("a", "b", "c", "d")
        assertEquals("b", list.swapRemove(1))
        assertEquals(listOf("a", "d", "c"), list) // d moved into the gap

        assertEquals("c", list.swapRemove(2)) // the last index: nothing moves
        assertEquals(listOf("a", "d"), list)

        val single = mutableListOf(42)
        assertEquals(42, single.swapRemove(0))
        assertTrue(single.isEmpty())

        assertFailsWith<IndexOutOfBoundsException> {
            mutableListOf<Int>().swapRemove(0)
        }
    }

    @Test
    fun `set lookups`() {
        assertTrue(hasDuplicate(listOf(1, 2, 3, 1)))
        assertFalse(hasDuplicate(listOf(1, 2, 3)))
        assertFalse(hasDuplicate(emptyList<Int>())) // empty
        assertFalse(hasDuplicate(listOf(1))) // single item
        assertTrue(hasDuplicate(listOf("a", "b", "a"))) // any type with equals and hashCode
    }

    @Test
    fun `group and multiply`() {
        val ideas = listOf("coffee", "donuts", "time", "toffee")
        val suffixes = groupSets(ideas, key = { it[0] }, value = { it.drop(1) })
        assertEquals(mapOf('c' to setOf("offee"), 'd' to setOf("onuts"), 't' to setOf("ime", "offee")), suffixes)
        assertEquals(emptyMap(), groupSets(emptyList<String>(), key = { it.length }, value = { it }))

        assertEquals(0L, crossPairs(suffixes.getValue('t'), suffixes.getValue('c'))) // "offee" shared
        assertEquals(2L, crossPairs(suffixes.getValue('t'), suffixes.getValue('d')))
        assertEquals(0L, crossPairs(setOf(1, 2), emptySet())) // an empty group
        assertEquals(0L, crossPairs(setOf(1, 2), setOf(1, 2))) // all shared

        val big = (0 until 50_000).toSet()
        val other = (50_000 until 100_000).toSet()
        assertEquals(2_500_000_000L, crossPairs(big, other)) // larger than Int.MAX_VALUE
    }
}
