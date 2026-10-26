package toolbox.array

/*
Double array trick
Looking circularly from an index is the same as looking to the right in nums + nums: after the end, the copy
continues with nums[0], nums[1], ... So a circular problem becomes a normal left-to-right one, and no index ever
has to wrap around.

[1, 2, 3, 4] -> [1, 2, 3, 4, 1, 2, 3, 4]: the 3 elements starting at index 3 are simply doubled[3..5] = [4, 1, 2].

When to reach for it: circular problems where a linear algorithm (scan to the right, a window) already works on a
normal array. The copy costs O(n) space. To save it, map the indices back with i mod n instead (CyclicIndex.kt).

Seen in:
    questions/array/next_greater_element/solution/NextGreaterElementDoubleArray.kt

n: number of elements
T: O(n) to build the doubled array
S: O(n) - the doubled array
 */

fun doubled(numbers: IntArray): IntArray = numbers + numbers

// length elements starting at start, wrapping around: [1, 2, 3, 4], start = 3, length = 3 -> [4, 1, 2]
// works for start in 0 until n and length <= n, the doubled array only holds one extra round
fun circularSlice(numbers: IntArray, start: Int, length: Int): IntArray =
    doubled(numbers).copyOfRange(start, start + length)
