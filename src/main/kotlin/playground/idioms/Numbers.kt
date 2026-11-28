package playground.idioms

import kotlin.math.abs

/*
Numbers
Int.MAX_VALUE = 2_147_483_647, Int.MIN_VALUE = -2_147_483_648, Long.MAX_VALUE ≈ 9.2e18
a.toLong() * b                  multiply as Long: the conversion must come before the multiplication
7 / 2 = 3, -7 / 2 = -3          integer division rounds toward 0
-7 % 3 = -1, (-7).mod(3) = 2    % keeps the sign of the left side, mod() is always >= 0 for a positive divisor
abs(x), maxOf(a, b, c), minOf(a, b)
1 shl k, x shr 1, x ushr 1      shifts (no << / >> in Kotlin)
a and b, a or b, a xor b, x.inv()
x.countOneBits(), x.takeHighestOneBit()
(a + b - 1) / b                 ceiling division for positive numbers

Kotlin pitfalls:
- Int overflow is silent: Int.MAX_VALUE + 1 is Int.MIN_VALUE, no exception.
- abs(Int.MIN_VALUE) is still Int.MIN_VALUE, negative. Use abs(x.toLong()).
- (lo + hi) / 2 can overflow for large indices, lo + (hi - lo) / 2 cannot.
- a.toLong() * b is a Long, (a * b).toLong() is the already-overflowed Int.

T: O(1)
S: O(1)
 */

// the middle of [lo, hi] without overflow, rounded down
fun middle(lo: Int, hi: Int): Int = lo + (hi - lo) / 2

// true if x is a power of two: exactly one bit is set
fun isPowerOfTwo(x: Int): Boolean = x > 0 && (x and (x - 1)) == 0

// the number that occurs once when every other number occurs twice: a xor a = 0
fun singleNumber(numbers: IntArray): Int = numbers.fold(0) { acc, x ->
    acc xor x
}

// the absolute difference of two Ints, as a Long so that it cannot overflow
fun distance(a: Int, b: Int): Long = abs(a.toLong() - b)

// ceil(a / b) for a >= 0 and b > 0, e.g. the number of pages for a items with b per page
fun ceilDiv(a: Int, b: Int): Int = (a + b - 1) / b
