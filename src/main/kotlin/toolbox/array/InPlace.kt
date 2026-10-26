package toolbox.array

import kotlin.math.abs

/*
In-place mutation
Get O(1) extra space by reusing the input array itself:
- swap two elements instead of building a new array
- sign marking: when all values are positive, the sign is a free bit per index. Index = key, negative = "seen".
  Read values with abs(), because a cell may already be marked.

Changing the input is a side effect: the caller sees it. If the input must stay as it is, work on nums.copyOf()
(and pay O(n) space again), and say in the signature or the comment that the function mutates.

When to reach for it: "O(1) extra space" / "in place" in the task, after an extra-array solution works.

Seen in:
    questions/array/first_missing_positive/solution/FirstMissingPositiveSignMarking.kt  sign marking
    questions/array/rotate_array/solution/RotateArrayReverse.kt                        swaps

n: number of elements
T: O(1) per swap / mark
S: O(1)
 */

// marks value 1..n at index value - 1, numbers must only hold values 1..n (see BoundTheAnswer.kt)
fun markPresent(numbers: IntArray, value: Int) {
    val index = abs(value) - 1
    numbers[index] = -abs(numbers[index])
}

fun isPresent(numbers: IntArray, value: Int): Boolean = numbers[value - 1] < 0
