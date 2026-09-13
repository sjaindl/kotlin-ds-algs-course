package big_o.complexity

// Shared step counter to compare how amount of work grows with the input size
var steps = 0L

fun reset() {
    steps = 0L
}

// Small helper to reset counter and print example result/steps
fun <T> demo(name: String, block: () -> T) {
    reset()
    val result = block()
    println("$name -> result: $result, steps: $steps")
}

// ---------------------------------------------------------------
// "Sum all integers from 1 to n."
// ---------------------------------------------------------------
fun sumUpTo(n: Int): Long {
    steps++
    return n.toLong() * (n + 1) / 2
}

// Same result, different cost:
fun sumUpToByLooping(n: Int): Long {
    var sum = 0L

    for (i in 1..n) {
        sum += i
        steps++
    }

    return sum
}

/*
    Big(O) runtime analysis
    sumUpTo: O(1) - constant
    sumUpToByLooping: O(N) - linear
 */

// ---------------------------------------------------------------
// "Find the index of a target in a sorted array."
// This is using the binary search algorithm.
// ---------------------------------------------------------------
fun search(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.size - 1

    while (left <= right) {
        steps++
        val mid = left + (right - left) / 2
        when {
            nums[mid] == target -> return mid
            nums[mid] < target -> left = mid + 1
            else -> right = mid - 1
        }
    }

    return -1
}

/*
    Big(O) runtime analysis
    search: O(log N) - logarithmic
 */

// ---------------------------------------------------------------
// "Find the largest sum of any contiguous subarray."
// ---------------------------------------------------------------
fun maxSubarraySum(nums: IntArray): Int {
    var best = nums[0]
    var current = nums[0]
    steps++

    for (i in 1 until nums.size) {
        steps++
        current = maxOf(nums[i], current + nums[i])
        best = maxOf(best, current)
    }

    return best
}

/*
    Big(O) runtime analysis
    maxSubarraySum: O(N) - linear
 */

// ---------------------------------------------------------------
// "Given an array, find the smallest absolute difference
//  between any two elements."
// ---------------------------------------------------------------
fun minimumDifference(nums: IntArray): Int {
    val sorted = nums.sortedArray()
    steps += (nums.size * log2(nums.size)).toLong() // the sort

    var best = Int.MAX_VALUE
    for (i in 1 until sorted.size) {
        steps++
        best = minOf(best, sorted[i] - sorted[i - 1])
    }
    return best
}

private fun log2(n: Int): Int {
    var value = n
    var result = 0

    while (value > 1) {
        value /= 2
        result++
    }

    return result
}

/*
    Big(O) runtime analysis
    minimumDifference: O(N log N) - linear logarithmic
 */

// ---------------------------------------------------------------
// "Sort an array by repeatedly swapping adjacent elements
//  that are out of order."
// ---------------------------------------------------------------
fun bubbleSort(nums: IntArray) {
    for (i in nums.indices) {
        for (j in 0 until nums.size - 1 - i) {
            steps++
            if (nums[j] > nums[j + 1]) {
                val temp = nums[j]
                nums[j] = nums[j + 1]
                nums[j + 1] = temp
            }
        }
    }
}

/*
    Big(O) runtime analysis
    bubbleSort: O(N * N/2) = O(N^2) - quadratic
 */

// ---------------------------------------------------------------
// "How many groups of three numbers add up to the target?"
// ---------------------------------------------------------------
fun countTriplets(nums: IntArray, target: Int): Int {
    var count = 0

    for (i in nums.indices) {
        for (j in i + 1 until nums.size) {
            for (k in j + 1 until nums.size) {
                steps++
                if (nums[i] + nums[j] + nums[k] == target) {
                    count++
                }
            }
        }
    }

    return count
}

/*
    Big(O) runtime analysis
    countTriplets: N choose 3 = O(N * (N-1) * (N-2) / 3!) = O(N^3) - cubic
        unordered set of 3 distinct positions:
        - N choices for the first index
        - (N−1) for the second
        - (N−2) for the third
 */

// ---------------------------------------------------------------
// "Can any subset of these numbers add up exactly to target?"
// ---------------------------------------------------------------
fun canReachTarget(nums: IntArray, target: Int, index: Int = 0): Boolean {
    steps++
    if (target == 0) return true
    if (index == nums.size || target < 0) return false

    // Two choices per element: take it, or skip it.
    return canReachTarget(nums = nums, target = target - nums[index], index = index + 1) ||
            canReachTarget(nums = nums, target = target, index = index + 1)
}

/*
    Big(O) runtime analysis
    canReachTarget: O(2^N) - exponential
    2 choices at each position -> O(branches^depth)
 */

// ---------------------------------------------------------------
// "Visit every city exactly once and return home.
//  Find the shortest possible route."
// ---------------------------------------------------------------
fun shortestRoute(
    distances: Array<IntArray>,
    unvisited: MutableList<Int>,
    current: Int = 0,
    travelled: Int = 0
): Int {
    if (unvisited.isEmpty()) {
        steps++
        return travelled + distances[current][0]   // back home
    }

    var best = Int.MAX_VALUE

    for (i in unvisited.indices) {
        val next = unvisited.removeAt(index = i)
        best = minOf(
            best,
            shortestRoute(
                distances = distances,
                unvisited = unvisited,
                current = next,
                travelled = travelled + distances[current][next],
            )
        )
        unvisited.add(index = i, element = next)
    }

    return best
}

/*
    Big(O) runtime analysis
    shortestRoute: O(N!) - factorial
    brute-force: traveling salesman (TSP)
    The decision-TSP ("a tour shorter than K?") is NP-complete;
    start city is fixed, so it tries (N-1)! routes -> still O(N!)
 */
