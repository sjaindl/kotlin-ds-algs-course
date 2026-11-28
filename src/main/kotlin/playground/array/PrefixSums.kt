package playground.array

/*
Running totals
runningFold(initial) { acc, x -> ... }   [initial, initial + a0, ...], size n + 1
runningReduce { acc, x -> ... }          [a0, a0 + a1, ...], size n
scan is the same as runningFold.

A prefix array with a leading 0 answers "sum of arr[from until to]" in O(1): prefix[to] - prefix[from].

[3, 1, 4] -> runningFold(0) = [0, 3, 4, 8] -> sum of index 1..2 = prefix[3] - prefix[1] = 5

Kotlin pitfall: runningFold returns a List<Int>, not an IntArray. Use .toIntArray() if you need one, or build the
prefix array by hand as below, which also lets you pick Long against overflow.

n: number of elements
T: O(n) to build, O(1) per range query
S: O(n)
 */

// prefix[i] = sum of the first i numbers, prefix[0] = 0
fun prefixSums(numbers: IntArray): LongArray {
    val prefix = LongArray(numbers.size + 1)
    for (i in numbers.indices) {
        prefix[i + 1] = prefix[i] + numbers[i]
    }
    return prefix
}

// sum of numbers[from until to], in O(1)
fun rangeSum(prefix: LongArray, from: Int, to: Int): Long = prefix[to] - prefix[from]

// the same prefix sums with the stdlib, as Ints
fun prefixSumsStdlib(numbers: IntArray): List<Int> = numbers.runningFold(0) { sum, x ->
    sum + x
}
