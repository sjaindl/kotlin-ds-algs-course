package toolbox.array

/*
Bound the answer
Before searching, ask in which range the answer must lie. Often it is small and depends only on n, so all values
outside that range can be ignored or overwritten, and the range itself fits into an array of size n.

First missing positive: n numbers can cover at most 1..n, so the answer is in 1..n + 1 (pigeonhole).
Values <= 0 or > n can never change the answer, so they may be replaced, e.g. with 1 (if 1 is present).
That bound is what makes a presence array of size n + 1 (SpaceForTime.kt) or sign marking on nums (InPlace.kt)
possible at all.

When to reach for it: "smallest / first missing", "find the duplicate" with values in 1..n, counting problems
with a small value range.

Seen in:
    questions/array/first_missing_positive/solution/FirstMissingPositiveBruteforce.kt   candidates 1..n + 1
    questions/array/first_missing_positive/solution/FirstMissingPositiveExtraSpace.kt   seen array of size n + 1
    questions/array/first_missing_positive/solution/FirstMissingPositiveSignMarking.kt  replace out-of-range values

n: number of elements
T: O(n)
S: O(1)
 */

fun inRange(value: Int, n: Int): Boolean = value in 1..n

// replaces every value outside 1..n with replacement, returns how many were replaced
fun replaceOutOfRange(numbers: IntArray, n: Int, replacement: Int): Int {
    var replaced = 0

    for (index in numbers.indices) {
        if (!inRange(numbers[index], n)) {
            numbers[index] = replacement
            replaced++
        }
    }

    return replaced
}
