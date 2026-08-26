package big_o.rules

// Shared step counters to compare how amount of work grows with the input size
var steps = 0L
var sortSteps = 0L
var walkSteps = 0L

fun reset() {
    steps = 0L
}

// Small helper to time a block of work in milliseconds
inline fun measure(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}

// ---------------------------------------------------------------
// Rule 1: worst case
// "Does this array contain the target?"
// ---------------------------------------------------------------
fun contains(nums: IntArray, target: Int): Boolean {
    for (value in nums) {
        steps++
        if (value == target) return true
    }
    return false
}

/*
    Big(O) runtime analysis
    contains: O(N) - linear
    best case is O(1) (target at the front), but Big(O) describes the worst case
 */

// ---------------------------------------------------------------
// Rule 2: drop constants
// "Find the smallest and the largest value in one array."
// ---------------------------------------------------------------
fun minAndMax(nums: IntArray): Pair<Int, Int> {
    var min = nums[0]
    for (value in nums) {
        steps++
        if (value < min) {
            min = value
        }
    }

    var max = nums[0]
    for (value in nums) {
        steps++
        if (value > max) {
            max = value
        }
    }

    return min to max
}

/*
    Big(O) runtime analysis
    minAndMax: O(2N) = O(N) - linear
 */

// ---------------------------------------------------------------
// Rule 2: drop constants
// "Reverse the array in place."
// ---------------------------------------------------------------
fun reverse(nums: IntArray) {
    var left = 0
    var right = nums.size - 1

    while (left < right) {
        steps++
        val temp = nums[left]
        nums[left] = nums[right]
        nums[right] = temp
        left++
        right--
    }
}

/*
    Big(O) runtime analysis
    minAndMax: O(N/2) = O(N) - linear
 */

// ---------------------------------------------------------------
// Rule 2: drop constants
// "Return the 100 highest-priced items from the catalogue.
//  The catalogue is already sorted, most expensive first."
// ---------------------------------------------------------------
fun topHundred(pricesDesc: IntArray): List<Int> {
    val result = mutableListOf<Int>()

    for (i in 0 until minOf(100, pricesDesc.size)) {
        steps++
        result.add(pricesDesc[i])
    }

    return result
}

/*
    Big(O) runtime analysis (Rule 2: drop constants)
    minAndMax:  O(2N)  = O(N) - linear
    reverse:    O(N/2) = O(N) - linear
    topHundred: O(100) = O(1) - constant (does not grow with input)
 */

// ---------------------------------------------------------------
// Rule 3: drop non-dominant terms
// "Given an array, find the smallest absolute difference
//  between any two elements."
// ---------------------------------------------------------------
fun minimumDifference(nums: IntArray): Int {
    val sorted = nums.sortedArray() // expensive part -> O(N log N)
    sortSteps += (nums.size * log2(nums.size)).toLong()

    var best = Int.MAX_VALUE
    for (i in 1 until sorted.size) { // cheap part -> O(N)
        walkSteps++
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
    Big(O) runtime analysis (Rule 3: drop non-dominant terms)
    minimumDifference: O(N log N + N) = O(N log N) - linear logarithmic
    the sort dominates the single linear walk, so O(N) term is dropped
 */

// ---------------------------------------------------------------
// Rule 4: multiple inputs
// "Which usernames appear in both lists?"
// ---------------------------------------------------------------
fun common(a: List<String>, b: List<String>): List<String> {
    val seen = HashSet<String>()

    for (name in a) {
        steps++
        seen.add(name)
    }

    val result = mutableListOf<String>()

    for (name in b) {
        steps++
        if (name in seen) {
            result.add(name)
        }
    }

    return result
}

/*
    Big(O) runtime analysis (Rule 4: multiple inputs)
    common: O(A + B) - one loop over each input, separately
    do NOT collapse two different inputs into a single "N"
 */

// ---------------------------------------------------------------
// Rule 4: multiple inputs
// "Which usernames appear in both lists?"
// Same question, but without extra memory.
// ---------------------------------------------------------------
fun commonNoExtraSpace(a: List<String>, b: List<String>): List<String> {
    val result = mutableListOf<String>()

    for (nameA in a) {
        for (nameB in b) {
            steps++
            if (nameA == nameB) {
                result.add(nameA)
                break
            }
        }
    }

    return result
}

/*
    Big(O) runtime analysis (Rule 4: multiple inputs)
    common:             O(A + B) - one loop over each input, separately
    commonNoExtraSpace: O(A * B) - nested loops, one input inside the other
    do NOT collapse two different inputs into a single "N"
 */

// ---------------------------------------------------------------
// Rule 5: Calling other functions
// "Remove all duplicates from a list, keeping the original order."
// Without extra memory
// ---------------------------------------------------------------
fun deduplicate(items: List<Int>): List<Int> {
    val result = mutableListOf<Int>()

    for (item in items) {
        steps++
        if (!result.contains(item)) {
            result.add(item)
        }
    }

    return result
}

// ---------------------------------------------------------------
// Rule 5: Calling other functions
// "Remove all duplicates from a list, keeping the original order."
// With extra memory
// ---------------------------------------------------------------
fun deduplicateFast(items: List<Int>): List<Int> {
    val seen = HashSet<Int>()
    val result = mutableListOf<Int>()

    for (item in items) {
        steps++
        if (seen.add(item)) {
            result.add(item)
        }
    }

    return result
}

/*
    Big(O) runtime analysis (Rule 5: account for called functions)
    deduplicate:     O(N) * O(N) = O(N^2) - list.contains re-scans on every item
    deduplicateFast: O(N) * O(1) = O(N)   - HashSet.add is amortized O(1)
    the loop looks identical; the hidden cost of the called function decides it
 */
