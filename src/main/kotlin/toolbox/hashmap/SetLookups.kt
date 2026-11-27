package toolbox.hashmap

/*
Set lookups
Put the input into a hash set once, then "is x in the input?" costs O(1) instead of a scan. That replaces an inner
loop, and often a sort: to find x - 1 or x + 1 you don't need the numbers in order, you only need to ask the set.

When to reach for it: duplicates, "does the partner exist?", neighbours of a value (x - 1, x + 1), visited sets.

Seen in:
    questions/hashmap/longest_consecutive/solution/LongestConsecutiveTwoSets.kt      "is num - 1 / num + 1 in the array?"
    questions/hashmap/longest_consecutive/solution/LongestConsecutiveSorting.kt      the sort it replaces
    questions/hashmap/naming_a_company/solution/NamingACompanyBruteforce.kt          "is the swapped name an idea?"

Kotlin: add returns false if the element was already in the set, so checking and inserting is one call.
x in set calls contains; on a List it would scan.

[1, 2, 3, 1] -> add 1, 2, 3 returns true, add 1 returns false -> duplicate

n: number of items
T: O(n) average to build, O(1) average per lookup
S: O(n)
 */

// true if some item occurs twice: add returns false for the second one
fun <T> hasDuplicate(items: List<T>): Boolean {
    val seen = HashSet<T>()
    for (item in items) {
        if (!seen.add(item)) return true
    }
    return false
}
