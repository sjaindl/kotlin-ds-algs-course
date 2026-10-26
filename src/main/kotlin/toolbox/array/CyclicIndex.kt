package toolbox.array

/*
Cyclic arrays (modulo)
Treat the array as a circle: after the last index comes index 0 again. index mod n maps any index, also one past
the end or a negative one, back into 0 until n. Moving by k steps in a circle of n is the same as moving by
k mod n steps, so the same function also reduces a shift k (k > n, or negative = the other way).

When to reach for it: "circular array", "wrap around", rotations, anything where the end connects to the start.

Seen in:
    questions/array/rotate_array/solution/RotateArrayExtraArray.kt          (index + k) mod n
    questions/array/rotate_array/solution/RotateArrayCyclicReplacements.kt   k mod n, following cycles
    questions/array/next_greater_element/solution/NextGreaterElementBruteforce.kt  scan circularly

Kotlin: % keeps the sign of the left side (-1 % 5 == -1), .mod() is always in 0 until n (-1.mod(5) == 4).

Following index -> (index + k) mod n from every start splits the indices into gcd(n, k) cycles,
e.g. n = 4, k = 2: 0 -> 2 -> 0 and 1 -> 3 -> 1.

n: number of elements
T: O(1) per index
S: O(1)
 */

// maps an index or a shift into 0 until n: circularIndex(-1, 5) = 4, circularIndex(10, 3) = 1
fun circularIndex(index: Int, n: Int): Int = index.mod(n)

// the other n - 1 indices in circular order, starting right after index: n = 5, index = 3 -> [4, 0, 1, 2]
fun nextIndicesCircular(index: Int, n: Int): List<Int> =
    (index + 1 until index + n).map { nextIndex ->
        circularIndex(index = nextIndex, n = n)
    }
