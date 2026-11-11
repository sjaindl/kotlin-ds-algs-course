package questions.string.unique_letter_string.solution

/*
Turn the question around: instead of asking every substring for its unique characters, ask every occurrence in how
many substrings it is the only copy of its character. That's the case when the substring starts after the previous
occurrence (prev) and ends before the next one (next): (index - prev) choices for the start times
(next - index) choices for the end. Use -1 and s.length when there is no previous / next occurrence.

"LEETCODE": the 'E's at 1, 2, 7
    E at 1: prev -1, next 2 -> (1 - -1) * (2 - 1) = 2
    E at 2: prev  1, next 7 -> (2 -  1) * (7 - 2) = 5
    E at 7: prev  2, next 8 -> (7 -  2) * (8 - 7) = 5
    L at 0: (0 - -1) * (8 - 0) = 8, and so on, 92 in total

n: length of s
T: O(n) - collect the indices, then one product per index
S: O(n) - all indices

indices[c] = list of the indices of c
count = 0
for each list of indices:
    for each position p in the list:
        prev = list[p - 1] or -1, next = list[p + 1] or n
        count += (list[p] - prev) * (next - list[p])
return count
 */

fun uniqueLetterStringCharSplit(s: String): Int {
    var count = 0

    val indexMap = mutableMapOf<Char, MutableList<Int>>()

    s.forEachIndexed { index, char ->
        val list = indexMap.getOrDefault(key = char, defaultValue = mutableListOf()).apply {
            add(index)
        }
        indexMap[char] = list
    }

    indexMap.values.forEach { list ->
        list.forEachIndexed { listIndex, charIndex ->
            val leftBoundary = if (listIndex == 0) -1 else list[listIndex - 1]
            val rightBoundary = if (listIndex == list.size - 1) s.length else list[listIndex + 1]

            count += (charIndex - leftBoundary) * (rightBoundary - charIndex)
        }
    }

    return count
}
