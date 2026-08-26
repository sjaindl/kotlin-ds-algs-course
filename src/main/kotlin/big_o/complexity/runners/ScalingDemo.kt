package big_o.complexity.runners

import big_o.complexity.canReachTarget
import big_o.complexity.countTriplets
import big_o.complexity.reset
import big_o.complexity.shortestRoute
import big_o.complexity.steps

// Shows how the amount of work explodes as the input grows — great for
// seeing O(n^3), O(2^n) and O(n!) in action.
fun main() {
    for (n in listOf(10, 50, 100, 200)) {
        val nums = IntArray(n) {
            it + 1
        }
        reset()
        val ms = measure {
            countTriplets(nums, target = -1)   // -1 is never hit -> full n^3 scan
        }
        println("Count triplets, n=$n   →  $steps steps  (${ms}ms)")
    }

    for (n in listOf(10, 20, 25, 30)) {
        reset()
        val nums = IntArray(n) {
            it + 1
        }
        val ms = measure {
            canReachTarget(nums, target = -1)
        }
        println("Subset sum, n=$n       →  $steps calls  (${ms}ms)")
    }

    for (n in listOf(5, 8, 10, 11)) {
        val d = Array(n) { i ->
            IntArray(n) { j ->
                if (i == j) 0 else (i + j) % 7 + 1
            }
        }
        reset()
        val ms = measure {
            shortestRoute(d, (1 until n).toMutableList())
        }
        println("Shortest route, n=$n   →  $steps routes  (${ms}ms)")
    }
}

private inline fun measure(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}
