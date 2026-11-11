package questions.string.frequency_sort.solution

/*
A count is always between 1 and n, so no comparison sort is needed: put every character into the bucket of its
count, buckets[count], and walk the buckets from n down to 1. This is O(n) no matter how many distinct
characters there are.

"tree" -> counts {t: 1, r: 1, e: 2} -> buckets [2: e] [1: t, r] -> "eetr"

n: length of s
T: O(n)
S: O(n) - n + 1 buckets and the result

counts = count of every character in s
buckets = n + 1 empty lists
for each (char, count) in counts:
    add char to buckets[count]
result = empty
for count from n down to 1:
    for each char in buckets[count]:
        append char count times to result
return result
 */

fun frequencySortBucket(s: String): String {
    // letters and digits are all ASCII, so the character code works as index
    val counts = IntArray(128)
    for (char in s) {
        counts[char.code]++
    }

    val buckets = Array(s.length + 1) {
        mutableListOf<Char>()
    }
    for (code in counts.indices) {
        if (counts[code] > 0) {
            buckets[counts[code]].add(code.toChar())
        }
    }

    val result = StringBuilder(s.length)
    for (count in s.length downTo 1) {
        for (char in buckets[count]) {
            repeat(count) {
                result.append(char)
            }
        }
    }

    return result.toString()
}
