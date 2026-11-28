package playground.array

/*
Everyday array operations
arr.size, arr.indices (0..size-1), arr.lastIndex (size - 1)
arr.sum(), arr.max(), arr.min(), arr.average()        max()/min() throw on an empty array, use maxOrNull()
for ((i, x) in arr.withIndex())                       index and value together
arr.sort(), arr.sortDescending()                      in place, O(n log n)
arr.sorted(), arr.reversed()                          return a new List<Int>, the array stays as it is
arr.reverse()                                         in place
arr.binarySearch(x)                                   on a sorted array: index of x, or -(insertion point) - 1
arr.indexOf(x), x in arr                              linear search, O(n)

Kotlin pitfalls:
- sort() vs sorted(): the -ed versions return a copy as a List, the others change the array.
- binarySearch on an unsorted array gives a meaningless result, no exception.
- arr.sum() of large Ints overflows silently, use arr.sumOf { it.toLong() }.

See toolbox: toolbox/array/ReverseTrick.kt for IntArray.swap and reverseRange.

n: number of elements
T: O(n) for sum / min / max / reverse, O(n log n) for sort, O(log n) for binarySearch
S: O(1) in place, O(n) for the -ed copies
 */

// where x is or would be inserted to keep the array sorted (the "lower bound"), for distinct values
fun insertionPoint(sorted: IntArray, x: Int): Int {
    val index = sorted.binarySearch(x)
    return if (index >= 0) index else -(index + 1)
}

// the index of the largest value, -1 for an empty array
fun indexOfMax(numbers: IntArray): Int = numbers.indices.maxByOrNull {
    numbers[it]
} ?: -1

// the sum as a Long, so large arrays cannot overflow
fun longSum(numbers: IntArray): Long = numbers.sumOf {
    it.toLong()
}
