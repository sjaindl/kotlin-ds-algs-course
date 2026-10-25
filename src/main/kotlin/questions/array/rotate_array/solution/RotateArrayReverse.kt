package questions.array.rotate_array.solution

/*
Rotating right by k moves the last k elements to the front. Reversing the whole array brings them to the front
(but in reverse order), so reversing both parts again restores their original order.

n=7, k=3
Original list                    : 1 2 3 4 5 6 7
After reversing all numbers      : 7 6 5 4 3 2 1
After reversing first k numbers  : 5 6 7 4 3 2 1
After reversing last n-k numbers : 5 6 7 1 2 3 4 --> Result

n: number of elements
T: O(n)
S: O(1)

shift = k mod n
reverse(0, n - 1)
reverse(0, shift - 1)
reverse(shift, n - 1)

reverse(from, to):
    while from < to:
        swap nums[from] and nums[to]
        from++, to--
 */

fun rotateReverse(nums: IntArray, k: Int) {
    val shift = k % nums.size

    reverse(nums, 0, nums.size - 1)
    reverse(nums, 0, shift - 1)
    reverse(nums, shift, nums.size - 1)
}

private fun reverse(nums: IntArray, from: Int, to: Int) {
    var start = from
    var end = to

    while (start < end) {
        val temp = nums[start]
        nums[start] = nums[end]
        nums[end] = temp
        start++
        end--
    }
}
