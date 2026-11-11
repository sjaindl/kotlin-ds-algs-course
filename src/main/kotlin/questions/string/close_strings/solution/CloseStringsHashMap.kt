package questions.string.close_strings.solution

/*
Operation 1 means the order of the characters doesn't matter, only how often each one occurs.
Operation 2 swaps the counts of two existing characters, but can't create a new character or remove one.
So the words are close exactly when they use the same set of characters and the counts, sorted, are the same.
Count both words in a hash map, then compare the key sets and the sorted values.

"cabbba" -> {c: 1, a: 2, b: 3}, "abbccc" -> {a: 1, b: 2, c: 3}
keys {a, b, c} == {a, b, c}, sorted counts [1, 2, 3] == [1, 2, 3] -> true

"uau" -> {u: 2, a: 1}, "ssx" -> {s: 2, x: 1}: same counts, but different keys -> false

n: length of the words
T: O(n) - counting, sorting at most 26 values is constant
S: O(1) - at most 26 entries per map

if lengths differ: return false
counts1 = count of every character in word1
counts2 = count of every character in word2
if keys of counts1 != keys of counts2: return false
return sorted values of counts1 == sorted values of counts2
 */

fun closeStringsHashMap(word1: String, word2: String): Boolean {
    if (word1.length != word2.length) return false

    val counts1 = countCharacters(word1)
    val counts2 = countCharacters(word2)

    // same characters: operation 2 only exchanges existing ones
    if (counts1.keys.size != counts2.keys.size) return false
    if (!counts1.keys.containsAll(counts2.keys)) return false

    // same counts in any order: operation 2 swaps the counts of two characters
    return counts1.values.sorted() == counts2.values.sorted()
}

private fun countCharacters(word: String): Map<Char, Int> {
    return buildMap {
        for (char in word) {
            put(char, getOrDefault(key = char, defaultValue = 0) + 1)
        }
    }
}
