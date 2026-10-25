package questions.array.next_greater_element.solution

/*
For every element, scan the other n - 1 elements circularly (index + 1, index + 2, ... wrapping with mod n)
and take the first greater one. The maximum can never find a greater number, so it is -1 without scanning.

n: number of elements
T: O(n^2)
S: O(1) - besides the result

max = maximum of numbers
for each index:
    if numbers[index] == max:
        result[index] = -1
    else:
        for next in index + 1 until index + n:
            if numbers[next mod n] > numbers[index]:
                result[index] = numbers[next mod n]
                stop
 */

fun nextGreaterElementsBruteforce(numbers: IntArray): IntArray {
    val max = numbers.max()
    val result = IntArray(numbers.size)

    for (index in numbers.indices) {
        result[index] = if (numbers[index] == max) {
            -1
        } else {
            nextGreaterElement(numbers = numbers, index = index)
        }
    }

    return result
}

private fun nextGreaterElement(numbers: IntArray, index: Int): Int {
    for (nextIndex in index + 1 until index + numbers.size) {
        val circularIndex = nextIndex % numbers.size
        if (numbers[circularIndex] > numbers[index]) {
            return numbers[circularIndex]
        }
    }

    return -1
}
