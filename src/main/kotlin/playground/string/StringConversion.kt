package playground.string

import java.util.Locale

/*
Converting and slicing strings
"42".toInt(), "42".toLong()           throw NumberFormatException on bad input
"4x".toIntOrNull()                    null instead of an exception
10.toString(2) = "1010"               any radix 2..36
"1010".toInt(2) = 10
42.toString(), "$x"                   number to string
s.chunked(3)                          "abcdefg" -> ["abc", "def", "g"]
s.windowed(3)                         "abcd" -> ["abc", "bcd"], every substring of length 3
buildString { append(...) }           a StringBuilder that returns the String
"%.2f".format(Locale.ROOT, 3.14159)   "3.14", rounds; also "%5d", "%-8s", "%03d"
"ab".padEnd(5), "ab".padStart(5)      "ab   " / "   ab", aligned columns in output

Kotlin pitfalls:
- "2147483648".toInt() throws, the value does not fit into an Int. Use toLong() or toBigInteger().
- " 42".toInt() throws too, trim() first.
- windowed(k) on a string shorter than k returns an empty list.
- "%.2f".format(x) uses the machine's locale: on a German system it prints "3,14". Pass Locale.ROOT for a dot.

n: length of the string, k: window or chunk size
T: O(n) for conversion and chunked, O(n * k) for windowed (every window is a new string)
S: as much as the result
 */

// the binary digits of a non-negative number, padded to width: binary(5, 8) = "00000101"
fun binary(n: Int, width: Int = 0): String = n.toString(2).padStart(width, '0')

// adds two binary strings: addBinary("11", "1") = "100"
fun addBinary(a: String, b: String): String = buildString {
    var i = a.lastIndex
    var j = b.lastIndex
    var carry = 0
    while (i >= 0 || j >= 0 || carry > 0) {
        val sum = carry + (if (i >= 0) a[i--] - '0' else 0) + (if (j >= 0) b[j--] - '0' else 0)
        append(sum % 2)
        carry = sum / 2
    }
}.reversed().ifEmpty {
    "0"
}

// x rounded to the given decimals, always with a dot: decimals(2.0 / 3, 3) = "0.667"
fun decimals(x: Double, places: Int): String = "%.${places}f".format(Locale.ROOT, x)

// a two-column table row, the label padded to width: row("sum", 12, 6) = "sum   12"
fun row(label: String, value: Any, width: Int): String = label.padEnd(width) + value

// the number of distinct substrings of length k
fun distinctWindows(s: String, k: Int): Int = s.windowed(k).toSet().size

// parses all integers in a separated list, skipping entries that are not numbers: "1, x, 3" -> [1, 3]
fun parseInts(s: String, separator: String = ","): List<Int> = s.split(separator).mapNotNull {
    it.trim().toIntOrNull()
}
