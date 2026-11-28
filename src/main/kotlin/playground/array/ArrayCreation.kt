package playground.array

/*
Creating arrays
                                     result                    note
IntArray(3)                          [0, 0, 0]                 zero-filled, primitive int[]
IntArray(3) { it * it }              [0, 1, 4]                 the lambda gets the index
intArrayOf(5, 1, 4)                  [5, 1, 4]
BooleanArray(3), CharArray(3)        [false, ...], ['\u0000', ...]
Array(2) { IntArray(3) }             [[0, 0, 0], [0, 0, 0]]    2D grid: rows x cols
Array(3) { "" }                      ["", "", ""]              Array<T> for non-primitive types
arr.copyOf(), arr.copyOfRange(1, 3)                            new arrays, end index exclusive
arr.fill(-1)                                                   in place

IntArray vs Array<Int>: IntArray stores primitive ints. Array<Int> stores boxed Integer objects, which costs more
memory and time. Use IntArray, LongArray, ... for numbers.

Kotlin pitfalls:
- arrays do not override equals and toString: a == b compares references, println(arr) prints "[I@1b6d3586".
  Use a.contentEquals(b) and arr.contentToString() (2D: contentDeepEquals / contentDeepToString).
- Array(rows) { IntArray(cols) } creates a new row per index. Array(rows) { row } with one shared row would make
  every row the same object, so writing grid[0][0] would change all rows.

n: number of elements
T: O(n) to create, copy or fill
S: O(n)
 */

// a rows x cols grid, every cell set to value
fun grid(rows: Int, cols: Int, value: Int = 0): Array<IntArray> = Array(rows) {
    IntArray(cols) {
        value
    }
}

// a deep copy of a grid: copyOf() on the outer array alone would still share the rows
fun Array<IntArray>.deepCopy(): Array<IntArray> = Array(size) { row ->
    this[row].copyOf()
}

// content equality for 2D grids, the == on arrays only compares references
fun sameGrid(a: Array<IntArray>, b: Array<IntArray>): Boolean = a.contentDeepEquals(b)
