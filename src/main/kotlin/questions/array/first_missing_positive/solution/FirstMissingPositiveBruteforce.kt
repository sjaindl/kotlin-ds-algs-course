package questions.array.first_missing_positive.solution

/*
Try the candidates 1, 2, 3, … in order and scan the whole array for each one. The first candidate that is not
found is the answer. n numbers can cover at most 1..n, so the answer is at most n + 1.

n: number of elements
T: O(n^2) - up to n + 1 candidates, each scans all n elements
S: O(1)

for candidate from 1 to n + 1:
    if candidate is not in nums:
        return candidate
 */

fun firstMissingPositiveBruteforce(numbers: IntArray): Int {
    for (candidate in 1..numbers.size) {
        var found = false
        for (num in numbers) {
            if (num == candidate) {
                found = true
                break
            }
        }

        if (!found) return candidate
    }

    return numbers.size + 1
}
