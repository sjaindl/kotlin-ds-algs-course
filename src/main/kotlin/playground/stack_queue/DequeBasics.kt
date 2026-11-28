package playground.stack_queue

/*
ArrayDeque: stack, queue and deque in one (kotlin.collections.ArrayDeque, a circular buffer)
                 add            remove              peek
stack (LIFO)     addLast(x)     removeLast()        last()
queue (FIFO)     addLast(x)     removeFirst()       first()
deque            addFirst(x)    removeFirst() / removeLast()
removeFirstOrNull(), lastOrNull(), ...       null instead of NoSuchElementException on an empty deque

Why not java.util.Stack: it extends Vector, so every call is synchronized (slow), and it also lets you insert in
the middle. java.util.LinkedList works as a queue but allocates a node per element. ArrayDeque is the default.

When to reach for it: matching brackets, undo, DFS without recursion (stack), BFS and level order (queue),
sliding window maximum (deque).

Kotlin pitfall: Kotlin's ArrayDeque and java.util.ArrayDeque have the same name. Without an import you get the
Kotlin one, which also supports indexing: deque[i].

n: number of elements
T: O(1) amortized for add / remove / peek at both ends
S: O(n)
 */

// true if every bracket is closed in the right order: "([]{})" -> true, "(]" -> false
fun isBalanced(s: String): Boolean {
    val pairs = mapOf(')' to '(', ']' to '[', '}' to '{')
    val stack = ArrayDeque<Char>()
    for (c in s) {
        when (c) {
            '(', '[', '{' -> stack.addLast(c)
            ')', ']', '}' -> if (stack.removeLastOrNull() != pairs[c]) return false
        }
    }
    return stack.isEmpty()
}

// the shortest number of steps from start to every reachable node, by breadth-first search
fun distances(graph: Map<Int, List<Int>>, start: Int): Map<Int, Int> {
    val distance = hashMapOf(start to 0)
    val queue = ArrayDeque<Int>()
    queue.addLast(start)
    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        for (next in graph[node].orEmpty()) {
            if (next !in distance) {
                distance[next] = distance.getValue(node) + 1
                queue.addLast(next)
            }
        }
    }
    return distance
}

// the maximum of every window of size k, a deque of indices with decreasing values
fun windowMax(numbers: IntArray, k: Int): List<Int> {
    val result = mutableListOf<Int>()
    val deque = ArrayDeque<Int>()
    for (i in numbers.indices) {
        if (deque.isNotEmpty() && deque.first() <= i - k) {
            deque.removeFirst()
        }
        while (deque.isNotEmpty() && numbers[deque.last()] <= numbers[i]) {
            deque.removeLast()
        }
        deque.addLast(i)
        if (i >= k - 1) {
            result.add(numbers[deque.first()])
        }
    }
    return result
}
