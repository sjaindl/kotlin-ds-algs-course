package questions.array.rotate_array.solution

/*
Move each element directly to its final index (index + k) mod n, carrying the replaced value along to its own
final index, until the cycle returns to where it started. If n and k share a divisor, one cycle doesn't visit
every index, so start the next cycle one index further until all n elements have been moved.

[-1,-100,3,99], k = 2
cycle from index 0: -1 -> index 2, 3 -> index 0  => [3,-100,-1,99]
cycle from index 1: -100 -> index 3, 99 -> index 1 => [3,99,-1,-100]

n: number of elements
T: O(n)
S: O(1)

moved = 0
start = 0
while moved < n:
    from = start, value = nums[start]
    do:
        to = (from + k) mod n
        swap value and nums[to]
        from = to
        moved++
    while from != start
    start++
 */

fun rotateCyclicReplacements(nums: IntArray, k: Int) {
    if (nums.size < 2) return

    val shift = k % nums.size
    var moved = 0
    var startIndex = 0

    while (moved < nums.size) {
        var fromIndex = startIndex
        var fromValue = nums[startIndex]

        do {
            val toIndex = (fromIndex + shift) % nums.size
            val temp = nums[toIndex]
            nums[toIndex] = fromValue

            fromValue = temp
            fromIndex = toIndex
            moved++
        } while (startIndex != fromIndex)

        startIndex++
    }
}
