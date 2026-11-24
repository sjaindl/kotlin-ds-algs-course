package data_structures

/*
Hash map with separate chaining
The key's hash code picks a bucket: index = hashCode mod capacity. Every bucket holds a list of the entries that
landed there, so colliding keys simply share a bucket. put replaces the value of an existing key or appends a new
entry, get scans the one bucket and compares the keys with equals.

17, 1, 33 and 2 in 16 buckets:
    bucket 1 -> [17, 1, 33]     all three have the remainder 1
    bucket 2 -> [2]

Simple, removing is just removing from a list, and it still works with more entries than buckets. The cost is one
object per entry, linked by pointers. The JVM HashMap works like this (and turns long lists into trees).

n: number of entries
T: O(1) average for put, get and remove - O(n) if all keys collide
S: O(n + capacity)
 */
class ChainingHashMap<K : Any, V>(initialCapacity: Int = DEFAULT_CAPACITY) {

    companion object {
        const val DEFAULT_CAPACITY = 16
        const val MAX_LOAD_FACTOR = 0.75
    }

    init {
        require(initialCapacity > 0) {
            "Initial capacity must be positive, was $initialCapacity"
        }
    }

    // value is a var, so put can replace it in place
    private class Entry<K, V>(val key: K, var value: V)

    // A bucket stays null until its first entry arrives
    private var buckets = arrayOfNulls<MutableList<Entry<K, V>>>(initialCapacity)

    var size: Int = 0
        private set

    val capacity: Int
        get() = buckets.size

    fun isEmpty(): Boolean = size == 0

    // O(1) average: replace the value of an existing key, or append a new entry to its bucket
    fun put(key: K, value: V) {
        val existing = entryFor(key)
        if (existing != null) {
            existing.value = value
            return
        }

        val index = indexFor(key)
        val bucket = buckets[index] ?: mutableListOf()
        bucket.add(Entry(key, value))
        buckets[index] = bucket
        size++

        // Too many entries per bucket: grow (see the load factor lecture)
        if (size > capacity * MAX_LOAD_FACTOR) resize()
    }

    // O(1) average
    operator fun get(key: K): V? = entryFor(key)?.value

    // O(1) average
    operator fun contains(key: K): Boolean = entryFor(key) != null

    // O(1) average
    fun remove(key: K): V? {
        val bucket = buckets[indexFor(key)] ?: return null

        for (i in bucket.indices) {
            if (bucket[i].key == key) {
                size--
                return bucket.removeAt(i).value
            }
        }

        return null
    }

    // hashCode() can be negative: floorMod keeps the index in 0 until capacity, where % could give -3
    private fun indexFor(key: K): Int = Math.floorMod(key.hashCode(), capacity)

    // Scans the key's bucket only, comparing with equals
    private fun entryFor(key: K): Entry<K, V>? {
        val bucket = buckets[indexFor(key)] ?: return null

        for (entry in bucket) {
            if (entry.key == key) return entry
        }

        return null
    }

    // O(n): the index depends on the capacity, so every entry is put again into the doubled array
    private fun resize() {
        val old = buckets
        buckets = arrayOfNulls(old.size * 2)
        size = 0
        for (bucket in old) {
            bucket?.forEach {
                put(it.key, it.value)
            }
        }
    }
}
