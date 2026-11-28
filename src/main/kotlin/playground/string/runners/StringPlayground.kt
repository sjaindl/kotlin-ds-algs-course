package playground.string.runners

import playground.string.addBinary
import playground.string.balancedParentheses
import playground.string.binary
import playground.string.decimals
import playground.string.digitSum
import playground.string.distinctChars
import playground.string.distinctWindows
import playground.string.isCleanPalindrome
import playground.string.isPalindrome
import playground.string.normalize
import playground.string.parseInts
import playground.string.replaceAt
import playground.string.reverseWords
import playground.string.row
import playground.string.shiftLetters
import playground.string.withThousands
import playground.string.words

fun main() {
    val sentence = "  the sky   is blue "
    println("String basics on \"$sentence\"")
    println("  split(\" \") = ${sentence.split(" ")}")
    println("  words = ${words(sentence)}, reverseWords = \"${reverseWords(sentence)}\"")
    println("  \"abc\".take(10) = \"${"abc".take(10)}\", \"42\".padStart(5, '0') = ${"42".padStart(5, '0')}")
    println("  isPalindrome(\"racecar\") = ${isPalindrome("racecar")}")
    println()

    val phrase = "A man, a plan, a canal: Panama"
    println("Characters")
    println("  'c' - 'a' = ${'c' - 'a'}, 'a' + 2 = ${'a' + 2}, '7'.code = ${'7'.code}, '7'.digitToInt() = ${'7'.digitToInt()}")
    println("  normalize(\"$phrase\") = ${normalize(phrase)}, isCleanPalindrome = ${isCleanPalindrome(phrase)}")
    println("  shiftLetters(\"xyz\", 3) = ${shiftLetters("xyz", 3)}, digitSum(\"a1b22\") = ${digitSum("a1b22")}")
    println("  Char(65) = ${Char(65)}, distinctChars(\"banana\") = ${distinctChars("banana")}")
    println()

    println("Converting and slicing")
    println("  \"4x\".toIntOrNull() = ${"4x".toIntOrNull()}, \"1010\".toInt(2) = ${"1010".toInt(2)}")
    println("  binary(5, 8) = ${binary(5, 8)}, addBinary(\"1011\", \"11\") = ${addBinary("1011", "11")}")
    println("  \"abcdefg\".chunked(3) = ${"abcdefg".chunked(3)}, \"abcd\".windowed(3) = ${"abcd".windowed(3)}")
    println("  decimals(2.0 / 3, 3) = ${decimals(2.0 / 3, 3)}, row(\"sum\", 12, 6) = \"${row("sum", 12, 6)}\"")
    println("  distinctWindows(\"abab\", 2) = ${distinctWindows("abab", 2)}, parseInts(\"1, x, 3\") = ${parseInts("1, x, 3")}")
    println()

    println("Editing a StringBuilder")
    println("  balancedParentheses(3) = ${balancedParentheses(3)}")
    println("  replaceAt(\"hello\", [0, 4], '*') = ${replaceAt("hello", listOf(0, 4), '*')}, withThousands(1234567) = ${withThousands(1234567)}")
}
