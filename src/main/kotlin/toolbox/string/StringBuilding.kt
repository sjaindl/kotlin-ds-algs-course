package toolbox.string

/*
Building strings
Strings are immutable: result += x creates a new string and copies everything so far, so a loop of n appends is
O(n²). A StringBuilder appends in O(1) amortized and creates the string once with toString() at the end.
Sorting the characters puts equal ones next to each other, so the sorted string falls apart into runs of one
character each, and the length of a run is the count of its character. No map needed.

When to reach for it: any result built character by character, substrings extended one character at a time,
grouping equal characters without counting.

Seen in:
    questions/string/frequency_sort/solution/FrequencySortBruteforce.kt     runs of the sorted characters
    questions/string/frequency_sort/solution/FrequencySortHashMap.kt        repeat(count) { append(char) }
    questions/string/unique_letter_string/solution/UniqueLetterStringBruteforce.kt  extend a substring

"tree" -> sorted "eert" -> runs ["ee", "r", "t"]
[e to 2, t to 1, r to 1] -> "eetr"

n: length of the string
T: O(n) - every character is appended once
S: O(n) - the result
 */

// consecutive equal characters: runs("aabccc") = ["aa", "b", "ccc"]
fun runs(s: String): List<String> {
    if (s.isEmpty()) return emptyList()

    val result = mutableListOf<String>()
    val run = StringBuilder()
    run.append(s[0])
    for (i in 1 until s.length) {
        if (s[i] != s[i - 1]) {
            result.add(run.toString())
            run.clear()
        }
        run.append(s[i])
    }
    result.add(run.toString())

    return result
}

// every char count times, in the given order: expand([e to 2, t to 1]) = "eet"
fun expand(counts: List<Pair<Char, Int>>): String {
    val result = StringBuilder()
    for ((char, count) in counts) {
        repeat(count) {
            result.append(char)
        }
    }
    return result.toString()
}
