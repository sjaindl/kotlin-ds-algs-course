package toolbox.hashmap

/*
Swap remove
removeAt(i) shifts every element behind i one step to the left: O(n). If the order of the list doesn't matter,
move the last element into the gap instead and drop the last slot: O(1). Only one element changed its index, the one
that moved, so an index map (IndexMap.kt) needs exactly one fix.

When to reach for it: a list that needs O(1) removal and O(1) random access, e.g. a bag of items to pick randomly
from. Together with an index map, a value is found and removed in O(1).

Seen in:
    questions/hashmap/randomized_collection/solution/RandomizedCollectionHashMap.kt     swap with last, fix the index
    questions/hashmap/randomized_collection/solution/RandomizedCollectionBruteforce.kt  removeAt, O(n)

Kotlin: removeAt(lastIndex) needs a non-empty list. Prefer it over removeLast(), which compiles to Java 21's
List.removeLast() and crashes on older JVMs and Android below API 35. When i is the last index, the element is moved onto itself; check that
case before fixing the index map, or the fix points to a slot that no longer exists.

[a, b, c, d].swapRemove(1) -> [a, d, c], returns b; d moved from index 3 to 1

n: size of the list
T: O(1)
S: O(1)
 */

// removes the element at index by moving the last one into its place, returns the removed element
fun <T> MutableList<T>.swapRemove(index: Int): T {
    val removed = this[index]
    this[index] = this[lastIndex]
    removeAt(lastIndex)
    return removed
}
