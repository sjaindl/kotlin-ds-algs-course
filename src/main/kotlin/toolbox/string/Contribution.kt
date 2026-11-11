package toolbox.string

/*
Contribution technique
A string of length n has n(n + 1) / 2 substrings, so listing them all is O(n²) at least, and O(n³) if every
substring is built. Turn the question around: instead of asking every substring, ask every index in how many
substrings it counts, and add these numbers up.
- index i lies in (i + 1) * (n - i) substrings: i + 1 choices for the start, n - i for the end
- if the substring must not reach prev (left) or next (right), there are (i - prev) * (next - i) of them.
  Use -1 and n when there is no such boundary.

When to reach for it: "sum over all substrings / subarrays", when the answer is a sum of what single elements
contribute. Often combined with the indices of every character (previous / next occurrence).

Seen in:
    questions/string/unique_letter_string/solution/UniqueLetterStringCharSplit.kt        (i - prev) * (next - i)
    questions/string/unique_letter_string/solution/UniqueLetterStringNextOccurrences.kt  next2 - next1 per start
    questions/string/unique_letter_string/solution/UniqueLetterStringBruteforce.kt       without: all substrings

Kotlin: these products grow like n², and the sums faster, so they overflow an Int for large n. Use Long unless the
task bounds the result. Taking indices from the front of a list: ArrayList.removeAt(0) shifts all others (O(n)),
ArrayDeque.removeFirst() is O(1).

"LEETCODE", the 'E's at 1, 2, 7 (n = 8)
    E at 1: prev -1, next 2 -> (1 - -1) * (2 - 1) = 2
    E at 2: prev  1, next 7 -> (2 -  1) * (7 - 2) = 5
    E at 7: prev  2, next 8 -> (7 -  2) * (8 - 7) = 5

n: length of the string
T: O(1) per index, O(n) for the indices of every character
S: O(n) - the indices
 */

fun substringCount(n: Int): Long = n.toLong() * (n + 1) / 2

// substrings containing index, but neither prev nor next: substringsBetween(-1, 1, 3) = 4 ("ab", "abc", "b", "bc")
fun substringsBetween(prev: Int, index: Int, next: Int): Long = (index - prev).toLong() * (next - index)

// the indices of every character in increasing order: indicesByChar("ABA") = {A=[0, 2], B=[1]}
fun indicesByChar(s: String): Map<Char, List<Int>> {
    val indices = mutableMapOf<Char, MutableList<Int>>()
    s.forEachIndexed { index, char ->
        indices.getOrPut(char) {
            mutableListOf()
        }.add(index)
    }
    return indices
}
