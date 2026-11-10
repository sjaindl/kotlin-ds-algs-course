package questions.string.group_anagrams.solution

/*
Anagrams consist of the same letters, so sorting their letters gives the same string: "eat", "tea" and "ate" all
become "aet". Use the sorted string as key of a hash map from key to group, so every lookup is O(k) on average.

"eat" -> key "aet" -> {aet: [eat]}
"tea" -> key "aet" -> {aet: [eat, tea]}
"tan" -> key "ant" -> {aet: [eat, tea], ant: [tan]}

n: number of strings, k: length of the longest string
T: O(n · k log k) - sorting every string
S: O(n · k) - keys and groups

groups = empty map
for each str in strs:
    key = letters of str sorted
    append str to groups[key], create the list first if missing
return values of groups
 */

fun groupAnagramsSorting(strs: Array<String>): List<List<String>> {
    val groups = mutableMapOf<String, MutableList<String>>()

    for (str in strs) {
        val key = str.toCharArray().sorted().joinToString(separator = "")
        groups.getOrPut(key) {
            mutableListOf()
        }.add(str)
    }

    return groups.values.toList()
}
