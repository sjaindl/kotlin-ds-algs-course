package playground.array

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ArrayPlaygroundTest {

    @Test
    fun `grid of rows`() {
        val board = grid(2, 3, value = 7)
        assertEquals(2, board.size)
        assertContentEquals(intArrayOf(7, 7, 7), board[1])
        board[0][0] = 1
        assertEquals(7, board[1][0]) // rows are separate objects
        assertEquals(0, grid(0, 5).size) // no rows
        assertContentEquals(IntArray(0), grid(1, 0)[0]) // empty row
    }

    @Test
    fun `deep copy`() {
        val board = grid(2, 2)
        val copy = board.deepCopy()
        copy[0][0] = 9
        assertEquals(0, board[0][0]) // rows are not shared
        val shallow = board.copyOf()
        shallow[0][0] = 9
        assertEquals(9, board[0][0]) // copyOf shares the rows
    }

    @Test
    fun `same grid`() {
        assertTrue(sameGrid(grid(2, 2, 1), grid(2, 2, 1)))
        assertFalse(grid(2, 2, 1).contentEquals(grid(2, 2, 1))) // contentEquals compares the rows by reference
        assertFalse(sameGrid(grid(2, 2, 1), grid(2, 3, 1)))
        assertFalse(intArrayOf(1) == intArrayOf(1)) // == on arrays compares references
    }

    @Test
    fun `insertion point`() {
        val sorted = intArrayOf(1, 3, 5)
        assertEquals(1, insertionPoint(sorted, 3)) // found
        assertEquals(2, insertionPoint(sorted, 4)) // between 3 and 5
        assertEquals(0, insertionPoint(sorted, -10)) // before all
        assertEquals(3, insertionPoint(sorted, 10)) // after all
        assertEquals(0, insertionPoint(IntArray(0), 1)) // empty
    }

    @Test
    fun `index of max`() {
        assertEquals(2, indexOfMax(intArrayOf(5, 1, 9, 2)))
        assertEquals(0, indexOfMax(intArrayOf(4, 4))) // tie: the first one
        assertEquals(-1, indexOfMax(IntArray(0))) // empty
    }

    @Test
    fun `long sum`() {
        assertEquals(2L * Int.MAX_VALUE, longSum(intArrayOf(Int.MAX_VALUE, Int.MAX_VALUE)))
        assertEquals(-2, intArrayOf(Int.MAX_VALUE, Int.MAX_VALUE).sum()) // sum() overflows
        assertEquals(0L, longSum(IntArray(0)))
    }

    @Test
    fun `prefix sums`() {
        val prefix = prefixSums(intArrayOf(3, 1, 4))
        assertContentEquals(longArrayOf(0, 3, 4, 8), prefix)
        assertEquals(5L, rangeSum(prefix, 1, 3))
        assertEquals(0L, rangeSum(prefix, 2, 2)) // empty range
        assertEquals(8L, rangeSum(prefix, 0, 3)) // whole array
        assertContentEquals(longArrayOf(0), prefixSums(IntArray(0)))
        assertEquals(listOf(0, 3, 4, 8), prefixSumsStdlib(intArrayOf(3, 1, 4)))
    }
}
