package playground.hashmap

import java.util.TreeMap
import java.util.TreeSet

/*
Sorted maps and sets (red-black trees)
TreeMap(), sortedMapOf(), TreeSet(), sortedSetOf()
map.firstKey(), map.lastKey()                       smallest / largest key, throw on an empty map
map.firstEntry(), map.pollFirstEntry()              entry or null, poll also removes it
map.floorKey(x), map.ceilingKey(x)                  largest key <= x / smallest key >= x, or null
map.lowerKey(x), map.higherKey(x)                   strictly < x / strictly > x
map.headMap(x), map.tailMap(x), map.subMap(a, b)    views: keys < x, keys >= x, a <= keys < b
set.first(), set.last(), set.floor(x), set.ceiling(x), set.lower(x), set.higher(x), set.pollFirst()
TreeSet(compareByDescending<Int> { it })            any order with a Comparator

Kotlin pitfalls:
- floorKey and friends return null, not -1. Handle the null with ?: or ?.let.
- A TreeSet with a Comparator treats elements that compare as 0 as the same element: TreeSet(compareBy { it.length })
  keeps only one word per length.
- sortedMapOf() is declared as SortedMap, which has no floorKey. Use TreeMap() when you need the navigation calls.

See toolbox: toolbox/hashmap/MapAndSetTypes.kt has nearestKey and lruCache.

n: number of entries
T: O(log n) per operation, O(log n + k) for a view of k entries
S: O(n)
 */

// the smallest value in set that is strictly larger than x, null if there is none (the "next greater")
fun nextLarger(set: TreeSet<Int>, x: Int): Int? = set.higher(x)

// the values of all keys in [from, to], in key order
fun <V> valuesBetween(map: TreeMap<Int, V>, from: Int, to: Int): List<V> {
    if (from > to) return emptyList()
    return map.subMap(from, true, to, true).values.toList()
}

// the value that was valid at time t: the entry with the largest key <= t (a time-based key-value store)
fun <V> valueAt(history: TreeMap<Int, V>, t: Int): V? = history.floorEntry(t)?.value

// removes and returns the k smallest values, smallest first
fun takeSmallest(set: TreeSet<Int>, k: Int): List<Int> {
    val result = mutableListOf<Int>()
    while (result.size < k && set.isNotEmpty()) {
        result.add(set.pollFirst()!!)
    }
    return result
}
