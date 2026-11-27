package toolbox.hashmap

/*
Index map
A map from every value to its index answers "is it there, and where?" in O(1), where indexOf scans the whole list.
Build it once in O(n), then every lookup is a hash lookup. With duplicates, map every value to all its indices.

When to reach for it: "find the partner of x" (two sum, palindrome pairs), removing a value from a list in O(1)
(see SwapRemove.kt), positions of every character or word.

Seen in:
    questions/hashmap/palindrome_pairs/solution/PalindromePairsHashMap.kt               word -> index
    questions/hashmap/randomized_collection/solution/RandomizedCollectionHashMap.kt     value -> set of indices
    toolbox/string/Contribution.kt                                                     indicesByChar

Kotlin: getOrPut creates the list or set of a new key and returns it, so adding an index is one line.
associate / withIndex build a value -> index map too, but a later duplicate overwrites an earlier one.

["b", "a", "b"] -> indexOfEach = {b=2, a=1} (last index wins), indicesOfEach = {b=[0, 2], a=[1]}

n: number of items
T: O(n) to build, O(1) average per lookup
S: O(n)
 */

// value -> index; for a duplicate, the last index wins
fun <T> indexOfEach(items: List<T>): Map<T, Int> {
    val index = HashMap<T, Int>()
    items.forEachIndexed { i, item ->
        index[item] = i
    }
    return index
}

// value -> all its indices, in increasing order
fun <T> indicesOfEach(items: List<T>): Map<T, List<Int>> {
    val indices = HashMap<T, MutableList<Int>>()
    items.forEachIndexed { i, item ->
        indices.getOrPut(item) {
            mutableListOf()
        }.add(i)
    }
    return indices
}
