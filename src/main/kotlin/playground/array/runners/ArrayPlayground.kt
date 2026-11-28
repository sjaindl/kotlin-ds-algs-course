package playground.array.runners

import playground.array.deepCopy
import playground.array.grid
import playground.array.indexOfMax
import playground.array.insertionPoint
import playground.array.longSum
import playground.array.prefixSums
import playground.array.prefixSumsStdlib
import playground.array.rangeSum
import playground.array.sameGrid

fun main() {
    println("Creating arrays")
    println("  IntArray(3) = ${IntArray(3).contentToString()}, IntArray(3) { it * it } = ${IntArray(3) { it * it }.contentToString()}")
    val a = intArrayOf(1, 2)
    val b = intArrayOf(1, 2)
    println("  a == b: ${a == b}, a.contentEquals(b): ${a.contentEquals(b)}")
    val board = grid(2, 3, value = 7)
    val copy = board.deepCopy()
    copy[0][0] = 0
    println("  grid(2, 3, 7) = ${board.contentDeepToString()}, deepCopy changed: ${copy.contentDeepToString()}")
    println("  sameGrid(board, copy) = ${sameGrid(board, copy)}")
    println()

    val numbers = intArrayOf(5, 1, 4, 2)
    println("Array operations on ${numbers.contentToString()}")
    println("  sum = ${numbers.sum()}, max = ${numbers.max()}, indexOfMax = ${indexOfMax(numbers)}")
    println("  sorted() = ${numbers.sorted()}, array still ${numbers.contentToString()}")
    numbers.sort()
    println("  after sort() = ${numbers.contentToString()}, insertionPoint(3) = ${insertionPoint(numbers, 3)}")
    println("  longSum([MAX, MAX]) = ${longSum(intArrayOf(Int.MAX_VALUE, Int.MAX_VALUE))}, sum() = ${intArrayOf(Int.MAX_VALUE, Int.MAX_VALUE).sum()}")
    println()

    val values = intArrayOf(3, 1, 4)
    val prefix = prefixSums(values)
    println("Running totals on ${values.contentToString()}")
    println("  prefixSums = ${prefix.contentToString()}, runningFold = ${prefixSumsStdlib(values)}")
    println("  rangeSum(1, 3) = ${rangeSum(prefix, 1, 3)}")
}
