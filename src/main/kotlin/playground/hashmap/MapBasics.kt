package playground.hashmap

/*
Maps
mapOf("a" to 1), mutableMapOf(), hashMapOf()   mapOf / mutableMapOf keep insertion order (LinkedHashMap)
map[key]                                       V? : null when the key is missing
map.getOrDefault(key, 0)
map.getOrPut(key) { mutableListOf() }          inserts the default when the key is missing, returns the value
map.getValue(key)                              V : throws NoSuchElementException when the key is missing
map[key] = value, map.remove(key), key in map
map.merge(key, 1, Int::plus)                   counting in one call: put 1, or add 1 to the old value
map.compute(key) { _, old -> ... }             returning null removes the entry
for ((key, value) in map)                      destructuring the entries
map.keys, map.values, map.entries
map.filterValues { }, map.mapValues { }, map.maxByOrNull { it.value }
map.toList() / list.toMap()                    List<Pair<K, V>> <-> Map<K, V>

Kotlin pitfalls:
- map[key]!! + 1 crashes on a missing key. Use getOrDefault(key, 0) + 1 or merge.
- map[key]?.add(x) silently does nothing for a missing key. getOrPut(key) { mutableListOf() }.add(x) creates it.
- Changing a map while iterating over it throws ConcurrentModificationException. Collect the keys first, or
  use an iterator's remove().

See toolbox: toolbox/hashmap/MapAndSetTypes.kt for the HashMap / LinkedHashMap / TreeMap table.

n: number of entries
T: O(1) average per operation (hash map), O(n) to iterate
S: O(n)
 */

// how often each word occurs, counted with merge
fun wordCount(words: List<String>): Map<String, Int> {
    val counts = HashMap<String, Int>()
    for (word in words) {
        counts.merge(word, 1, Int::plus)
    }
    return counts
}

// all indices of each value: getOrPut creates the list on the first occurrence
fun <T> positions(items: List<T>): Map<T, List<Int>> {
    val result = HashMap<T, MutableList<Int>>()
    for ((i, item) in items.withIndex()) {
        result.getOrPut(item) {
            mutableListOf()
        }.add(i)
    }
    return result
}

// decrements the count of key and removes it when it reaches 0 (sliding-window bookkeeping)
fun <K> MutableMap<K, Int>.decrement(key: K) {
    compute(key) { _, old ->
        if (old == null || old <= 1) null else old - 1
    }
}

// the map with keys and values swapped, a later key wins on duplicate values
fun <K, V> Map<K, V>.inverted(): Map<V, K> = entries.associate { (key, value) ->
    value to key
}
