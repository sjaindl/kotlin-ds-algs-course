package big_o.space.runners

import big_o.space.reverseInPlace
import big_o.space.reversedCopy

fun main() {
    val nums = intArrayOf(1, 2, 3, 4, 5)

    reverseInPlace(nums = nums)
    println("reverseInPlace → ${nums.toList()}   (swaps in place: O(1) space)")

    // Same result, same runtime - but different space.
    val copy = reversedCopy(nums = intArrayOf(1, 2, 3, 4, 5))
    println("reversedCopy   → ${copy.toList()}   (allocates a 2nd array: O(n) space)")
}
