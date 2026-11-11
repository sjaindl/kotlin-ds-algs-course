package questions.string.close_strings

import questions.string.close_strings.solution.closeStringsBitMask
import questions.string.close_strings.solution.closeStringsFrequencyArray
import questions.string.close_strings.solution.closeStringsHashMap
import kotlin.test.Test
import kotlin.test.assertEquals

class CloseStringsTest {

    private val alphabet = ('a'..'z').joinToString("")

    private val cases = listOf(
        ("abc" to "bca") to true, // example 1
        ("a" to "aa") to false, // example 2
        ("cabbba" to "abbccc") to true, // example 3
        ("abc" to "abc") to true, // identical words
        ("a" to "b") to false, // same counts, but different letters
        ("aab" to "abb") to true, // operation 2 swaps the counts of a and b
        ("aaabbc" to "bbbaac") to true, // operation 2 on longer words
        ("ab" to "aabb") to false, // same letters, different lengths
        ("uau" to "ssx") to false, // same sorted counts, but different letters: breaks a counts-only check
        ("aaab" to "aabb") to false, // same letters and length, but counts [1, 3] vs [2, 2]: breaks a letters-only check
        ("abbccc" to "abbbcc") to true, // counts of b and c swapped
        ("aaaa" to "aaaa") to true, // all equal
        (alphabet to alphabet.reversed()) to true, // all 26 letters, sets bit 25 for 'z'
        (alphabet to alphabet.dropLast(1) + "y") to false, // 'z' missing in word2: masks differ only in bit 25
        ("a".repeat(30_000) + "b".repeat(70_000) to "b".repeat(30_000) + "a".repeat(70_000)) to true, // constraint bound
        ("a".repeat(50_000) + "b".repeat(50_000) to "a".repeat(49_999) + "b".repeat(50_001)) to false // constraint bound
    )

    private fun verify(solution: (String, String) -> Boolean) {
        cases.forEach { (words, expected) ->
            val (word1, word2) = words
            val name = if (word1.length > 30) "words of length ${word1.length}" else "word1 = \"$word1\", word2 = \"$word2\""
            assertEquals(expected, solution(word1, word2), name)
        }
    }

    @Test
    fun `starter solution`() {
        verify(::closeStrings)
    }

    @Test
    fun `hash map solution`() {
        verify(::closeStringsHashMap)
    }

    @Test
    fun `frequency array solution`() {
        verify(::closeStringsFrequencyArray)
    }

    @Test
    fun `bit mask solution`() {
        verify(::closeStringsBitMask)
    }
}
