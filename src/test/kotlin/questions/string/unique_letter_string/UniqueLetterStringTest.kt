package questions.string.unique_letter_string

import questions.string.unique_letter_string.solution.uniqueLetterStringBruteforce
import questions.string.unique_letter_string.solution.uniqueLetterStringCharSplit
import questions.string.unique_letter_string.solution.uniqueLetterStringNextOccurrences
import kotlin.test.Test
import kotlin.test.assertEquals

class UniqueLetterStringTest {

    private val alphabet = ('A'..'Z').joinToString("")

    private val cases = listOf(
        "ABC" to 10, // example 1
        "ABA" to 8, // example 2
        "LEETCODE" to 92, // example 3
        "Z" to 1, // single character
        "AAAA" to 4, // all equal: only the substrings of length 1 have a unique character
        "ABAB" to 12, // interleaved repeats
        alphabet to 3276 // all 26 letters distinct: sum of all substring lengths, 26 * 27 * 28 / 6
    )

    // too big for the bruteforce: O(n³) memory and a recursion depth of n
    private val largeCases = listOf(
        "A".repeat(100_000) to 100_000, // one character n times, O(n²) with ArrayList.remove on the head
        String(CharArray(100_000) { alphabet[it % 26] }) to 67_583_100 // constraint bound, still fits into an Int
    )

    private fun verify(solution: (String) -> Int, large: Boolean = true) {
        (if (large) cases + largeCases else cases).forEach { (s, expected) ->
            val name = if (s.length > 30) "s of length ${s.length}" else "s = \"$s\""
            assertEquals(expected, solution(s), name)
        }
    }

    @Test
    fun `starter solution`() {
        verify(::uniqueLetterString)
    }

    @Test
    fun `bruteforce solution`() {
        verify(::uniqueLetterStringBruteforce, large = false)
    }

    @Test
    fun `next occurrences solution`() {
        verify(::uniqueLetterStringNextOccurrences)
    }

    @Test
    fun `char split solution`() {
        verify(::uniqueLetterStringCharSplit)
    }
}
