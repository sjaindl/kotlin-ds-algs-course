package playground.idioms.runners

import playground.idioms.Point
import playground.idioms.between
import playground.idioms.ceilDiv
import playground.idioms.clamp
import playground.idioms.countdown
import playground.idioms.distance
import playground.idioms.find
import playground.idioms.firstWithPrefix
import playground.idioms.grade
import playground.idioms.hasNegative
import playground.idioms.inBounds
import playground.idioms.isPowerOfTwo
import playground.idioms.lengthOrZero
import playground.idioms.linkedListOf
import playground.idioms.middle
import playground.idioms.minMax
import playground.idioms.neighbours
import playground.idioms.nonNegativeIndices
import playground.idioms.pick
import playground.idioms.positiveOrNull
import playground.idioms.safeDivide
import playground.idioms.sample
import playground.idioms.singleNumber
import playground.idioms.summary
import playground.idioms.swapWithAlso
import playground.idioms.values
import kotlin.random.Random

fun main() {
    println("Null safety")
    println("  lengthOrZero(null) = ${lengthOrZero(null)}, positiveOrNull(\"42\") = ${positiveOrNull("42")}, positiveOrNull(\"-3\") = ${positiveOrNull("-3")}")
    println("  safeDivide(7, 2) = ${safeDivide(7, 2)}, safeDivide(7, 0) = ${safeDivide(7, 0)}")
    println("  firstWithPrefix([apple, banana], \"ba\") = ${firstWithPrefix(listOf("apple", "banana"), "ba")}")
    println()

    println("Scope functions")
    println("  linkedListOf(1, 2, 3).values() = ${linkedListOf(1, 2, 3).values()}")
    val numbers = intArrayOf(1, 2, 3)
    numbers.swapWithAlso(0, 2)
    println("  swapWithAlso(0, 2) on [1, 2, 3] = ${numbers.contentToString()}")
    println("  summary([4, 1, 7]) = ${summary(listOf(4, 1, 7))}")
    println()

    println("Data classes and pairs")
    val p = Point(2, 3)
    val (x, y) = p
    println("  $p destructured: x = $x, y = $y, copy(y = 0) = ${p.copy(y = 0)}, equal to Point(2, 3): ${p == Point(2, 3)}")
    println("  neighbours = ${neighbours(p)}")
    println("  minMax([4, 1, 7]) = ${minMax(listOf(4, 1, 7))}, grade(80) = ${grade(80)}")
    println()

    println("Ranges and loops")
    val grid = arrayOf(intArrayOf(1, 2), intArrayOf(3, 4))
    println("  find(grid, 3) = ${find(grid, 3)}, inBounds(2, 0, 2, 2) = ${inBounds(2, 0, 2, 2)}")
    println("  nonNegativeIndices([3, -1, 4, -5]) = ${nonNegativeIndices(listOf(3, -1, 4, -5))}, hasNegative = ${hasNegative(listOf(3, -1, 4, -5))}")
    println("  countdown(10, 3) = ${countdown(10, 3)}, (5..0).toList() = ${(5..0).toList()}, clamp(15, 0, 9) = ${clamp(15, 0, 9)}")
    println()

    println("Numbers")
    println("  Int.MAX_VALUE + 1 = ${Int.MAX_VALUE + 1}, -7 % 3 = ${-7 % 3}, (-7).mod(3) = ${(-7).mod(3)}")
    println("  middle(2_000_000_000, 2_100_000_000) = ${middle(2_000_000_000, 2_100_000_000)}, isPowerOfTwo(64) = ${isPowerOfTwo(64)}")
    println("  singleNumber([4, 1, 2, 1, 2]) = ${singleNumber(intArrayOf(4, 1, 2, 1, 2))}, distance(MIN, MAX) = ${distance(Int.MIN_VALUE, Int.MAX_VALUE)}")
    println("  ceilDiv(7, 3) = ${ceilDiv(7, 3)}")
    println()

    val seeded = Random(42)
    val items = listOf("a", "b", "c", "d", "e")
    println("Random numbers, seeded with 42")
    println("  pick($items) = ${pick(items, seeded)}, between(1, 6) = ${between(1, 6, seeded)}")
    println("  sample($items, 2) = ${sample(items, 2, seeded)}, shuffled = ${items.shuffled(seeded)}")
}
