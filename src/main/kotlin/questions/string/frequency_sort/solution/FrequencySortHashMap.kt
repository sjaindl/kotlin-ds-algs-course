package questions.string.frequency_sort.solution

/*
Count every character in a hash map, then sort only the k distinct characters by count instead of all n of them.
Append each character count times to a StringBuilder (O(1) amortized per append; += on a String would copy it every
time). Here k <= 62 (letters and digits), so the sort is effectively constant.

"tree" -> counts {t: 1, r: 1, e: 2} -> sorted [e: 2, t: 1, r: 1] -> "eetr"

n: length of s, k: number of distinct characters
T: O(n + k log k)
S: O(n) - the result, plus O(k) for the map

counts = count of every character in s
entries = counts sorted by count descending
result = empty
for each (char, count) in entries:
    append char count times to result
return result
 */

fun frequencySortHashMap(s: String): String {
    val counts = mutableMapOf<Char, Int>()
    for (char in s) {
        counts[char] = counts.getOrDefault(char, 0) + 1
    }

    val sorted = counts.entries.sortedByDescending {
        it.value
    }

    val result = StringBuilder(s.length)
    for ((char, count) in sorted) {
        repeat(count) {
            result.append(char)
        }
    }

    return result.toString()
}
