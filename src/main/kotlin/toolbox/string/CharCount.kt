package toolbox.string

/*
Frequency counting
Count how often every character occurs. Many string questions only depend on these counts, not on the order of the
characters. For a small known alphabet (e.g. lowercase a-z), an IntArray(26) indexed by char - 'a' replaces a hash
map: no hashing, no boxing, and O(1) space. For arbitrary characters, count in a map instead.

When to reach for it: anagrams, "same characters", "most frequent", permutations of a word, sorting by frequency.

Seen in:
    questions/string/group_anagrams/solution/GroupAnagramsBruteforce.kt         isAnagram with one count array
    questions/string/group_anagrams/solution/GroupAnagramsCharCount.kt          counts as key
    questions/string/close_strings/solution/CloseStringsHashMap.kt              map counts, sorted values
    questions/string/close_strings/solution/CloseStringsFrequencyArray.kt       IntArray(26), sorted counts
    questions/string/frequency_sort/solution/FrequencySortHashMap.kt            map counts

Kotlin: char - 'a' is an Int (the index), but char + 1 is a Char again. Uppercase input needs char - 'A'.

"cabbba" -> a2 b3 c1 -> [2, 3, 1, 0, ..., 0]
"tree"   -> {t: 1, r: 1, e: 2}

n: length of the string
T: O(n)
S: O(1) - 26 counts, or one entry per distinct character in the map
 */

const val ALPHABET_SIZE = 26

// counts of the lowercase letters a-z: letterCounts("abb") = [1, 2, 0, ..., 0]
fun letterCounts(s: String): IntArray {
    val counts = IntArray(size = ALPHABET_SIZE)
    for (char in s) {
        counts[char - 'a']++
    }
    return counts
}

// counts of any characters: charCounts("tree") = {t=1, r=1, e=2}
fun charCounts(s: String): Map<Char, Int> {
    val counts = mutableMapOf<Char, Int>()
    for (char in s) {
        counts[char] = counts.getOrDefault(key = char, defaultValue = 0) + 1
    }
    return counts
}
