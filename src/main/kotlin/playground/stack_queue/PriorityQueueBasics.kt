package playground.stack_queue

import java.util.PriorityQueue

/*
PriorityQueue: a binary heap (java.util.PriorityQueue)
PriorityQueue<Int>()                          min-heap: peek() is the smallest
PriorityQueue<Int>(reverseOrder())            max-heap
PriorityQueue(compareBy<Task> { it.cost })    by a key, compareBy({ }, { }) for ties
pq.add(x), pq.poll(), pq.peek()               poll() / peek() return null on an empty queue
pq.size, pq.isEmpty()

When to reach for it: the k largest / smallest, merge k sorted lists, Dijkstra, scheduling ("always take the
cheapest next").

Kotlin pitfalls:
- Iterating a PriorityQueue (for, toList, println) does not give sorted order, only poll() does.
- poll() returns a nullable type in Kotlin (T!), so pq.poll() + 1 compiles but crashes on an empty queue.
- PriorityQueue(k, comparator) takes an initial capacity, not a maximum size. Keep the size yourself.

n: number of elements, k: heap size
T: O(log n) for add / poll, O(1) for peek, O(n) to build from a collection
S: O(n)
 */

// the k largest numbers, largest first: a min-heap of size k keeps the k largest so far, O(n log k)
fun kLargest(numbers: IntArray, k: Int): List<Int> {
    if (k <= 0) return emptyList()
    val heap = PriorityQueue<Int>()
    for (x in numbers) {
        heap.add(x)
        if (heap.size > k) {
            heap.poll()
        }
    }
    return heap.sortedDescending()
}

// all values in sorted order, by polling the heap
fun heapSort(numbers: List<Int>): List<Int> {
    val heap = PriorityQueue(numbers)
    val result = mutableListOf<Int>()
    while (heap.isNotEmpty()) {
        result.add(heap.poll())
    }
    return result
}

// merges sorted lists into one sorted list, the heap holds one (value, list, index) per list
fun mergeSorted(lists: List<List<Int>>): List<Int> {
    val heap = PriorityQueue(compareBy<Triple<Int, Int, Int>> {
        it.first
    })
    for ((i, list) in lists.withIndex()) {
        if (list.isNotEmpty()) {
            heap.add(Triple(list[0], i, 0))
        }
    }
    val result = mutableListOf<Int>()
    while (heap.isNotEmpty()) {
        val (value, listIndex, index) = heap.poll()
        result.add(value)
        if (index + 1 < lists[listIndex].size) {
            heap.add(Triple(lists[listIndex][index + 1], listIndex, index + 1))
        }
    }
    return result
}
