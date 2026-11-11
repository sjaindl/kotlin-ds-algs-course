package toolbox.string.runners

import toolbox.string.charCounts
import toolbox.string.countKey
import toolbox.string.expand
import toolbox.string.groupByKey
import toolbox.string.hasLetter
import toolbox.string.indicesByChar
import toolbox.string.isAnagram
import toolbox.string.letterCounts
import toolbox.string.letterMask
import toolbox.string.runs
import toolbox.string.sortedKey
import toolbox.string.substringCount
import toolbox.string.substringsBetween

fun main() {
    println("Frequency counting")
    println("  letterCounts(\"cabbba\") = ${letterCounts("cabbba").take(4)} ...")
    println("  charCounts(\"tree\") = ${charCounts("tree")}")
    println()

    val words = listOf("eat", "tea", "tan", "ate", "nat", "bat")
    println("Canonical key on $words")
    println("  sortedKey: ${words.map { sortedKey(it) }}")
    println("  groupByKey(sortedKey) = ${groupByKey(words, ::sortedKey)}")
    println("  groupByKey(countKey)  = ${groupByKey(words, ::countKey)}")
    val counts1 = letterCounts("eat")
    val counts2 = letterCounts("tea")
    println("  IntArray keys equal: ${counts1 == counts2}, as List: ${counts1.toList() == counts2.toList()}")
    println("  isAnagram(\"anagram\", \"nagaram\") = ${isAnagram("anagram", "nagaram")}")
    println()

    println("Set of letters as bit mask")
    println("  letterMask(\"cabbba\") = ${letterMask("cabbba").toString(2)}, letterMask(\"abbccc\") = ${letterMask("abbccc").toString(2)}")
    println("  letterMask(\"uau\") = ${letterMask("uau").toString(2)}, hasLetter(_, 'a') = ${hasLetter(letterMask("uau"), 'a')}, hasLetter(_, 's') = ${hasLetter(letterMask("uau"), 's')}")
    println("  distinct letters in \"leetcode\": ${letterMask("leetcode").countOneBits()}")
    println()

    val sorted = "tree".toCharArray().sorted().joinToString(separator = "")
    println("Building strings")
    println("  runs(\"$sorted\") = ${runs(sorted)}")
    println("  expand([e to 2, t to 1, r to 1]) = ${expand(listOf('e' to 2, 't' to 1, 'r' to 1))}")
    println()

    println("Contribution technique")
    println("  substringCount(3) = ${substringCount(3)}, substringCount(10^5) = ${substringCount(100_000)}")
    println("  substringsBetween(-1, 1, 3) = ${substringsBetween(-1, 1, 3)} (index 1 of \"ABC\", no boundaries)")
    val s = "LEETCODE"
    val indices = indicesByChar(s)
    println("  indicesByChar(\"$s\") = $indices")
    var total = 0L
    indices.values.forEach { list ->
        list.forEachIndexed { position, index ->
            val prev = if (position == 0) -1 else list[position - 1]
            val next = if (position == list.size - 1) s.length else list[position + 1]
            total += substringsBetween(prev, index, next)
        }
    }
    println("  sum of substringsBetween(prev, index, next) = $total (unique letters of all substrings)")
}
