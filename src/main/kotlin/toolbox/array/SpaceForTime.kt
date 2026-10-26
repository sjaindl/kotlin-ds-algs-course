package toolbox.array

/*
Trade space for time
Spend O(n) extra memory to avoid repeated work: remember what was seen instead of searching again, or write into
a copy instead of shuffling values around in place.
For values in a small known range 0..maxValue, a BooleanArray is a simpler and faster "set" than a HashSet.

Trades in the array lecture:
    first missing positive   O(n^2) time, O(1) space  ->  O(n) time, O(n) space   presence array
    rotate array             O(n * k) time, O(1) space ->  O(n) time, O(n) space   write into a copy
    next greater element     O(n^2) time, O(1) space  ->  O(n) time, O(n) space   stack (stack lectures)

When to reach for it: a brute force searches the same data again and again, or values have to be moved to
positions that are still needed. Then try to get the space back with an in-place trick (see InPlace.kt).

Seen in:
    questions/array/first_missing_positive/solution/FirstMissingPositiveExtraSpace.kt
    questions/array/rotate_array/solution/RotateArrayExtraArray.kt
    questions/array/next_greater_element/solution/NextGreaterElementMonotonicStack.kt

n: number of elements
T: O(n + maxValue) for presence, O(n) for placeAt
S: O(maxValue) for presence, O(n) for placeAt
 */

// seen[v] is true if v occurs in numbers, values outside 0..maxValue are ignored
fun presence(numbers: IntArray, maxValue: Int): BooleanArray {
    val seen = BooleanArray(maxValue + 1)

    for (num in numbers) {
        if (num in 0..maxValue) {
            seen[num] = true
        }
    }

    return seen
}

// moves every element to target(index) using a copy, target must hit every index exactly once
fun placeAt(numbers: IntArray, target: (index: Int) -> Int): IntArray {
    val result = IntArray(numbers.size)

    for (index in numbers.indices) {
        result[target(index)] = numbers[index]
    }

    return result
}
