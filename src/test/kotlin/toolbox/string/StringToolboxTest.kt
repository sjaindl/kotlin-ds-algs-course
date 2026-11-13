package toolbox.string

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class StringToolboxTest {

    @Test
    fun `letter counts`() {
        val counts = letterCounts("cabbba")
        assertEquals(listOf(2, 3, 1), counts.take(3))
        assertEquals(6, counts.sum())
        assertContentEquals(IntArray(26), letterCounts("")) // empty
        assertEquals(1, letterCounts("z")[25]) // single char, last letter
        assertEquals(4, letterCounts("aaaa")[0]) // all equal
    }

    @Test
    fun `char counts`() {
        assertEquals(mapOf('t' to 1, 'r' to 1, 'e' to 2), charCounts("tree"))
        assertEquals(mapOf('A' to 1, 'a' to 2, '1' to 1), charCounts("Aaa1")) // case-sensitive, digits
        assertEquals(emptyMap(), charCounts("")) // empty
    }

    @Test
    fun `canonical keys`() {
        assertEquals("aet", sortedKey("tea"))
        assertEquals(sortedKey("eat"), sortedKey("ate"))
        assertEquals("", sortedKey("")) // empty

        assertEquals(countKey("eat"), countKey("tea"))
        assertNotEquals(countKey("eat"), countKey("tan"))

        // arrays compare by reference, lists by content
        assertFalse(letterCounts("eat").contentEquals(letterCounts("tea")))
        assertEquals(letterCounts("eat").toList(), letterCounts("tea").toList())
    }

    @Test
    fun `is anagram`() {
        assertTrue(isAnagram("anagram", "nagaram"))
        assertFalse(isAnagram("rat", "car"))
        assertFalse(isAnagram("ab", "abb")) // different length
        assertTrue(isAnagram("", "")) // empty
        assertTrue(isAnagram("a", "a")) // single char
    }

    @Test
    fun `group by key`() {
        val words = listOf("eat", "tea", "tan", "ate", "nat", "bat")
        val expected = listOf(listOf("eat", "tea", "ate"), listOf("tan", "nat"), listOf("bat"))
        assertEquals(expected, groupByKey(words, ::sortedKey))
        assertEquals(expected, groupByKey(words, ::countKey))
        assertEquals(listOf(listOf("")), groupByKey(listOf(""), ::sortedKey)) // single empty string
        assertEquals(emptyList(), groupByKey(emptyList(), ::sortedKey)) // no strings
    }

    @Test
    fun `letter mask`() {
        assertEquals(0b111, letterMask("cabbba"))
        assertEquals(letterMask("cabbba"), letterMask("abbccc")) // same set of letters
        assertNotEquals(letterMask("uau"), letterMask("ssx"))
        assertEquals(0, letterMask("")) // empty
        assertEquals(1 shl 25, letterMask("zzz")) // highest bit, all equal

        val all = letterMask("thequickbrownfoxjumpsoverthelazydog")
        assertEquals(26, all.countOneBits())
        assertTrue(hasLetter(all, 'z'))
        assertTrue(hasLetter(letterMask("uau"), 'a'))
        assertFalse(hasLetter(letterMask("uau"), 's'))
    }

    @Test
    fun runs() {
        assertEquals(listOf("ee", "r", "t"), runs("eert"))
        assertEquals(listOf("aa", "b", "ccc"), runs("aabccc"))
        assertEquals(listOf("a", "b", "a"), runs("aba")) // unsorted: equal chars only join when adjacent
        assertEquals(listOf("aaaa"), runs("aaaa")) // all equal
        assertEquals(listOf("x"), runs("x")) // single char
        assertEquals(emptyList(), runs("")) // empty
    }

    @Test
    fun expand() {
        assertEquals("eetr", expand(listOf('e' to 2, 't' to 1, 'r' to 1)))
        assertEquals("b", expand(listOf('a' to 0, 'b' to 1))) // count 0
        assertEquals("", expand(emptyList())) // empty
        assertEquals("a".repeat(100_000), expand(listOf('a' to 100_000))) // long result
    }

    @Test
    fun `substring counts`() {
        assertEquals(0L, substringCount(0))
        assertEquals(1L, substringCount(1))
        assertEquals(6L, substringCount(3))
        assertTrue(substringCount(100_000) > Int.MAX_VALUE) // 5_000_050_000 needs a Long

        assertEquals(4L, substringsBetween(-1, 1, 3)) // no boundaries: "ab", "abc", "b", "bc" in "abc"
        assertEquals(1L, substringsBetween(-1, 0, 1)) // single char
        assertEquals(1L, substringsBetween(1, 2, 3)) // both neighbours are boundaries: only the char itself
        assertEquals(2_500_050_000L, substringsBetween(-1, 49_999, 100_000)) // overflows an Int
    }

    @Test
    fun `all substrings`() {
        assertEquals(listOf("a", "ab", "abc", "b", "bc", "c"), allSubstrings("abc"))
        assertEquals(listOf("a", "aa", "a"), allSubstrings("aa")) // equal substrings are listed again
        assertEquals(listOf("z"), allSubstrings("z")) // single char
        assertEquals(emptyList(), allSubstrings("")) // empty
        assertEquals(substringCount(10), allSubstrings("abcdefghij").size.toLong())
    }

    @Test
    fun `all substrings recursive`() {
        assertEquals(listOf("a", "ab", "abc", "b", "bc", "c"), allSubstringsRecursive("abc"))
        assertEquals(emptyList(), allSubstringsRecursive("")) // empty
        listOf("z", "aa", "ABA", "LEETCODE").forEach { s ->
            assertEquals(allSubstrings(s), allSubstringsRecursive(s), s) // same list, same order
        }
    }

    @Test
    fun `indices by char`() {
        assertEquals(mapOf('A' to listOf(0, 2), 'B' to listOf(1)), indicesByChar("ABA"))
        assertEquals(mapOf('A' to listOf(0, 1, 2)), indicesByChar("AAA")) // all equal
        assertEquals(emptyMap(), indicesByChar("")) // empty
    }

    @Test
    fun `contribution sums`() {
        val cases = listOf(
            "ABC" to 10L,
            "ABA" to 8L,
            "LEETCODE" to 92L,
            "Z" to 1L, // single char
            "AAAA" to 4L // all equal: every A is unique only on its own
        )
        cases.forEach { (s, expected) ->
            assertEquals(expected, uniqueLetterSum(s), s)
        }
    }

    // sum of substringsBetween over every occurrence, bounded by the neighbouring occurrences
    private fun uniqueLetterSum(s: String): Long {
        var total = 0L
        indicesByChar(s).values.forEach { list ->
            list.forEachIndexed { position, index ->
                val prev = if (position == 0) -1 else list[position - 1]
                val next = if (position == list.size - 1) s.length else list[position + 1]
                total += substringsBetween(prev, index, next)
            }
        }
        return total
    }
}
