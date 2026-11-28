package playground.string

/*
Editing a StringBuilder
sb.append(x)                 at the end, O(1) amortized
sb.insert(i, x)              at index i, O(n): everything behind i moves
sb.deleteCharAt(i)           O(n), at the end O(1)
sb.setLength(sb.length - 1)  drops the last character, O(1), also clears with setLength(0)
sb[i] = c                    setCharAt, O(1)
sb.reverse()                 in place, returns the builder
sb.clear(), sb.length, sb.toString(), sb.isEmpty()

The backtracking pattern: append a character, recurse, then remove it again, so one builder serves the whole
search instead of a new string per step.

When to reach for it: building a result character by character, backtracking (permutations, parentheses),
editing a string in place.

Kotlin pitfalls:
- sb.toString() copies, O(n). Call it once at the end, or only when a result is complete.
- sb == other compares references. Compare sb.toString(), or use sb.contentEquals(other).
- insert(0, c) in a loop is O(n²). Append and reverse() at the end instead.

See toolbox: toolbox/string/StringBuilding.kt for why StringBuilder beats += in a loop.

n: length of the builder
T: O(1) at the end, O(n) at the front or in the middle
S: O(n)
 */

// all strings of n balanced pairs of parentheses, one builder for the whole backtracking
fun balancedParentheses(n: Int): List<String> {
    val result = mutableListOf<String>()
    val sb = StringBuilder()
    fun build(open: Int, close: Int) {
        if (sb.length == 2 * n) {
            result.add(sb.toString())
            return
        }
        if (open < n) {
            sb.append('(')
            build(open + 1, close)
            sb.setLength(sb.length - 1)
        }
        if (close < open) {
            sb.append(')')
            build(open, close + 1)
            sb.deleteCharAt(sb.lastIndex)
        }
    }
    build(0, 0)
    return result
}

// s with every character at the given indices replaced: setCharAt in O(1) each
fun replaceAt(s: String, indices: List<Int>, c: Char): String {
    val sb = StringBuilder(s)
    for (i in indices) {
        sb[i] = c
    }
    return sb.toString()
}

// the digits of a non-negative number with a separator every three digits: 1234567 -> "1,234,567"
fun withThousands(n: Long, separator: Char = ','): String {
    val sb = StringBuilder(n.toString())
    var i = sb.length - 3
    while (i > 0) {
        sb.insert(i, separator)
        i -= 3
    }
    return sb.toString()
}
