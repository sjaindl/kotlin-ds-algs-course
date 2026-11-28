package playground.string

/*
Characters
c.isDigit(), c.isLetter(), c.isLetterOrDigit(), c.isWhitespace(), c.isUpperCase()
c.lowercaseChar(), c.uppercaseChar()
c - 'a'                    Int: the letter's index 0..25
'a' + 2                    Char: 'c'
c.code                     Int: the char code, 'A'.code = 65 (toInt() is deprecated)
Char(65), 65.toChar()      Char: 'A', back from a code, e.g. a bucket index of an IntArray(128)
'7'.digitToInt()           7, the digit's value
7.digitToChar()            '7'
c.toString()               a one-character String
s.toCharArray(), String(chars), chars.concatToString()

Kotlin pitfalls:
- '7'.code is 55, not 7. Use digitToInt() or c - '0'.
- 'a' + 'b' does not compile: Char + Int is a Char, Char - Char is an Int, Char + Char is not defined.
- Char and String differ: 'a' == "a"[0], but 'a'.equals("a") is false.

n: length of the string
T: O(1) per character, O(n) per string
S: O(n) for a new string or CharArray
 */

// keeps only letters and digits, lowercased: "A man, a plan!" -> "amanaplan"
fun normalize(s: String): String = s.filter {
    it.isLetterOrDigit()
}.lowercase()

// true if s is a palindrome when you ignore case and everything except letters and digits
fun isCleanPalindrome(s: String): Boolean {
    val clean = normalize(s)
    return clean == clean.reversed()
}

// shifts every lowercase letter by k places, wrapping around after 'z' (Caesar cipher)
fun shiftLetters(s: String, k: Int): String = s.map {
    if (it in 'a'..'z') 'a' + (it - 'a' + k).mod(26) else it
}.joinToString("")

// the characters that occur in s, in code order: counts per code, then Char(code) back
fun distinctChars(s: String): String {
    val counts = IntArray(128)
    for (c in s) {
        counts[c.code]++
    }
    val sb = StringBuilder()
    for (code in counts.indices) {
        if (counts[code] > 0) {
            sb.append(Char(code))
        }
    }
    return sb.toString()
}

// the sum of all digits in s: "a1b22" -> 5
fun digitSum(s: String): Int = s.filter {
    it.isDigit()
}.sumOf {
    it.digitToInt()
}
