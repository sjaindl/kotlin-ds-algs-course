package questions.string.frequency_sort.solution

/*
No counting needed: sorting the characters puts equal characters next to each other, so the sorted string can be
cut into runs of one character each. The length of a run is the frequency of its character, so sorting the runs by
length (longest first) and joining them gives the answer.

"tree" -> sorted "eert" -> runs ["ee", "r", "t"] -> by length ["ee", "r", "t"] -> "eert"

n: length of s, k: number of distinct characters
T: O(n log n) - sorting all characters
S: O(n) - the sorted characters and the runs

chars = characters of s sorted
runs = []
run = chars[0]
for i from 1 to n - 1:
    if chars[i] != chars[i - 1]:
        add run to runs
        run = empty
    append chars[i] to run
add run to runs
sort runs by length descending
return runs joined
 */

fun frequencySortBruteforce(s: String): String {
    if (s.isEmpty()) return s

    val chars = s.toCharArray()
    chars.sort()

    // cut the sorted characters into runs of equal characters
    val runs = mutableListOf<String>()
    val run = StringBuilder()
    run.append(chars[0])
    for (i in 1 until chars.size) {
        if (chars[i] != chars[i - 1]) {
            runs.add(run.toString())
            run.clear()
        }
        run.append(chars[i])
    }
    runs.add(run.toString())

    return runs.sortedByDescending {
        it.length
    }.joinToString("")
}
