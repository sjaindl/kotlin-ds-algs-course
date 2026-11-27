package toolbox.hashmap

/*
Canonical order
Two things that only differ in their order should give the same key. Sort first: then (b, a) becomes (a, b), and
[3, 1, 2] becomes [1, 2, 3], so the map or set sees them as one key.
- two values: no full sort needed, (min, max) is the sorted order
- a list: a sorted copy, as List (compares by content)
- a string: its sorted letters, like the anagram key in toolbox/string/CanonicalKey.kt

When to reach for it: "a with b is the same as b with a" (undirected edges, unordered pairs counted once),
combinations and multisets that must be unique, deduplicating results that may come in any order.

Seen in:
    toolbox/string/CanonicalKey.kt                                              sortedKey for anagrams
    questions/string/group_anagrams/solution/GroupAnagramsSorting.kt            sorted letters as key
    questions/hashmap/longest_consecutive/solution/LongestConsecutiveSorting.kt sort first, then scan

Kotlin: sorted() returns a new List and leaves the input alone; sort() sorts a MutableList in place. Never sort a
list that is already a key in a map: that changes the key (see CustomKeys.kt).

unorderedPair(5, 2) = unorderedPair(2, 5) = (2, 5)
[[1, 2, 3], [3, 1, 2], [2, 2]] -> keys [1, 2, 3], [1, 2, 3], [2, 2] -> 2 distinct

n: number of lists, k: length of the longest list
T: O(n · k log k) - every list sorted once
S: O(n · k) - the keys
 */

// the same Pair for (a, b) and (b, a)
fun unorderedPair(a: Int, b: Int): Pair<Int, Int> = if (a <= b) a to b else b to a

// a sorted copy: equal for all orders of the same items
fun canonical(items: List<Int>): List<Int> = items.sorted()

// how many different lists there are, if the order inside a list doesn't count
fun distinctUpToOrder(lists: List<List<Int>>): Int {
    val seen = HashSet<List<Int>>()
    for (list in lists) {
        seen.add(canonical(list))
    }
    return seen.size
}
