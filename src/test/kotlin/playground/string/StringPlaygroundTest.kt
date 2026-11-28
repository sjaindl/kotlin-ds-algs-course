package playground.string

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StringPlaygroundTest {

    @Test
    fun `words of a sentence`() {
        assertEquals(listOf("the", "sky", "is", "blue"), words("  the sky   is blue "))
        assertEquals(listOf("a", "", "b"), "a  b".split(" ")) // split(" ") keeps empty parts
        assertEquals(emptyList(), words("   ")) // only blanks
        assertEquals(emptyList(), words(""))
        assertEquals(listOf("one"), words("one"))
    }

    @Test
    fun `reverse words`() {
        assertEquals("blue is sky the", reverseWords("the sky is blue"))
        assertEquals("world hello", reverseWords("  hello   world  "))
        assertEquals("", reverseWords(" "))
    }

    @Test
    fun `palindrome`() {
        assertTrue(isPalindrome("racecar"))
        assertTrue(isPalindrome("abba"))
        assertFalse(isPalindrome("ab"))
        assertTrue(isPalindrome("")) // empty
        assertTrue(isPalindrome("x")) // single character
        assertFalse(isPalindrome("Aa")) // case matters
    }

    @Test
    fun `normalize text`() {
        assertEquals("amanaplan", normalize("A man, a plan!"))
        assertEquals("", normalize(",.! "))
        assertEquals("r2d2", normalize("R2-D2"))
    }

    @Test
    fun `clean palindrome`() {
        assertTrue(isCleanPalindrome("A man, a plan, a canal: Panama"))
        assertFalse(isCleanPalindrome("race a car"))
        assertTrue(isCleanPalindrome(" ")) // nothing left: empty is a palindrome
        assertFalse(isCleanPalindrome("0P")) // digits count
    }

    @Test
    fun `shift letters`() {
        assertEquals("abc", shiftLetters("xyz", 3)) // wraps after 'z'
        assertEquals("xyz", shiftLetters("abc", -3)) // negative shift wraps before 'a'
        assertEquals("abc", shiftLetters("abc", 26)) // full circle
        assertEquals("b-C!", shiftLetters("a-C!", 1)) // only lowercase letters move
        assertEquals("", shiftLetters("", 5))
    }

    @Test
    fun `digit sum`() {
        assertEquals(5, digitSum("a1b22"))
        assertEquals(0, digitSum("abc"))
        assertEquals(55, '7'.code) // not 7
        assertEquals(7, '7'.digitToInt())
        assertEquals(2, 'c' - 'a')
        assertEquals('c', 'a' + 2)
    }

    @Test
    fun `binary digits`() {
        assertEquals("101", binary(5))
        assertEquals("00000101", binary(5, 8))
        assertEquals("0", binary(0))
        assertEquals(10, "1010".toInt(2))
    }

    @Test
    fun `add binary`() {
        assertEquals("100", addBinary("11", "1"))
        assertEquals("10101", addBinary("1010", "1011"))
        assertEquals("0", addBinary("0", "0"))
        assertEquals("1", addBinary("", "1")) // one side empty
        assertEquals("0", addBinary("", "")) // both empty
        assertEquals("1" + "0".repeat(64), addBinary("1".repeat(64), "1")) // longer than a Long
    }

    @Test
    fun `distinct windows`() {
        assertEquals(2, distinctWindows("abab", 2)) // "ab", "ba"
        assertEquals(1, distinctWindows("aaaa", 3))
        assertEquals(0, distinctWindows("ab", 3)) // shorter than the window
        assertEquals(listOf("abc", "def", "g"), "abcdefg".chunked(3))
    }

    @Test
    fun `parse ints`() {
        assertEquals(listOf(1, 3), parseInts("1, x, 3"))
        assertEquals(listOf(-5, 7), parseInts("-5; 7", ";"))
        assertEquals(emptyList(), parseInts(""))
        assertEquals(emptyList(), parseInts("2147483648")) // does not fit into an Int
    }

    @Test
    fun `distinct chars`() {
        assertEquals("abn", distinctChars("banana"))
        assertEquals(" !a", distinctChars("a! a")) // code order: space, '!', letters
        assertEquals("", distinctChars(""))
        assertEquals('A', Char(65))
        assertEquals('7', 7.digitToChar())
    }

    @Test
    fun `decimals with a dot`() {
        assertEquals("0.667", decimals(2.0 / 3, 3)) // rounds
        assertEquals("3", decimals(3.0, 0))
        assertEquals("-1.50", decimals(-1.5, 2))
        assertEquals("3,14", "%.2f".format(java.util.Locale.GERMANY, 3.14159)) // the locale decides the separator
    }

    @Test
    fun `table row`() {
        assertEquals("sum   12", row("sum", 12, 6))
        assertEquals("toolong1", row("toolong", 1, 3)) // padEnd never cuts
        assertEquals("   ab", "ab".padStart(5))
    }

    @Test
    fun `balanced parentheses`() {
        assertEquals(listOf("((()))", "(()())", "(())()", "()(())", "()()()"), balancedParentheses(3))
        assertEquals(listOf("()"), balancedParentheses(1))
        assertEquals(listOf(""), balancedParentheses(0)) // one way to place no pairs
        assertEquals(42, balancedParentheses(5).size) // the 5th Catalan number
    }

    @Test
    fun `replace at`() {
        assertEquals("*ell*", replaceAt("hello", listOf(0, 4), '*'))
        assertEquals("hello", replaceAt("hello", emptyList(), '*'))
        assertFailsWith<IndexOutOfBoundsException> {
            replaceAt("hi", listOf(2), '*')
        }
    }

    @Test
    fun `with thousands`() {
        assertEquals("1,234,567", withThousands(1234567))
        assertEquals("123,456", withThousands(123456)) // no leading separator
        assertEquals("999", withThousands(999))
        assertEquals("0", withThousands(0))
        assertEquals("1.000", withThousands(1000, '.'))
    }

    @Test
    fun `string builder equality`() {
        val a = StringBuilder("ab")
        val b = StringBuilder("ab")
        assertFalse(a == b) // compares references
        assertTrue(a.contentEquals(b))
        assertEquals("ba", a.reverse().toString()) // in place
        assertEquals("ba", a.toString())
    }
}
