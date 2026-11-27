package toolbox.hashmap

import java.util.TreeMap

/*
Map and set types
Three maps, each with a matching set, that differ in the order they keep and what that order costs:

                 Kotlin                     get / put / remove        order               when
HashMap          hashMapOf()                O(1) average              none                lookups, counting, seen sets
HashSet          hashSetOf()                O(n) worst case *
LinkedHashMap    mutableMapOf(), mapOf()    O(1) average              insertion order     predictable output, first unique,
LinkedHashSet    mutableSetOf(), setOf()    a bit more memory         (or access order)   LRU cache
TreeMap          sortedMapOf()              O(log n)                  sorted by key       sorted output, min / max,
TreeSet          sortedSetOf()              (red-black tree)                              floor / ceiling, ranges

* when many keys share a bucket. Since Java 8, a long bucket turns into a small tree, so Comparable keys get
  O(log n) at worst.

A set is a map without values: HashSet is backed by a HashMap, TreeSet by a TreeMap, and they cost the same.
Iterating takes O(n) in all three, the HashMap also visits its empty buckets.

When to reach for it: pick HashMap / HashSet by default. Switch to Linked... when the output order must be
predictable or "first" means "first inserted", and to Tree... when you need the keys sorted, the smallest or
largest key, or the nearest key below or above a value.

Seen in:
    questions/hashmap/longest_consecutive/solution/LongestConsecutiveTwoSets.kt          HashSet lookups
    questions/hashmap/randomized_collection/solution/RandomizedCollectionHashMap.kt     LinkedHashSet, first() in O(1)
    questions/hashmap/naming_a_company/solution/NamingACompanySuffixGroups.kt           HashSet per letter

Kotlin: mutableMapOf() and mutableSetOf() give a LinkedHashMap and a LinkedHashSet, not the plain hash versions.
Use hashMapOf() / hashSetOf() for those. A TreeMap needs keys that are Comparable, or a Comparator.

insert 30, 10, 20:  hashSetOf -> any order,  mutableSetOf -> [30, 10, 20],  sortedSetOf -> [10, 20, 30]

n: number of entries
T: O(1) average per operation for the hash versions, O(log n) for the tree versions
S: O(n)
 */

// the first item that occurs exactly once: the LinkedHashMap keeps the counts in insertion order
fun <T> firstUnique(items: List<T>): T? {
    val counts = LinkedHashMap<T, Int>()
    for (item in items) {
        counts[item] = counts.getOrDefault(item, 0) + 1
    }
    return counts.entries.firstOrNull { it.value == 1 }?.key
}

// the key closest to x, in O(log n): floorKey is the largest key <= x, ceilingKey the smallest key >= x
fun <V> nearestKey(map: TreeMap<Int, V>, x: Int): Int? {
    val below = map.floorKey(x)
    val above = map.ceilingKey(x)
    if (below == null) return above
    if (above == null) return below
    return if (x.toLong() - below <= above.toLong() - x) below else above
}

// a map of at most capacity entries that drops the least recently used one: access order + removeEldestEntry
fun <K, V> lruCache(capacity: Int): MutableMap<K, V> = object : LinkedHashMap<K, V>(16, 0.75f, true) {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>): Boolean = size > capacity
}
