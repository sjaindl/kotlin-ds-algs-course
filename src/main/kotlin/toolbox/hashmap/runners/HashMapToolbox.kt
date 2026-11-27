package toolbox.hashmap.runners

import toolbox.hashmap.Point
import toolbox.hashmap.canonical
import toolbox.hashmap.countPoints
import toolbox.hashmap.crossPairs
import toolbox.hashmap.distinctUpToOrder
import toolbox.hashmap.firstUnique
import toolbox.hashmap.groupSets
import toolbox.hashmap.hasDuplicate
import toolbox.hashmap.indexOfEach
import toolbox.hashmap.indicesOfEach
import toolbox.hashmap.lruCache
import toolbox.hashmap.nearestKey
import toolbox.hashmap.pointKey
import toolbox.hashmap.swapRemove
import toolbox.hashmap.unorderedPair
import toolbox.hashmap.xOf
import toolbox.hashmap.yOf
import java.util.TreeMap

fun main() {
    val words = listOf("pear", "fig", "apple", "kiwi", "banana", "cherry", "date")
    println("Map and set types, inserting $words")
    println("  hashSetOf:    ${hashSetOf(*words.toTypedArray())} (bucket order)")
    println("  mutableSetOf: ${mutableSetOf(*words.toTypedArray())} (insertion order)")
    println("  sortedSetOf:  ${sortedSetOf(*words.toTypedArray())} (sorted)")
    println("  firstUnique([3, 1, 3, 2, 1]) = ${firstUnique(listOf(3, 1, 3, 2, 1))}")
    val prices = TreeMap(mapOf(10 to "ten", 20 to "twenty", 40 to "forty"))
    println("  nearestKey(${prices.keys}, 33) = ${nearestKey(prices, 33)}, nearestKey(_, 5) = ${nearestKey(prices, 5)}")
    val cache = lruCache<String, Int>(capacity = 2)
    cache["a"] = 1
    cache["b"] = 2
    cache["a"]
    cache["c"] = 3
    println("  lruCache(2): put a, put b, get a, put c -> ${cache.keys} (b was least recently used)")
    println()

    println("Custom classes as keys")
    println("  countPoints = ${countPoints(listOf(Point(1, 2), Point(1, 2), Point(3, 4)))}")
    val arrayKeys = hashMapOf(intArrayOf(1, 2) to "array")
    val listKeys = hashMapOf(listOf(1, 2) to "list")
    println("  IntArray key found again: ${arrayKeys[intArrayOf(1, 2)]}, List key: ${listKeys[listOf(1, 2)]}")
    val key = pointKey(-3, 7)
    println("  pointKey(-3, 7) = $key -> x = ${xOf(key)}, y = ${yOf(key)}")
    println()

    println("Canonical order")
    println("  unorderedPair(5, 2) = ${unorderedPair(5, 2)}, unorderedPair(2, 5) = ${unorderedPair(2, 5)}")
    val lists = listOf(listOf(1, 2, 3), listOf(3, 1, 2), listOf(2, 2))
    println("  canonical: ${lists.map { canonical(it) }}, distinctUpToOrder = ${distinctUpToOrder(lists)}")
    println()

    val letters = listOf("b", "a", "b")
    println("Index map on $letters")
    println("  indexOfEach = ${indexOfEach(letters)}, indicesOfEach = ${indicesOfEach(letters)}")
    println()

    val list = mutableListOf("a", "b", "c", "d")
    val removed = list.swapRemove(1)
    println("Swap remove")
    println("  [a, b, c, d].swapRemove(1) removed $removed -> $list")
    println()

    println("Set lookups")
    println("  hasDuplicate([1, 2, 3, 1]) = ${hasDuplicate(listOf(1, 2, 3, 1))}")
    println()

    val ideas = listOf("coffee", "donuts", "time", "toffee")
    val suffixes = groupSets(ideas, key = { it[0] }, value = { it.drop(1) })
    println("Group and multiply on $ideas")
    println("  suffixes by first letter: $suffixes")
    println("  crossPairs(t, c) = ${crossPairs(suffixes.getValue('t'), suffixes.getValue('c'))}, crossPairs(t, d) = ${crossPairs(suffixes.getValue('t'), suffixes.getValue('d'))}")
}
