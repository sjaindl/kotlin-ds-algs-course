package data_structures

/*
Hash map with open addressing (linear probing)
All entries live in one flat array. The key's hash code picks its home slot: index = hashCode mod capacity. If that
slot is taken by another key, we try the next slot, and the next, wrapping around at the end. A lookup follows the
same path and stops at the key, or at an empty slot: then the key isn't there.

17, 1 and 33 in 16 slots, all with the home slot 1:
    slot 1 -> 17
    slot 2 -> 1      slot 1 was taken
    slot 3 -> 33     slots 1 and 2 were taken

No object per entry, and probing walks through neighboring memory (cache-friendly). But occupied slots form runs
(clustering), so the table must stay well below full. Removing can't simply empty a slot, that would cut the path
to the keys behind it: it leaves a tombstone instead. Python's dict and Java's IdentityHashMap work like this.

n: number of entries
T: O(1) average for put, get and remove, as long as the table is at most half full - O(n) worst case
S: O(capacity)
 */
class OpenAddressingHashMap<K : Any, V>(initialCapacity: Int = DEFAULT_CAPACITY) {

    companion object {
        const val DEFAULT_CAPACITY = 16

        // Probe paths get long quickly as the table fills, so keep it at most half full
        const val MAX_LOAD_FACTOR = 0.5
    }

    init {
        require(initialCapacity > 0) {
            "Initial capacity must be positive, was $initialCapacity"
        }
    }

    // Tombstone of a removed key: lookups keep probing past it, put may reuse its slot
    private object DELETED

    // Two parallel arrays: keys[i] belongs to values[i]. null is an empty slot
    private var keys: Array<Any?> = arrayOfNulls(initialCapacity)
    private var values: Array<Any?> = arrayOfNulls(initialCapacity)

    var size: Int = 0
        private set

    // Tombstones make probe paths longer too, so they count towards the load
    private var tombstones = 0

    val capacity: Int
        get() = keys.size

    fun isEmpty(): Boolean = size == 0

    // O(1) average: probe for the key, replace its value or insert it
    fun put(key: K, value: V) {
        var i = indexFor(key)
        var firstTombstone = -1

        while (keys[i] != null) {
            if (keys[i] == key) {
                values[i] = value
                return
            }
            if (keys[i] === DELETED && firstTombstone == -1) firstTombstone = i
            i = (i + 1) % capacity
        }

        // The key is new: reuse the first tombstone on its path, otherwise take the empty slot
        if (firstTombstone != -1) {
            i = firstTombstone
            tombstones--
        }
        keys[i] = key
        values[i] = value
        size++

        if (size + tombstones > capacity * MAX_LOAD_FACTOR) resize()
    }

    // O(1) average
    @Suppress("UNCHECKED_CAST")
    operator fun get(key: K): V? {
        val slot = slotOf(key)
        return if (slot == -1) null else values[slot] as V
    }

    // O(1) average
    operator fun contains(key: K): Boolean = slotOf(key) != -1

    // O(1) average
    @Suppress("UNCHECKED_CAST")
    fun remove(key: K): V? {
        val slot = slotOf(key)
        if (slot == -1) return null

        val removed = values[slot] as V
        // An empty slot here would end the lookup of every key behind it: leave a tombstone instead
        keys[slot] = DELETED
        values[slot] = null
        size--
        tombstones++

        return removed
    }

    // hashCode() can be negative: floorMod keeps the index in 0 until capacity, where % could give -3
    private fun indexFor(key: K): Int = Math.floorMod(key.hashCode(), capacity)

    // The key's slot, or -1: probe from the home slot until the key or an empty slot (past tombstones)
    private fun slotOf(key: K): Int {
        var i = indexFor(key)

        while (keys[i] != null) {
            if (keys[i] == key) return i
            i = (i + 1) % capacity
        }

        return -1
    }

    // O(n): put every live key again, which also drops all tombstones.
    // Mostly tombstones: rebuild at the same capacity instead of growing
    @Suppress("UNCHECKED_CAST")
    private fun resize() {
        val oldKeys = keys
        val oldValues = values
        val newCapacity = if (size > capacity * MAX_LOAD_FACTOR / 2) capacity * 2 else capacity

        keys = arrayOfNulls(newCapacity)
        values = arrayOfNulls(newCapacity)
        size = 0
        tombstones = 0

        for (i in oldKeys.indices) {
            val key = oldKeys[i]
            if (key != null && key !== DELETED) put(key as K, oldValues[i] as V)
        }
    }
}
