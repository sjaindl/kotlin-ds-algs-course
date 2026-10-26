# Common bugs in array questions

Each entry in this list first shows a common bug, with a wrong snippet and a fixed one.

## 1. Off-by-one error in loop bounds

`0..n` includes `n`, but the last valid index is `n - 1`. The loop reads one element past the end and throws
`ArrayIndexOutOfBoundsException`.

**Wrong**
```kotlin
for (index in 0..nums.size) {
    sum += nums[index]
}
```

**Correct**
```kotlin
for (index in nums.indices) { // same as 0 until nums.size
    sum += nums[index]
}
```

## 2. Off-by-one when looking at a neighbour

Reading `nums[index + 1]` (or `nums[index - 1]`) needs a shorter range, otherwise the last (or first) iteration
leaves the array.

Seen in: `questions/array/max_profit/solution/MaxProfitGreedy.kt`

**Wrong**
```kotlin
for (day in prices.indices) {
    if (prices[day] > prices[day - 1]) {
        profit += prices[day] - prices[day - 1]
    }
}
```

**Correct**
```kotlin
for (day in 1 until prices.size) {
    if (prices[day] > prices[day - 1]) {
        profit += prices[day] - prices[day - 1]
    }
}
```

## 3. Forgetting the empty or single-element array

`nums[0]`, `nums.max()` or `nums.size - 1` assume at least one element. Check the constraints: if `n = 0` is
allowed, handle it first.

**Wrong**
```kotlin
fun largest(nums: IntArray): Int {
    var max = nums[0] // crashes for []
    for (value in nums) {
        max = maxOf(max, value)
    }
    return max
}
```

**Correct**
```kotlin
fun largest(nums: IntArray): Int? {
    if (nums.isEmpty()) return null

    var max = nums[0]
    for (value in nums) {
        max = maxOf(max, value)
    }
    return max
}
```

## 4. Negative modulo

In Kotlin `%` keeps the sign of the left side: `-1 % 5 == -1`. Stepping backwards in a circular array then gives
a negative index. `.mod(n)` always returns a value in `0 until n`.

Seen in: `toolbox/array/CyclicIndex.kt`

**Wrong**
```kotlin
val previous = (index - 1) % nums.size // index = 0 -> -1
```

**Correct**
```kotlin
val previous = (index - 1).mod(nums.size) // index = 0 -> nums.size - 1
```

## 5. Shift larger than the array

Rotating by `k` where `k > n` either reads out of bounds or does needless full turns. Reduce it with `k % n`
first, and guard `n = 0` because `% 0` throws `ArithmeticException`.

Seen in: `questions/array/rotate_array/solution/RotateArrayReverse.kt`

**Wrong**
```kotlin
fun rotate(nums: IntArray, k: Int) {
    nums.reverse()
    nums.reverse(0, k) // k = 10, n = 3 -> IndexOutOfBoundsException
    nums.reverse(k, nums.size)
}
```

**Correct**
```kotlin
fun rotate(nums: IntArray, k: Int) {
    if (nums.isEmpty()) return

    val shift = k % nums.size
    nums.reverse()
    nums.reverse(0, shift)
    nums.reverse(shift, nums.size)
}
```

## 6. Integer overflow

`Int` ends at `2^31 - 1`. Adding two large values silently wraps around to a negative number, no exception. Watch
out for midpoints, sums and products with values at the constraint limits.

**Wrong**
```kotlin
val middle = (low + high) / 2          // low = high = Int.MAX_VALUE -> -1
var sum = 0                            // overflows for [Int.MAX_VALUE, 1]
for (value in nums) {
    sum += value
}
```

**Correct**
```kotlin
val middle = low + (high - low) / 2
var sum = 0L
for (value in nums) {
    sum += value
}
```

## 7. Wrong start value for min / max

Starting a maximum at `0` silently assumes a non-negative answer. For `[-3, -1, -2]` the result is `0`, which isn't
even in the array.

**Wrong**
```kotlin
var max = 0
for (value in nums) {
    max = maxOf(max, value)
}
```

**Correct**
```kotlin
var max = Int.MIN_VALUE // or nums[0] after the empty check
for (value in nums) {
    max = maxOf(max, value)
}
```

## 8. Overwriting a value before reading it

When shifting in place, writing in the direction of the shift overwrites the next value you still need. Every
element after the first becomes a copy of it.

Seen in: `questions/array/rotate_array/solution/RotateArrayBruteforce.kt`

**Wrong**
```kotlin
// shift right by one: [1, 2, 3] -> [3, 1, 1]
val last = nums[nums.size - 1]
for (index in 1 until nums.size) {
    nums[index] = nums[index - 1]
}
nums[0] = last
```

**Correct**
```kotlin
// shift right by one: [1, 2, 3] -> [3, 1, 2]
val last = nums[nums.size - 1]
for (index in nums.size - 1 downTo 1) {
    nums[index] = nums[index - 1]
}
nums[0] = last
```

