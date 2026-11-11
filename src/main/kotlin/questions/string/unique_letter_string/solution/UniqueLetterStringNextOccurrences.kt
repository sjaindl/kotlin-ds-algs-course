package questions.string.unique_letter_string.solution

/*
Fix the start index of the substrings. A character c is unique in s[start..end] exactly when end lies between the
next occurrence of c (at or after start) and the one after it. So next2 - next1 substrings starting at start have c
as unique character. Summing that over all characters and all start indices gives the answer.
Keep the indices of every character in a queue: the head is always next1, the one after it next2. Two sentinels
s.length at the end of each queue mean "no further occurrence".

"ABA", start 0: A at [0, 2] -> 2 - 0 = 2 ("A", "AB"), B at [1, 3] -> 3 - 1 = 2 ("AB", "ABA") -> 4
       start 1: A at [2, 3] -> 1 ("BA"),           B at [1, 3] -> 2 ("B", "BA")      -> 3
       start 2: A at [2, 3] -> 1 ("A"),            B at [3, 3] -> 0                   -> 1, total 8

Pitfall: removing the head with remove(index) on an ArrayList shifts all remaining elements, which makes
"AAAA..." O(n²). ArrayDeque.removeFirst() is O(1).

n: length of s, k: number of distinct characters (at most 26)
T: O(n · k) = O(n)
S: O(n) - all indices

indices[c] = queue of the indices of c, followed by n, n
count = 0
for start from 0 to n - 1:
    for each character c:
        count += indices[c][1] - indices[c][0]
    remove the head of indices[s[start]]
return count
 */

fun uniqueLetterStringNextOccurrences(s: String): Int {
    var count = 0

    val charIndexMap = mutableMapOf<Char, ArrayDeque<Int>>()
    s.forEachIndexed { index, char ->
        charIndexMap.getOrPut(char) {
            ArrayDeque()
        }.add(index)
    }

    // two sentinels per character, so indices[1] always exists
    charIndexMap.values.forEach { indices ->
        indices.add(s.length)
        indices.add(s.length)
    }

    for (index in s.indices) {
        for (charIndices in charIndexMap.values) {
            val uniqueContributingDistance = charIndices[1] - charIndices[0]
            count += uniqueContributingDistance
        }

        // index is the head of its character's queue, the next start doesn't include it anymore
        charIndexMap.getValue(s[index]).removeFirst()
    }

    return count
}
