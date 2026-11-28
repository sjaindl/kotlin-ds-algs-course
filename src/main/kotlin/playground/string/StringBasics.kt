package playground.string

/*
Everyday string operations
s.length, s[i], s.first(), s.last(), s.indices, s.lastIndex
s.substring(from, to)                  end index exclusive, O(to - from) copy
s.take(3), s.drop(3), s.takeLast(3)    never throw: take(10) on "abc" gives "abc"
s.split(" "), s.split(Regex("\\s+"))   "a  b".split(" ") = ["a", "", "b"], the Regex skips repeated blanks
list.joinToString(", ")                prefix, postfix and a transform lambda are optional
s.trim(), s.padStart(5, '0')           "42".padStart(5, '0') = "00042"
s.repeat(3), s.reversed()              reversed() returns a CharSequence on CharSequence, a String on String
s.lowercase(), s.uppercase()           toLowerCase() is deprecated
s.startsWith("ab"), s.contains("b"), s.indexOf("b")
"$name has ${list.size} items"         string templates

Kotlin pitfalls:
- Strings are immutable, s[0] = 'x' does not compile. Use a CharArray or StringBuilder.
- == compares content (equals), === compares references. Never use === for strings.
- substring(from, to) throws on a bad index, take / drop clamp instead.

See toolbox: toolbox/string/StringBuilding.kt for StringBuilder and runs.

n: length of the string
T: O(n) for every operation that creates a new string, O(1) for length and s[i]
S: O(n) for the new string
 */

// the words of a sentence, ignoring leading, trailing and repeated blanks
fun words(sentence: String): List<String> {
    val trimmed = sentence.trim()
    if (trimmed.isEmpty()) return emptyList()
    return trimmed.split(Regex("\\s+"))
}

// reverses the order of the words: "the sky is blue" -> "blue is sky the"
fun reverseWords(sentence: String): String = words(sentence).reversed().joinToString(" ")

// true if s reads the same in both directions
fun isPalindrome(s: String): Boolean = s == s.reversed()
