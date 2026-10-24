package data_structures

class DynamicArray<T>(initialCapacity: Int = DEFAULT_CAPACITY) : Iterable<T> {

    companion object {
        const val DEFAULT_CAPACITY = 4
    }

    init {
        require(initialCapacity > 0) {
            "Initial capacity must be positive, was $initialCapacity"
        }
    }

    // Never shrink below the capacity we started with
    private val minCapacity = initialCapacity

    // Generic arrays can't be created directly due to type erasure, so we store Any? and cast on read
    private var data: Array<Any?> = arrayOfNulls(initialCapacity)

    var size: Int = 0
        private set

    val capacity: Int
        get() = data.size

    fun isEmpty(): Boolean = size == 0

    // O(1) amortized: an occasional O(n) resize is spread over the n cheap appends before it
    fun add(element: T) {
        if (size == capacity) resize(newCapacity = capacity * 2)
        data[size++] = element
    }

    // O(n): every element from index onwards shifts one slot to the right
    fun add(index: Int, element: T) {
        if (index !in 0..size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
        if (size == capacity) resize(newCapacity = capacity * 2)

        for (i in size downTo index + 1) {
            data[i] = data[i - 1]
        }
        data[index] = element
        size++
    }

    // O(1)
    @Suppress("UNCHECKED_CAST")
    operator fun get(index: Int): T {
        checkIndex(index)
        return data[index] as T
    }

    // O(1)
    operator fun set(index: Int, element: T) {
        checkIndex(index)
        data[index] = element
    }

    // O(n): every element after index shifts one slot to the left
    fun removeAt(index: Int): T {
        val removed = get(index)

        for (i in index until size - 1) {
            data[i] = data[i + 1]
        }
        // Clear the vacated slot so the array doesn't keep the object alive (loitering)
        data[--size] = null

        shrinkIfNeeded()

        return removed
    }

    // O(1) amortized
    fun removeLast(): T {
        if (isEmpty()) throw NoSuchElementException("Array is empty")

        return removeAt(index = size - 1)
    }

    // O(n)
    fun indexOf(element: T): Int {
        for (i in 0 until size) {
            if (data[i] == element) return i
        }

        return -1
    }

    // O(n)
    operator fun contains(element: T): Boolean = indexOf(element) != -1

    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var current = 0

        override fun hasNext(): Boolean = current < size

        override fun next(): T {
            if (!hasNext()) throw NoSuchElementException()
            return get(current++)
        }
    }

    // O(n)
    override fun toString(): String = joinToString(prefix = "[", postfix = "]")

    // Shrink at one quarter (not one half) full: after halving, the array is half full,
    // so neither an add nor a remove can immediately trigger another resize (no thrashing)
    private fun shrinkIfNeeded() {
        if (size > 0 && size <= capacity / 4 && capacity / 2 >= minCapacity) {
            resize(capacity / 2)
        }
    }

    // O(n): copies all elements into a new array (equivalent to data.copyOf(newCapacity))
    private fun resize(newCapacity: Int) {
        val newData = arrayOfNulls<Any?>(newCapacity)
        for (i in 0 until size) {
            newData[i] = data[i]
        }
        data = newData
    }

    private fun checkIndex(index: Int) {
        if (index !in 0..<size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }
}
