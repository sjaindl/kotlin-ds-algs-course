package playground.collections.runners

import playground.collections.Person
import playground.collections.at
import playground.collections.byFirstLetter
import playground.collections.byLengthThenAlphabet
import playground.collections.collatz
import playground.collections.differences
import playground.collections.evenOdd
import playground.collections.firstSquareAbove
import playground.collections.frequencies
import playground.collections.lengths
import playground.collections.mostFrequent
import playground.collections.move
import playground.collections.oldestFirst
import playground.collections.popOrNull
import playground.collections.powersOfTwo
import playground.collections.product
import playground.collections.sortIntervals
import playground.collections.topK
import playground.collections.transpose

fun main() {
    println("Lists")
    val list = mutableListOf("a", "b", "c", "d")
    list.move(0, 3)
    println("  move(0, 3) on [a, b, c, d] = $list")
    val popped = list.popOrNull()
    println("  popOrNull() = $popped, then $list")
    println("  at(10, \"?\") = ${list.at(10, "?")}, List(3) { it * 2 } = ${List(3) { it * 2 }}")
    println()

    val numbers = listOf(1, 4, 9, 16)
    println("Transformations on $numbers")
    println("  differences = ${differences(numbers)}, evenOdd = ${evenOdd(numbers)}")
    println("  chunked(3) = ${numbers.chunked(3)}, windowed(2) = ${numbers.windowed(2)}")
    println("  lengths([hi, you]) = ${lengths(listOf("hi", "you"))}")
    println("  transpose([[1, 2, 3], [4, 5, 6]]) = ${transpose(listOf(listOf(1, 2, 3), listOf(4, 5, 6)))}")
    println()

    val letters = listOf("b", "a", "b", "c", "a", "b")
    println("Aggregations on $letters")
    println("  frequencies = ${frequencies(letters)}, mostFrequent = ${mostFrequent(letters)}")
    println("  byFirstLetter([ant, bee, ape]) = ${byFirstLetter(listOf("ant", "bee", "ape"))}")
    println("  product([1, 2, 3, 4]) = ${product(listOf(1, 2, 3, 4))}, emptyList().all { false } = ${emptyList<Int>().all { false }}")
    println()

    println("Sorting")
    val people = listOf(Person("Bo", 30), Person("Al", 25), Person("Ann", 30))
    println("  oldestFirst = ${oldestFirst(people)}")
    val intervals = arrayOf(intArrayOf(3, 4), intArrayOf(1, 5), intArrayOf(1, 2))
    sortIntervals(intervals)
    println("  sortIntervals = ${intervals.contentDeepToString()}")
    println("  byLengthThenAlphabet([pear, fig, kiwi, apple]) = ${byLengthThenAlphabet(listOf("pear", "fig", "kiwi", "apple"))}")
    println("  topK([5, 1, 9, 3], 2) = ${topK(listOf(5, 1, 9, 3), 2)}")
    println()

    println("Sequences")
    println("  powersOfTwo(6) = ${powersOfTwo(6)}, collatz(6) = ${collatz(6)}")
    println("  firstSquareAbove([1, 2, 3, 4, 5], 5) = ${firstSquareAbove(listOf(1, 2, 3, 4, 5), 5)}")
}
