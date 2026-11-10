package questions.string.group_anagrams

import questions.string.group_anagrams.solution.groupAnagramsBruteforce
import questions.string.group_anagrams.solution.groupAnagramsCharCount
import questions.string.group_anagrams.solution.groupAnagramsSorting
import kotlin.test.Test
import kotlin.test.assertEquals

class GroupAnagramsTest {

    private val cases = listOf(
        arrayOf("eat", "tea", "tan", "ate", "nat", "bat") to
            listOf(listOf("bat"), listOf("nat", "tan"), listOf("ate", "eat", "tea")), // example 1
        arrayOf("") to listOf(listOf("")), // example 2
        arrayOf("a") to listOf(listOf("a")), // example 3
        arrayOf("ab", "ab", "ab") to listOf(listOf("ab", "ab", "ab")), // all equal: duplicates are kept
        arrayOf("abc", "def", "ghi") to listOf(listOf("abc"), listOf("def"), listOf("ghi")), // no anagrams
        arrayOf("", "", "a") to listOf(listOf("", ""), listOf("a")), // empty strings form their own group
        arrayOf("aab", "abb", "ab") to listOf(listOf("aab"), listOf("abb"), listOf("ab")), // same letters, other counts
        arrayOf("a" + "b".repeat(11), "a".repeat(11) + "b") to
            listOf(listOf("a" + "b".repeat(11)), listOf("a".repeat(11) + "b")), // counts 1,11 vs 11,1 must not collide
        arrayOf("ab", "a") to listOf(listOf("ab"), listOf("a")), // prefix is not an anagram
        arrayOf("z".repeat(50) + "a".repeat(50), "az".repeat(50)) to
            listOf(listOf("az".repeat(50), "z".repeat(50) + "a".repeat(50))) // constraint bound: length 100
    )

    // the groups may come in any order, so sort each group and then the groups to compare them
    private fun normalize(groups: List<List<String>>) = groups.map { it.sorted() }.sortedBy { it.joinToString(",") }

    private fun verify(solution: (Array<String>) -> List<List<String>>) {
        cases.forEach { (strs, expected) ->
            assertEquals(normalize(expected), normalize(solution(strs.copyOf())), "strs = ${strs.contentToString()}")
        }
    }

    @Test
    fun `starter solution`() {
        verify(::groupAnagrams)
    }

    @Test
    fun `bruteforce solution`() {
        verify(::groupAnagramsBruteforce)
    }

    @Test
    fun `sorting solution`() {
        verify(::groupAnagramsSorting)
    }

    @Test
    fun `char count solution`() {
        verify(::groupAnagramsCharCount)
    }
}
