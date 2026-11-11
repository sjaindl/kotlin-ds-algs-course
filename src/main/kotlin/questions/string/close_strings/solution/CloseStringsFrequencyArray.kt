package questions.string.close_strings.solution

/*
Same idea as the hash map (same set of characters, same sorted counts), but there are only 26 lowercase letters,
so an IntArray(26) indexed by char - 'a' replaces the map: no hashing and no boxing.
A letter is used by a word when its count is > 0, so the sets of characters are equal when no letter has count 0 in
one word and > 0 in the other. Sorting both arrays keeps the zeros (the same number in both) at the front, so they
don't disturb the comparison of the counts.

"cabbba" -> a2 b3 c1, "abbccc" -> a1 b2 c3
used letters a, b, c in both; sorted [0, ..., 1, 2, 3] == [0, ..., 1, 2, 3] -> true

n: length of the words
T: O(n) - counting, sorting 26 values is constant
S: O(1) - two arrays of 26 counts

if lengths differ: return false
counts1 = [0] * 26, counts2 = [0] * 26, filled by counting
for each letter:
    if (counts1[letter] == 0) != (counts2[letter] == 0): return false
return sorted counts1 == sorted counts2
 */

private const val ALPHABET_SIZE = 26

fun closeStringsFrequencyArray(word1: String, word2: String): Boolean {
    if (word1.length != word2.length) return false

    val counts1 = countLetters(word1)
    val counts2 = countLetters(word2)

    // same characters: a letter is used in both words or in neither
    for (letter in 0 until 26) {
        if ((counts1[letter] == 0) != (counts2[letter] == 0)) return false
    }

    // same counts in any order
    return counts1.sorted() == counts2.sorted()
}

private fun countLetters(word: String): IntArray {
    val counts = IntArray(size = ALPHABET_SIZE)
    for (char in word) {
        counts[char - 'a']++
    }
    return counts
}