## 9. Reading a marked value without `abs()`

With sign marking, a cell may already be negative when you reach it. Using it directly as an index gives a
negative index.

Seen in: `questions/array/first_missing_positive/solution/FirstMissingPositiveSignMarking.kt`,
`toolbox/array/InPlace.kt`

**Wrong**
```kotlin
for (index in numbers.indices) {
    val target = numbers[index] - 1 // may already be marked: -3 - 1 = -4
    numbers[target] = -abs(numbers[target])
}
```

**Correct**
```kotlin
for (index in numbers.indices) {
    val target = abs(numbers[index]) - 1
    numbers[target] = -abs(numbers[target])
}
```

## 10. Modifying a list while iterating over it

Removing from a `MutableList` inside a `for` loop over the same list usually throws `ConcurrentModificationException`.
Removing by index going forwards skips the element that moves into the freed slot.

**Wrong**
```kotlin
for (value in list) {
    if (value < 0) {
        list.remove(value)
    }
}
```

**Correct**
```kotlin
list.removeAll { value -> value < 0 }
// or, by index: walk backwards so removals don't shift what is still ahead
for (index in list.indices.reversed()) {
    if (list[index] < 0) {
        list.removeAt(index)
    }
}
```

## 11. Aliasing instead of copying

`val copy = nums` doesn't copy the array, both names point to the same one. Changing `copy` changes `nums`, e.g. when
running several approaches on the same input.

Seen in: `questions/array/rotate_array/solution/RotateArrayRunner.kt`

**Wrong**
```kotlin
val original = nums
rotate(nums, k)
println(original.contentToString()) // already rotated
```

**Correct**
```kotlin
val original = nums.copyOf()
rotate(nums, k)
println(original.contentToString()) // still the input
```

## 12. `==` and `toString()` on arrays

Arrays don't override `equals` and `toString`: `==` compares references and `println(nums)` prints something like
`[I@1b6d3586`. Use the `content…` functions.

**Wrong**
```kotlin
if (result == intArrayOf(3, 1, 2)) {
    println("ok: $result")
}
```

**Correct**
```kotlin
if (result.contentEquals(intArrayOf(3, 1, 2))) {
    println("ok: ${result.contentToString()}")
}
```

## 13. Assuming a single cycle

Following `index -> (index + k) mod n` returns to the start after `n / gcd(n, k)` steps. If `n` and `k` share a
divisor, one cycle doesn't visit every index. Count the moved elements instead of stopping after the first cycle.

Seen in: `questions/array/rotate_array/solution/RotateArrayCyclicReplacements.kt`

**Wrong**
```kotlin
// n = 4, k = 2: only moves indices 0 and 2
var fromIndex = 0
var fromValue = nums[0]
do {
    val toIndex = (fromIndex + shift) % nums.size
    val temp = nums[toIndex]
    nums[toIndex] = fromValue
    fromValue = temp
    fromIndex = toIndex
} while (fromIndex != 0)
```

**Correct**
```kotlin
var moved = 0
var startIndex = 0
while (moved < nums.size) {
    var fromIndex = startIndex
    var fromValue = nums[startIndex]
    do {
        val toIndex = (fromIndex + shift) % nums.size
        val temp = nums[toIndex]
        nums[toIndex] = fromValue
        fromValue = temp
        fromIndex = toIndex
        moved++
    } while (fromIndex != startIndex)
    startIndex++
}
```

## 14. Two pointers that never meet

A two-pointer loop must move at least one pointer in every iteration, and the loop condition must match what the
pointers mean. `left <= right` swaps the middle element with itself (harmless), but forgetting to move a pointer
gives a wrong result or, in a `while (left != right)` loop, never ends.

Seen in: `toolbox/array/ReverseTrick.kt`

**Wrong**
```kotlin
var left = 0
var right = nums.size - 1
while (left < right) {
    val temp = nums[left]
    nums[left] = nums[right]
    nums[right] = temp
    left++ // right is never moved: [1, 2, 3] -> [3, 1, 2]
}
```

**Correct**
```kotlin
var left = 0
var right = nums.size - 1
while (left < right) {
    val temp = nums[left]
    nums[left] = nums[right]
    nums[right] = temp
    left++
    right--
}
```

## 15. `<` vs `<=` with equal values

Duplicates and plateaus decide whether a comparison must be strict. "Next **greater**" means an equal value is not
an answer, so it must be popped from the stack as well.

Seen in: `questions/array/next_greater_element/solution/NextGreaterElementMonotonicStack.kt`

**Wrong**
```kotlin
// [1, 1] -> [1, 1], but 1 is not greater than 1
while (stack.isNotEmpty() && stack.last() < nums[index]) {
    stack.removeLast()
}
```

**Correct**
```kotlin
// [1, 1] -> [-1, -1]
while (stack.isNotEmpty() && stack.last() <= nums[index]) {
    stack.removeLast()
}
```
