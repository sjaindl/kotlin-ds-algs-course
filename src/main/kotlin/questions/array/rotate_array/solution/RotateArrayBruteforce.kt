package questions.array.rotate_array.solution

/*
Rotate by one step, k times: every element moves one index to the right, the last one wraps around to the front.
Rotating n times gives the original array again, so only k mod n passes are needed.

n: number of elements
T: O(n * k)
S: O(1)

repeat k mod n times:
    last = nums[n - 1]
    for each index:
        swap last and nums[index]
 */

fun rotateBruteforce(nums: IntArray, k: Int) {
    val shift = k % nums.size

    repeat(shift) {
        var last = nums[nums.size - 1]
        for (index in nums.indices) {
            val temp = nums[index]
            nums[index] = last
            last = temp
        }
    }
}
