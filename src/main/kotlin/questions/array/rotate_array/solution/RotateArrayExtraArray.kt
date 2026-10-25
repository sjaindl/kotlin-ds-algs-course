package questions.array.rotate_array.solution

/*
Copy the array, then write every element from the copy to its final index (index + k) mod n.

n: number of elements
T: O(n)
S: O(n) - the copy

copy = nums.copy()
for each index:
    nums[(index + k) mod n] = copy[index]
 */

fun rotateExtraArray(nums: IntArray, k: Int) {
    if (nums.size < 2) return

    val copy = nums.copyOf()

    for (index in nums.indices) {
        val offsetIndex = (index + k) % nums.size
        nums[offsetIndex] = copy[index]
    }
}
