package playground.idioms

/*
Ranges and loops
0..n          0, 1, ..., n (inclusive)
0 until n     0, 1, ..., n - 1, same as 0..<n
n downTo 0    n, n - 1, ..., 0
0..n step 2   0, 2, 4, ...
x in 1..9     a range check, no loop
arr.indices, list.indices, s.indices
repeat(n) { }
list.forEach { }, list.forEachIndexed { i, x -> }, for ((i, x) in list.withIndex())
x.coerceIn(0, 9), x.coerceAtLeast(0), x.coerceAtMost(9)
outer@ for (...) { for (...) { if (...) break@outer } }    leave both loops

Kotlin pitfalls:
- 5..0 is an empty range, not a countdown. Use 5 downTo 0.
- for (i in 0..arr.size) runs one step too far. Use arr.indices or until.
- An empty range makes the loop body run 0 times, no exception: 0 until 0 is fine.
- return inside forEach { } returns from the whole function. return@forEach skips to the next element, like
  continue. There is no break in forEach: use a for loop when you need to stop early.

T: O(1) for in on a range, O(n) for a loop over it
S: O(1)
 */

// the row and column of the first cell that holds target, null if none: break@outer leaves both loops
fun find(grid: Array<IntArray>, target: Int): Pair<Int, Int>? {
    var found: Pair<Int, Int>? = null
    outer@ for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (grid[row][col] == target) {
                found = row to col
                break@outer
            }
        }
    }
    return found
}

// the indices of the non-negative numbers: return@forEachIndexed skips an element like continue
fun nonNegativeIndices(numbers: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    numbers.forEachIndexed { i, x ->
        if (x < 0) return@forEachIndexed
        result.add(i)
    }
    return result
}

// true if any number is negative: a plain return inside forEach leaves the whole function
fun hasNegative(numbers: List<Int>): Boolean {
    numbers.forEach {
        if (it < 0) return true
    }
    return false
}

// true if (row, col) lies inside a rows x cols grid
fun inBounds(row: Int, col: Int, rows: Int, cols: Int): Boolean = row in 0..<rows && col in 0..<cols

// the indices from n - 1 down to 0, every step-th one
fun countdown(n: Int, step: Int = 1): List<Int> = (n - 1 downTo 0 step step).toList()

// x clamped into [low, high]
fun clamp(x: Int, low: Int, high: Int): Int = x.coerceIn(low, high)
