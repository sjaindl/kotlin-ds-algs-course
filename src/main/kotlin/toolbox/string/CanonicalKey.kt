package toolbox.string

/*
Canonical key
Map every string to a key that is the same for all strings that belong together, then group them in a hash map
from key to group. Every string is looked up once instead of being compared with every other string.
For anagrams there are two keys:
- the sorted letters: "eat", "tea" -> "aet", O(k log k)
- the letter counts: "eat", "tea" -> [1, 0, 0, 0, 1, ...], O(k) (see CharCount.kt)

When to reach for it: "group the strings that ...", finding duplicates up to some transformation (anagrams,
rotations, same pattern).

Seen in:
    questions/string/group_anagrams/solution/GroupAnagramsSorting.kt      sorted key
    questions/string/group_anagrams/solution/GroupAnagramsCharCount.kt    count key
    questions/string/group_anagrams/solution/GroupAnagramsBruteforce.kt   without key: compare with every group

Kotlin: an IntArray as key doesn't work, arrays use reference equality and hashCode, so two equal count arrays end
up in different groups. Convert it with toList(), a List compares by content.

["eat", "tea", "tan"] -> keys "aet", "aet", "ant" -> [[eat, tea], [tan]]

n: number of strings, k: length of the longest string
T: O(n · k log k) with the sorted key, O(n · k) with the count key
S: O(n · k) - keys and groups
 */

// the letters sorted: sortedKey("tea") = "aet"
fun sortedKey(s: String): String = s.toCharArray().sorted().joinToString(separator = "")

// the letter counts as List, so equal counts give equal keys
fun countKey(s: String): List<Int> = letterCounts(s).toList()

fun isAnagram(a: String, b: String): Boolean = a.length == b.length && countKey(a) == countKey(b)

// groups the strings with equal key, in the order the groups first appear
fun groupByKey(strings: List<String>, key: (String) -> Any): List<List<String>> {
    val groups = mutableMapOf<Any, MutableList<String>>()

    for (str in strings) {
        groups.getOrPut(key(str)) {
            mutableListOf()
        }.add(str)
    }

    return groups.values.toList()
}
