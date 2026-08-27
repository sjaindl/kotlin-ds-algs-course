package big_o.space

// Shared step counter to compare how amount of work grows with the input size
var steps = 0L

fun reset() {
    steps = 0L
}

// ---------------------------------------------------------------
// Snippet 1: Auxiliary vs. total space
// "Reverse the array."   — two ways
// ---------------------------------------------------------------
fun reverseInPlace(nums: IntArray) {
    var left = 0
    var right = nums.size - 1

    while (left < right) {
        val temp = nums[left] // one variable, regardless of n
        nums[left] = nums[right]
        nums[right] = temp
        left++
        right--
    }
}

fun reversedCopy(nums: IntArray): IntArray {
    val result = IntArray(nums.size) // a second array, size n
    for (i in nums.indices) {
        result[i] = nums[nums.size - 1 - i]
    }
    return result
}

/*
    Big(O) SPACE analysis (Snippet 1: Auxiliary vs. total space)
    reverseInPlace: O(1) space - a fixed set of variables, regardless of n
    reversedCopy:   O(N) space - allocates a second array of size n
    same runtime O(N), different space -> the two axes are independent
 */

// ---------------------------------------------------------------
// Snippet 2: recursive call stack
// "Sum the array."   — nothing allocated. Or is there?
// ---------------------------------------------------------------
fun sumRecursive(nums: IntArray, index: Int = 0): Long {
    if (index == nums.size) return 0
    return nums[index] + sumRecursive(nums = nums, index = index + 1)
}

// `tailrec` optimization -> Compiler rewrites the recursion into a loop.
tailrec fun sumTailRecursive(nums: IntArray, index: Int = 0, acc: Long = 0): Long {
    if (index == nums.size) return acc
    return sumTailRecursive(nums, index + 1, acc + nums[index])
}

/*
    Big(O) SPACE analysis (Snippet 2: recursive call stack)
    sumRecursive:     O(N) space - n pending call frames on the stack (hidden!),
                      no array/list is allocated, yet it overflows the stack
    sumTailRecursive: O(1) space - `tailrec` becomes a loop, the stack stays flat
 */

// ---------------------------------------------------------------
// Snippet 3: time-space trade-off
// "Find two numbers that add up to the target. Return their indices."
// ---------------------------------------------------------------
fun twoSumBruteForce(nums: IntArray, target: Int): IntArray? {
    for (i in nums.indices) {
        for (j in i + 1 until nums.size) {
            steps++
            if (nums[i] + nums[j] == target) return intArrayOf(i, j)
        }
    }
    return null
}

fun twoSumWithMap(nums: IntArray, target: Int): IntArray? {
    val seen = HashMap<Int, Int>() // value -> index
    for (i in nums.indices) {
        steps++
        val complement = target - nums[i]
        seen[complement]?.let {
            return intArrayOf(it, i)
        }
        seen[nums[i]] = i
    }
    return null
}

/*
    Big(O) time & SPACE analysis (Snippet 3: time-space trade-off)
    twoSumBruteForce: O(N^2) time, O(1) space - nested loops, nothing stored
    twoSumWithMap:    O(N)   time, O(N) space - one pass, but stores up to n entries
    the classic trade-off: spend memory to skip the entire inner loop
 */
