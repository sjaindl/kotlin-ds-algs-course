package big_o.space.runners

import big_o.space.sumRecursive
import big_o.space.sumTailRecursive

fun main() {
    // No array, list or visible allocation - still crashes with StackOverflowError
    // The n pending calls ARE the memory: O(n) space, hidden on the call stack.
    for (n in listOf(1_000, 10_000, 100_000)) {
        try {
            println("recursive      n = $n → ${sumRecursive(nums = IntArray(n) { 1 })}")
        } catch (e: StackOverflowError) {
            println("recursive      n = $n → StackOverflowError")
        }
    }

    println()

    // One word different: `tailrec` -> Kotlin builds a loop, the stack stays flat.
    for (n in listOf(1_000, 10_000, 100_000)) {
        println("tail-recursive n = $n → ${sumTailRecursive(nums = IntArray(n) { 1 })}")
    }
}
