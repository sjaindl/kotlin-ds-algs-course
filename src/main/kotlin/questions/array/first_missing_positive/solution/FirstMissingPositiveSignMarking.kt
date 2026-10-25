package questions.array.first_missing_positive.solution

import kotlin.math.abs

/*
Same idea as the extra space approach, but numbers itself is the seen array: the index is the hash key and a negative
sign means "this value is present". Values 1..n - 1 are marked at their own index, value n at index 0.
First replace everything outside 1..n with 1, so every value is a valid index and the sign is free to use.
This is only safe if 1 itself is present, so check for 1 first.

Example: [3, 4, -1, 1], n = 4
    contains 1, replace -1 with 1        → [3, 4, 1, 1]
    3 → negate numbers[3]                → [3, 4, 1, -1]
    4 → n, negate numbers[0]             → [-3, 4, 1, -1]
    1 → negate numbers[1]                → [-3, -4, 1, -1]
    1 → numbers[1] is already negative   → [-3, -4, 1, -1]
    numbers[2] is positive               → 2 is missing

n: number of elements
T: O(n) - three passes
S: O(1) - numbers is modified in place

if 1 is not in numbers:
    return 1
replace values <= 0 or > n with 1
for value in numbers:
    index = abs(value) mod n           // value n is stored at index 0
    numbers[index] = -abs(numbers[index])
for index from 1 to n - 1:
    if numbers[index] > 0:
        return index
if numbers[0] > 0:
    return n
return n + 1
 */

fun firstMissingPositiveSignMarking(numbers: IntArray): Int {
    var containsNumberOne = false

    for (index in numbers.indices) {
        if (numbers[index] == 1) {
            containsNumberOne = true
        }
        if (numbers[index] !in 1..numbers.size) {
            numbers[index] = 1
        }
    }

    if (!containsNumberOne) return 1

    for (index in numbers.indices) {
        val value = abs(numbers[index])
        val target = if (value == numbers.size) 0 else value
        numbers[target] = -abs(numbers[target])
    }

    for (index in 1 until numbers.size) {
        if (numbers[index] > 0) return index
    }

    if (numbers[0] > 0) return numbers.size

    return numbers.size + 1
}
