package big_o.space

fun main() {
    println("=== Snippet 1: auxiliary and total space ===")
    auxiliaryAndTotalSpace()

    println()
    println("=== Snippet 2: the call stack is memory ===")
    callStack()

    println()
    println("=== Snippet 3: the trade-off, live ===")
    spaceTimeTradeoff()
}

private fun auxiliaryAndTotalSpace() {
    val nums = intArrayOf(1, 2, 3, 4, 5)

    // Same result, same runtime - but different space.
    val copy = reversedCopy(nums)
    println("reversedCopy   → ${copy.toList()}   (allocates a 2nd array: O(n) space)")

    reverseInPlace(nums)
    println("reverseInPlace → ${nums.toList()}   (swaps in place: O(1) space)")
}

private fun callStack() {
    // No array, no list, no visible allocation - yet it still crashes.
    // The n pending calls ARE the memory: O(n) space, hidden on the call stack.
    for (n in listOf(1_000, 10_000, 100_000)) {
        try {
            println("recursive      n = $n → ${sumRecursive(IntArray(n) { 1 })}")
        } catch (e: StackOverflowError) {
            println("recursive      n = $n → StackOverflowError")
        }
    }

    println()

    // One word different: `tailrec` -> Kotlin builds a loop, the stack stays flat.
    for (n in listOf(1_000, 10_000, 100_000)) {
        println("tail-recursive n = $n → ${sumTailRecursive(IntArray(n) { 1 })}")
    }
}

private fun spaceTimeTradeoff() {
    for (n in listOf(1_000, 10_000, 50_000)) {
        val nums = IntArray(n) { it }
        val target = 2 * n // no solution — worst case

        reset()
        twoSumBruteForce(nums, target)
        val brute = steps

        reset()
        twoSumWithMap(nums, target)
        val map = steps

        println("n = $n → brute force: $brute steps, 0 extra memory")
        println("          hash map:    $map steps, $map entries stored")
    }
}
