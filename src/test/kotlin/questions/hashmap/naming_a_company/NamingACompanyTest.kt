package questions.hashmap.naming_a_company

import questions.hashmap.naming_a_company.solution.distinctNamesBruteforce
import questions.hashmap.naming_a_company.solution.distinctNamesSuffixGroups
import kotlin.test.Test
import kotlin.test.assertEquals

class NamingACompanyTest {

    private val cases = listOf(
        arrayOf("coffee", "donuts", "time", "toffee") to 6L, // example 1
        arrayOf("lack", "back") to 0L, // example 2: same suffix
        arrayOf("ab", "cd") to 2L, // smallest valid input: one pair, both orders
        arrayOf("aa", "ab") to 0L, // same first letter: the swap changes nothing
        arrayOf("a", "b") to 0L, // single letters swap into each other
        arrayOf("a", "bb") to 2L, // single letter with a longer idea: "b" and "ab"
        arrayOf("ax", "bx", "cx") to 0L, // one suffix shared by every letter
        arrayOf("aa", "bb", "cc") to 6L, // every pair valid
        arrayOf("ax", "ay", "bx", "bz") to 2L, // the shared suffix x drops out: only ay, bz
        arrayOf("ax", "bx", "ay", "cz") to 6L, // shared suffix between a and b only
        arrayOf("abcdefghij", "bbcdefghij", "zzzzzzzzzz") to 4L, // constraint bound: length 10, letters a and z
        Array(200) { "a" + "x".repeat(it % 8) + ('a' + it / 8) } to 0L // one group only
    )

    private fun verify(solution: (Array<String>) -> Long) {
        cases.forEach { (ideas, expected) ->
            assertEquals(expected, solution(ideas.copyOf()), "ideas = ${ideas.contentToString()}")
        }
    }

    @Test
    fun `starter solution`() {
        verify(::distinctNames)
    }

    @Test
    fun `bruteforce solution`() {
        verify(::distinctNamesBruteforce)
    }

    @Test
    fun `suffix groups solution`() {
        verify(::distinctNamesSuffixGroups)
    }
}
