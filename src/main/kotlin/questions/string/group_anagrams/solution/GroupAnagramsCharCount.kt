package questions.string.group_anagrams.solution

/*
Instead of sorting, count how often each of the 26 letters occurs: anagrams have the same counts.
Counting is O(k), so the key is built faster than the O(k log k) sorted string.

Kotlin pitfall: an IntArray key doesn't work, arrays use reference equality and hashCode, so two equal count
arrays land in different groups. A List<Int> compares by content, so it can be used as a key.

"eat" -> counts a1 e1 t1 -> {[a1 e1 t1]: [eat]}
"tea" -> counts a1 e1 t1 -> {[a1 e1 t1]: [eat, tea]}
"bat" -> counts a1 b1 t1 -> {[a1 e1 t1]: [eat, tea], [a1 b1 t1]: [bat]}

n: number of strings, k: length of the longest string
T: O(n · k) - counting every string, plus O(26) per key
S: O(n · k) - keys and groups

groups = empty map
for each str in strs:
    counts = [0] * 26
    for each char in str:
        counts[char - 'a']++
    append str to groups[counts], create the list first if missing
return values of groups
 */

const val ALPHABET_SIZE = 26

fun groupAnagramsCharCount(strs: Array<String>): List<List<String>> {
    return strs.groupBy { str ->
        val counts = IntArray(size = ALPHABET_SIZE)
        for (char in str) {
            counts[char - 'a']++
        }
        counts.toList() // Convert to list so structural equality works for groupBy keys
    }.values.toList()
}
