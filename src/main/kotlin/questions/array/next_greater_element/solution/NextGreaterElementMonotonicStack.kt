package questions.array.next_greater_element.solution

/*
Walk from right to left and keep a stack of candidates for "next greater", nearest on top.
Before answering for nums[index], pop every candidate <= nums[index]: it is not greater, and for every element
further left nums[index] comes first and is at least as big, so the popped value can never be an answer again.
What remains on top is the next greater number. The stack stays strictly decreasing from bottom to top.

A second pass over the array handles the circle: the stack then holds the elements from the start of the array,
as if they came after the end.

n: number of elements
T: O(n) - every element is pushed and popped at most twice
S: O(n) - the stack

result = [-1] * n
repeat 2 times:
    for index from n - 1 down to 0:
        while stack is not empty and top <= nums[index]:
            pop
        if stack is not empty:
            result[index] = top
        push nums[index]
 */

fun nextGreaterElementsMonotonicStack(nums: IntArray): IntArray {
    val stack = ArrayDeque<Int>()
    val result = IntArray(nums.size) { -1 }

    repeat(2) {
        for (index in nums.size - 1 downTo 0) {
            while (stack.isNotEmpty() && stack.last() <= nums[index]) {
                stack.removeLast()
            }

            if (stack.isNotEmpty()) {
                result[index] = stack.last()
            }

            stack.addLast(nums[index])
        }
    }

    return result
}
