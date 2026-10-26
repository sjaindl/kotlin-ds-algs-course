package toolbox.array

/*
Reverse trick
Reversing a range in place takes two pointers that swap and move inward until they meet. Rotations are three
reversals: reversing everything brings the last k elements to the front (in the wrong order), reversing each part
again fixes the order inside the parts.

[1, 2, 3, 4, 5, 6, 7], rotate right by 3
reverse all          -> [7, 6, 5, 4, 3, 2, 1]
reverse first 3      -> [5, 6, 7, 4, 3, 2, 1]
reverse last 4       -> [5, 6, 7, 1, 2, 3, 4]

When to reach for it: rotations in O(1) space, reversing words in a sentence (reverse all, then each word),
palindromes and anything else that walks from both ends.

Kotlin: the stdlib has numbers.reverse(fromIndex, toIndex), but toIndex is exclusive. reverseRange below takes an
inclusive end like the course pseudocode.

Seen in:
    questions/array/rotate_array/solution/RotateArrayReverse.kt

n: number of elements
T: O(n) - every element is swapped at most twice
S: O(1)
 */

// reverses numbers[from..to] (both inclusive), an empty or single-element range is left as it is
fun IntArray.reverseRange(from: Int, to: Int) {
    var start = from
    var end = to

    while (start < end) {
        swap(start, end)
        start++
        end--
    }
}

fun rotateRight(numbers: IntArray, k: Int) {
    if (numbers.isEmpty()) return

    val shift = circularIndex(index = k, n = numbers.size)
    numbers.reverseRange(0, numbers.size - 1)
    numbers.reverseRange(0, shift - 1)
    numbers.reverseRange(shift, numbers.size - 1)
}

// rotating left by k is rotating right by n - k
fun rotateLeft(numbers: IntArray, k: Int) {
    rotateRight(numbers, -k)
}

fun IntArray.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}
