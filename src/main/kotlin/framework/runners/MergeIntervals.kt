package framework.runners

import framework.mergeBruteforce
import framework.mergeBySorting

private fun format(intervals: Array<IntArray>): String =
    intervals.joinToString(prefix = "[", postfix = "]") { "[${it[0]},${it[1]}]" }

fun main() {
    val intervals = arrayOf(
        intArrayOf(1, 3),
        intArrayOf(2, 6),
        intArrayOf(8, 10),
        intArrayOf(15, 18)
    )

    println("Input:      ${format(intervals)}")
    println("Bruteforce: ${format(mergeBruteforce(intervals))}")
    println("Sorting:    ${format(mergeBySorting(intervals))}")
}
