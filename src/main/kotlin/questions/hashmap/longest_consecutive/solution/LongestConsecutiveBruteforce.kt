package questions.hashmap.longest_consecutive.solution

/*
From every number, count how far the sequence reaches downwards (num - 1, num - 2, …) and upwards
(num + 1, num + 2, …). Every step asks the array whether it contains the next value, which scans the whole array.

[100, 4, 200, 1, 3, 2], start at 4:
down: 3 found, 2 found, 1 found, 0 missing -> 4 numbers
up:   5 missing                            -> 4 numbers, longest = 4

n: length of nums
T: O(n³) - n starts, a sequence of up to n numbers, and every contains scans n numbers
S: O(1)

longest = 0
for each num in nums:
    current = 1
    prev = num - 1
    while nums contains prev:
        current++, prev--
    next = num + 1
    while nums contains next:
        current++, next++
    longest = max(longest, current)
return longest
 */

fun longestConsecutiveBruteforce(nums: IntArray): Int {
    var longest = 0

    nums.forEach { num ->
        var current = 1
        var prev = num - 1

        while (nums.contains(prev)) { // O(n) scan
            current++
            prev--
        }

        var next = num + 1
        while (nums.contains(next)) { // O(n) scan
            current++
            next++
        }

        longest = maxOf(longest, current)
    }

    return longest
}
