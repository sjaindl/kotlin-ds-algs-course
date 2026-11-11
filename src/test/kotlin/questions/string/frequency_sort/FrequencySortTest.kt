package questions.string.frequency_sort

import questions.string.frequency_sort.solution.frequencySortBruteforce
import questions.string.frequency_sort.solution.frequencySortBucket
import questions.string.frequency_sort.solution.frequencySortHashMap
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FrequencySortTest {

    private val chars = buildList {
        addAll('a'..'z')
        addAll('A'..'Z')
        addAll('0'..'9')
    }

    private val cases = listOf(
        "tree", // example 1
        "cccaaa", // example 2: tie between 'c' and 'a'
        "Aabb", // example 3: 'A' and 'a' are different
        "z", // single character
        "aaaa", // all equal: one block with count n, the last bucket
        "abcdef", // all distinct: every count is 1
        "a1b2b22", // digits and letters
        "abab", // tie with interleaved characters, must become "aabb" or "bbaa"
        "abcabcabcd", // several ties after the most frequent block
        Random(42).let { random -> String(CharArray(500_000) { chars[random.nextInt(chars.size)] }) } // constraint bound: 5 * 10^5
    )

    // the counts of every block of equal characters in result, in order
    private fun blocks(result: String): List<Pair<Char, Int>> {
        val blocks = mutableListOf<Pair<Char, Int>>()
        for (char in result) {
            if (blocks.isNotEmpty() && blocks.last().first == char) {
                blocks[blocks.size - 1] = char to blocks.last().second + 1
            } else {
                blocks.add(char to 1)
            }
        }
        return blocks
    }

    // ties may come in any order, so check the rules instead of comparing with one expected string
    private fun verify(solution: (String) -> String) {
        cases.forEach { s ->
            val result = solution(s)
            val name = if (s.length > 20) "s of length ${s.length}" else "s = \"$s\""
            assertEquals(s.toList().sorted(), result.toList().sorted(), "$name: not the same characters, got \"${result.take(50)}\"")

            val blocks = blocks(result)
            assertEquals(blocks.map { it.first }.toSet().size, blocks.size, "$name: equal characters not together, got \"${result.take(50)}\"")
            assertTrue(blocks.zipWithNext().all { (a, b) -> a.second >= b.second }, "$name: counts increase, got \"${result.take(50)}\"")
        }
    }

    @Test
    fun `starter solution`() {
        verify(::frequencySort)
    }

    @Test
    fun `bruteforce solution`() {
        verify(::frequencySortBruteforce)
    }

    @Test
    fun `hash map solution`() {
        verify(::frequencySortHashMap)
    }

    @Test
    fun `bucket solution`() {
        verify(::frequencySortBucket)
    }
}
