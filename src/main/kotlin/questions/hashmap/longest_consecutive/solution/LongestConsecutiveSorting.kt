package questions.hashmap.longest_consecutive.solution

/*
After sorting, a consecutive sequence is a block of neighbours that grow by exactly 1. Walk the sorted array once:
the next value either extends the streak (cur + 1), repeats the current value (a duplicate, skip it) or leaves a
gap, which ends the streak.

[100, 4, 200, 1, 3, 2] sorted: [1, 2, 3, 4, 100, 200]
1 -> 2 -> 3 -> 4: streak 4
100: gap, longest = 4, streak 1
200: gap, streak 1 -> longest = 4

n: length of nums
T: O(n log n) - sorting
S: O(n) - the sorted copy

if nums is empty: return 0
sorted = nums sorted
cur = sorted[0], streak = 1, longest = 0
for each value after the first in sorted:
    if value == cur + 1: streak++
    else if value > cur + 1: longest = max(longest, streak), streak = 1
    cur = value
return max(longest, streak)
 */

fun longestConsecutiveSorting(nums: IntArray): Int {
    if (nums.isEmpty()) return 0

    val sorted = nums.sorted()
    var cur = sorted[0]
    var streak = 1
    var longest = 0

    for (index in 1 until sorted.size) {
        if (sorted[index] == cur + 1) {
            streak++
        } else if (sorted[index] > cur + 1) {
            longest = maxOf(longest, streak)
            streak = 1
        } // equal values are duplicates: the streak neither grows nor ends
        cur = sorted[index]
    }

    return maxOf(longest, streak)
}
