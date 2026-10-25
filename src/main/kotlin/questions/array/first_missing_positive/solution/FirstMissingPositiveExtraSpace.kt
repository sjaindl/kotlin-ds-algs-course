package questions.array.first_missing_positive.solution

/*
The answer is always in 1..n + 1, so only the values 1..n matter. Mark each of them in a boolean array, then the
first unmarked value is the answer. If all of 1..n are marked, it is n + 1.

Example: [3, 4, -1, 1] → seen[1] and seen[3] are marked (4 > n is skipped, -1 is not positive) → 2 is missing.

n: number of elements
T: O(n)
S: O(n) - the seen array

seen = [false] * (n + 1)
for num in nums:
    if 1 <= num <= n:
        seen[num] = true
for value from 1 to n:
    if not seen[value]:
        return value
return n + 1
 */

fun firstMissingPositiveExtraSpace(numbers: IntArray): Int {
    val seen = BooleanArray(numbers.size + 1)

    for (num in numbers) {
        if (num > 0 && num < seen.size) {
            seen[num] = true
        }
    }

    for (value in 1 until seen.size) {
        if (!seen[value]) return value
    }

    return numbers.size + 1
}
