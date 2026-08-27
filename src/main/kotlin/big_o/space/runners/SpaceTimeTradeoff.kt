package big_o.space.runners

import big_o.space.reset
import big_o.space.steps
import big_o.space.twoSumBruteForce
import big_o.space.twoSumWithMap

fun main() {
    for (n in listOf(1_000, 10_000, 50_000)) {
        val nums = IntArray(n) { it }
        val target = 2 * n // no solution — worst case

        reset()
        twoSumBruteForce(nums = nums, target = target)
        val brute = steps

        reset()
        twoSumWithMap(nums = nums, target = target)
        val map = steps

        println("n = $n → brute force: $brute steps, 0 extra memory")
        println("          hash map:    $map steps, $map entries stored")
        println()
    }
}
