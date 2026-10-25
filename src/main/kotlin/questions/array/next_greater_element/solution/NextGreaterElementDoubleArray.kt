package questions.array.next_greater_element.solution

/*
Searching circularly is the same as searching to the right in nums + nums: after the end of the array,
the copy continues with nums[0], nums[1], ... So every element scans to the right in the doubled array.

[1,2,1] -> [1,2,1,1,2,1]: the last 1 (index 2) finds the 2 at index 4.

n: number of elements
T: O(n^2)
S: O(n) - the doubled array

doubled = numbers + numbers
for each index in numbers:
    result[index] = -1
    for next in index + 1 until 2n:
        if doubled[next] > numbers[index]:
            result[index] = doubled[next]
            stop
 */

fun nextGreaterElementsDoubleArray(numbers: IntArray): IntArray {
    val result = IntArray(numbers.size)
    val doubled = numbers + numbers

    for (index in numbers.indices) {
        var next = -1
        for (nextIndex in index + 1 until doubled.size) {
            if (doubled[nextIndex] > numbers[index]) {
                next = doubled[nextIndex]
                break
            }
        }
        result[index] = next
    }

    return result
}
