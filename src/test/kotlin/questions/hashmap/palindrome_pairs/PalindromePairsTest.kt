package questions.hashmap.palindrome_pairs

import questions.hashmap.palindrome_pairs.solution.palindromePairsBruteforce
import questions.hashmap.palindrome_pairs.solution.palindromePairsHashMap
import kotlin.test.Test
import kotlin.test.assertEquals

class PalindromePairsTest {

    private val cases = listOf(
        arrayOf("abcd", "dcba", "lls", "s", "sssll") to
            listOf(listOf(0, 1), listOf(1, 0), listOf(3, 2), listOf(2, 4)), // example 1
        arrayOf("bat", "tab", "cat") to listOf(listOf(0, 1), listOf(1, 0)), // example 2
        arrayOf("a", "") to listOf(listOf(0, 1), listOf(1, 0)), // example 3
        arrayOf("abc") to listOf(), // single word
        arrayOf("", "a") to listOf(listOf(0, 1), listOf(1, 0)), // empty word first
        arrayOf("", "aba", "ab") to listOf(listOf(0, 1), listOf(1, 0), listOf(2, 1)), // empty word pairs only with palindromes
        arrayOf("abc", "def") to listOf(), // no pairs
        arrayOf("a", "aa", "aaa") to
            listOf(listOf(0, 1), listOf(0, 2), listOf(1, 0), listOf(1, 2), listOf(2, 0), listOf(2, 1)), // all pairs, none twice
        arrayOf("abcd", "dcba") to listOf(listOf(0, 1), listOf(1, 0)), // full reverse: 2 pairs, not 4
        arrayOf("ab", "a") to listOf(listOf(0, 1)), // shorter word behind: "aba"
        arrayOf("ba", "a") to listOf(listOf(1, 0)), // shorter word in front: "aba"
        arrayOf("lls", "s", "sssll") to listOf(listOf(0, 2), listOf(1, 0)), // palindromic prefix and suffix
        arrayOf("a".repeat(300), "", "a") to
            listOf(listOf(0, 1), listOf(0, 2), listOf(1, 0), listOf(1, 2), listOf(2, 0), listOf(2, 1)) // constraint bound: length 300
    )

    // the pairs may come in any order, so sort them to compare them
    private fun normalize(pairs: List<List<Int>>) = pairs.sortedWith(compareBy({ it[0] }, { it[1] }))

    private fun verify(solution: (Array<String>) -> List<List<Int>>) {
        cases.forEach { (words, expected) ->
            assertEquals(normalize(expected), normalize(solution(words.copyOf())), "words = ${words.contentToString()}")
        }
    }

    @Test
    fun `starter solution`() {
        verify(::palindromePairs)
    }

    @Test
    fun `bruteforce solution`() {
        verify(::palindromePairsBruteforce)
    }

    @Test
    fun `hash map solution`() {
        verify(::palindromePairsHashMap)
    }
}
