package playground.stack_queue.runners

import playground.stack_queue.distances
import playground.stack_queue.heapSort
import playground.stack_queue.isBalanced
import playground.stack_queue.kLargest
import playground.stack_queue.mergeSorted
import playground.stack_queue.windowMax
import java.util.PriorityQueue

fun main() {
    println("ArrayDeque")
    val stack = ArrayDeque<Int>()
    stack.addLast(1)
    stack.addLast(2)
    stack.addLast(3)
    println("  stack after push 1, 2, 3: removeLast() = ${stack.removeLast()}, queue-style removeFirst() = ${stack.removeFirst()}")
    println("  isBalanced(\"([]{})\") = ${isBalanced("([]{})")}, isBalanced(\"(]\") = ${isBalanced("(]")}")
    val graph = mapOf(1 to listOf(2, 3), 2 to listOf(4), 3 to listOf(4), 4 to listOf(5))
    println("  distances($graph, 1) = ${distances(graph, 1)}")
    println("  windowMax([1, 3, -1, -3, 5, 3, 6, 7], 3) = ${windowMax(intArrayOf(1, 3, -1, -3, 5, 3, 6, 7), 3)}")
    println()

    println("PriorityQueue")
    val heap = PriorityQueue(listOf(5, 1, 4, 2, 3))
    println("  heap iterates as ${heap.toList()}, peek() = ${heap.peek()} (iteration is not sorted)")
    println("  heapSort([5, 1, 4, 2, 3]) = ${heapSort(listOf(5, 1, 4, 2, 3))}")
    println("  kLargest([3, 2, 1, 5, 6, 4], 2) = ${kLargest(intArrayOf(3, 2, 1, 5, 6, 4), 2)}")
    println("  mergeSorted([[1, 4, 5], [1, 3, 4], [2, 6]]) = ${mergeSorted(listOf(listOf(1, 4, 5), listOf(1, 3, 4), listOf(2, 6)))}")
}
