package playground.stack_queue

import java.util.PriorityQueue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class StackQueuePlaygroundTest {

    @Test
    fun `deque as stack and queue`() {
        val deque = ArrayDeque(listOf(1, 2, 3))
        assertEquals(3, deque.removeLast()) // stack pop
        assertEquals(1, deque.removeFirst()) // queue poll
        assertEquals(2, deque[0]) // indexing works
        deque.removeFirst()
        assertNull(deque.removeFirstOrNull()) // empty
        assertFailsWith<NoSuchElementException> {
            deque.removeLast()
        }
    }

    @Test
    fun `balanced brackets`() {
        assertTrue(isBalanced("([]{})"))
        assertTrue(isBalanced("")) // nothing to close
        assertTrue(isBalanced("a(b)c")) // other characters are ignored
        assertFalse(isBalanced("(]"))
        assertFalse(isBalanced("((")) // never closed
        assertFalse(isBalanced(")(")) // closed before opened
    }

    @Test
    fun `bfs distances`() {
        val graph = mapOf(1 to listOf(2, 3), 2 to listOf(4), 3 to listOf(4), 4 to listOf(5))
        assertEquals(mapOf(1 to 0, 2 to 1, 3 to 1, 4 to 2, 5 to 3), distances(graph, 1))
        assertEquals(mapOf(7 to 0), distances(graph, 7)) // node without edges
        assertEquals(mapOf(1 to 0, 2 to 1), distances(mapOf(1 to listOf(2), 2 to listOf(1)), 1)) // cycle
    }

    @Test
    fun `window max`() {
        assertEquals(listOf(3, 3, 5, 5, 6, 7), windowMax(intArrayOf(1, 3, -1, -3, 5, 3, 6, 7), 3))
        assertEquals(listOf(1, 2), windowMax(intArrayOf(1, 2), 1)) // window of 1: every element
        assertEquals(listOf(5), windowMax(intArrayOf(5, 1, 3), 3)) // one window
        assertEquals(emptyList(), windowMax(intArrayOf(1, 2), 3)) // window larger than the array
        assertEquals(listOf(2, 2), windowMax(intArrayOf(2, 2, 2), 2)) // duplicates
    }

    @Test
    fun `k largest`() {
        assertEquals(listOf(6, 5), kLargest(intArrayOf(3, 2, 1, 5, 6, 4), 2))
        assertEquals(listOf(3, 3), kLargest(intArrayOf(3, 3, 1), 2)) // duplicates count
        assertEquals(listOf(2, 1), kLargest(intArrayOf(1, 2), 5)) // k larger than the array
        assertEquals(emptyList(), kLargest(intArrayOf(1, 2), 0))
    }

    @Test
    fun `heap sort`() {
        assertEquals(listOf(1, 2, 3, 4, 5), heapSort(listOf(5, 1, 4, 2, 3)))
        assertEquals(listOf(-1, -1, 0), heapSort(listOf(0, -1, -1)))
        assertEquals(emptyList(), heapSort(emptyList()))
    }

    @Test
    fun `max heap`() {
        val heap = PriorityQueue<Int>(reverseOrder())
        heap.addAll(listOf(1, 5, 3))
        assertEquals(5, heap.poll())
        assertEquals(3, heap.poll())
        heap.poll()
        assertNull(heap.poll()) // empty: null, no exception
    }

    @Test
    fun `merge sorted`() {
        assertEquals(listOf(1, 1, 2, 3, 4, 4, 5, 6), mergeSorted(listOf(listOf(1, 4, 5), listOf(1, 3, 4), listOf(2, 6))))
        assertEquals(listOf(1, 2), mergeSorted(listOf(emptyList(), listOf(1, 2), emptyList()))) // empty lists
        assertEquals(emptyList(), mergeSorted(emptyList()))
    }
}
