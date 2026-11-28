package playground.hashmap.runners

import playground.hashmap.common
import playground.hashmap.decrement
import playground.hashmap.firstRepeated
import playground.hashmap.inverted
import playground.hashmap.isSubset
import playground.hashmap.nextLarger
import playground.hashmap.onlyInOne
import playground.hashmap.positions
import playground.hashmap.takeSmallest
import playground.hashmap.valueAt
import playground.hashmap.valuesBetween
import playground.hashmap.wordCount
import java.util.TreeMap
import java.util.TreeSet

fun main() {
    val words = listOf("to", "be", "or", "not", "to", "be")
    println("Maps on $words")
    println("  wordCount = ${wordCount(words)}")
    println("  positions = ${positions(words)}")
    val window = mutableMapOf("a" to 2, "b" to 1)
    window.decrement("a")
    window.decrement("b")
    println("  {a=2, b=1} after decrement(a), decrement(b) = $window")
    println("  {a=1, b=2}.inverted() = ${mapOf("a" to 1, "b" to 2).inverted()}")
    println("  emptyMap()[\"x\"] = ${emptyMap<String, Int>()["x"]}, getOrDefault = ${emptyMap<String, Int>().getOrDefault("x", 0)}")
    println()

    println("Sets")
    println("  firstRepeated([3, 1, 4, 1, 5, 3]) = ${firstRepeated(listOf(3, 1, 4, 1, 5, 3))}")
    println("  common([1, 2, 2, 3], [2, 3, 4]) = ${common(listOf(1, 2, 2, 3), listOf(2, 3, 4))}")
    println("  onlyInOne({1, 2, 3}, {2, 3, 4}) = ${onlyInOne(setOf(1, 2, 3), setOf(2, 3, 4))}")
    println("  isSubset([1, 2], [3, 2, 1]) = ${isSubset(listOf(1, 2), listOf(3, 2, 1))}")
    println()

    println("Sorted maps and sets")
    val set = TreeSet(listOf(10, 20, 30, 40))
    println("  $set: nextLarger(20) = ${nextLarger(set, 20)}, floor(25) = ${set.floor(25)}, ceiling(45) = ${set.ceiling(45)}")
    val history = TreeMap(mapOf(1 to "v1", 5 to "v2", 9 to "v3"))
    println("  history $history: valueAt(7) = ${valueAt(history, 7)}, valueAt(0) = ${valueAt(history, 0)}")
    println("  valuesBetween(2, 9) = ${valuesBetween(history, 2, 9)}")
    println("  takeSmallest(2) = ${takeSmallest(set, 2)}, set left = $set")
}
