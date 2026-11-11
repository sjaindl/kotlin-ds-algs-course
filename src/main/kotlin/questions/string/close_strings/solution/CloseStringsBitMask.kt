package questions.string.close_strings.solution

/*
Same idea again, but the set of characters of a word is stored as the bits of one Int: bit i is set when letter
'a' + i occurs. It's filled while counting, and comparing two sets becomes a single == instead of a loop over the
26 letters.

Kotlin pitfall: there are no << and | operators, use the infix functions shl and or: mask = mask or (1 shl i).
26 bits fit into an Int, the highest one is 1 shl 25 for 'z'.

"cabbba" -> counts a2 b3 c1, mask 0b111 (c b a), "abbccc" -> counts a1 b2 c3, mask 0b111
masks equal, sorted counts equal -> true

n: length of the words
T: O(n) - counting, sorting 26 values is constant
S: O(1) - two arrays of 26 counts and two Ints

if lengths differ: return false
for each word:
    counts = [0] * 26, mask = 0
    for each char:
        counts[char - 'a']++
        mask = mask or (1 shl (char - 'a'))
if mask1 != mask2: return false
return sorted counts1 == sorted counts2
 */

private const val ALPHABET_SIZE = 26

fun closeStringsBitMask(word1: String, word2: String): Boolean {
    if (word1.length != word2.length) return false

    val (counts1, mask1) = countWithMask(word1)
    val (counts2, mask2) = countWithMask(word2)

    // same characters: one comparison of the bit sets
    if (mask1 != mask2) return false

    // same counts in any order
    return counts1.sorted() == counts2.sorted()
}

private fun countWithMask(word: String): Pair<IntArray, Int> {
    val counts = IntArray(size = ALPHABET_SIZE)
    var mask = 0
    for (char in word) {
        counts[char - 'a']++
        mask = mask or (1 shl (char - 'a'))
    }

    return counts to mask
}
