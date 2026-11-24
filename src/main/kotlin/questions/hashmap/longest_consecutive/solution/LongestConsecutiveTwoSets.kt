package questions.hashmap.longest_consecutive.solution

/*
The bruteforce idea, made fast with two hash sets. numSet answers "is num - 1 in the array?" in O(1) instead of
scanning the array. checked remembers every number a sequence has already reached: the first number of a sequence
that we visit expands it completely, so the others never have to walk it again.

[100, 4, 200, 1, 3, 2], numSet = {100, 4, 200, 1, 3, 2}
100: 99 and 101 missing           -> 1
4:   down 3, 2, 1 (checked), up 5 missing -> 4, longest = 4
200: 199 and 201 missing          -> 1
1:   down 0 missing, up 2 is checked: stop right away -> 1
3, 2: neighbours already checked  -> short counts, longest stays 4

n: length of nums
T: O(n) average - every number is added to checked at most once, so all while loops together take O(n) steps
S: O(n) - both sets

numSet = all numbers of nums
checked = empty set
longest = 0
for each num in nums:
    current = 1
    prev = num - 1
    while prev in numSet and prev not in checked:
        current++, add prev to checked, prev--
    next = num + 1
    while next in numSet and next not in checked:
        current++, add next to checked, next++
    longest = max(longest, current)
return longest
 */

fun longestConsecutiveTwoSets(nums: IntArray): Int {
    val numSet = nums.toHashSet()
    val checked = hashSetOf<Int>()
    var longest = 0

    nums.forEach { num ->
        var current = 1
        var prev = num - 1
        while (prev in numSet && prev !in checked) {
            current++
            checked.add(prev)
            prev--
        }

        var next = num + 1
        while (next in numSet && next !in checked) {
            current++
            checked.add(next)
            next++
        }

        longest = maxOf(longest, current)
    }

    return longest
}
