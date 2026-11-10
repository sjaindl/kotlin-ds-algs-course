package questions.string.group_anagrams.solution

/*
Take the strings one by one and compare each with the first string of every group found so far.
Two strings are anagrams if they have the same length and every letter occurs equally often in both.
If no group matches, the string starts a new group.

"eat" -> new group [eat]
"tea" -> anagram of "eat" -> [eat, tea]
"tan" -> no match -> new group [tan]

n: number of strings, k: length of the longest string
T: O(n² · k) - every string is compared with up to n group heads in O(k)
S: O(n · k) - the groups

groups = []
for each str in strs:
    group = first group in groups whose first string is an anagram of str
    if group exists:
        add str to group
    else:
        add [str] to groups
return groups

isAnagram(a, b):
    if length differs: return false
    counts = [0] * 26
    for each char in a: counts[char - 'a']++
    for each char in b: counts[char - 'a']--
    return all counts are 0
 */

fun groupAnagramsBruteforce(strs: Array<String>): List<List<String>> {
    val groups = mutableListOf<MutableList<String>>()

    for (str in strs) {
        val group = groups.firstOrNull {
            isAnagram(a = it.first(), b = str)
        }

        if (group != null) {
            group.add(str)
        } else {
            groups.add(mutableListOf(str))
        }
    }

    return groups
}

private fun isAnagram(a: String, b: String): Boolean {
    if (a.length != b.length) return false

    val counts = IntArray(size = 26)
    for (char in a) {
        counts[char - 'a']++
    }
    for (char in b) {
        counts[char - 'a']--
    }

    return counts.all {
        it == 0
    }
}
