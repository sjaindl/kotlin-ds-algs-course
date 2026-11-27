# Common bugs in hash map and set questions

Each entry in this list first shows a common bug, with a wrong snippet and a fixed one.

## 1. Changing a key while it's in a map

The map puts an entry into the bucket of the key's hash code at insertion time. Change the key afterwards, and its
hash code changes too: the lookup searches the wrong bucket, and even an equal fresh key no longer matches. The entry
is still there (size 1), but unreachable. Use read-only keys, or copy a mutable one before inserting it.

**Wrong**
```kotlin
val visited = HashMap<MutableList<Int>, String>()
val path = mutableListOf(1, 2)
visited[path] = "seen"
path.add(3)
println(visited[path]) // null, and visited.size is still 1
```

**Correct**
```kotlin
val visited = HashMap<List<Int>, String>()
val path = mutableListOf(1, 2)
visited[path.toList()] = "seen" // a copy that nobody changes
path.add(3)
println(visited[listOf(1, 2)]) // seen
```

## 2. A data class with `var` properties as key

A data class computes `hashCode` from its properties. Changing a `var` property after adding the object to a set
breaks the lookup in the same way: the set can't find the object, not even itself. Use `val` properties and create a
new object with `copy`.

Seen in: `toolbox/hashmap/CustomKeys.kt`

**Wrong**
```kotlin
data class Point(var x: Int, var y: Int)

val visited = HashSet<Point>()
val point = Point(0, 0)
visited.add(point)
point.x = 1
println(point in visited) // false
```

**Correct**
```kotlin
data class Point(val x: Int, val y: Int)

val visited = HashSet<Point>()
var point = Point(0, 0)
visited.add(point)
point = point.copy(x = 1)
println(Point(0, 0) in visited) // true
```

## 3. `equals` without `hashCode`

Equal objects must have equal hash codes. With only `equals` overridden, two equal objects get different identity
hash codes, land in different buckets, and are never even compared: the set holds both. A data class generates both
from the same properties. If you write `equals` by hand, write `hashCode` with it.

Seen in: `toolbox/hashmap/CustomKeys.kt`

**Wrong**
```kotlin
class Person(val name: String) {
    override fun equals(other: Any?): Boolean = other is Person && other.name == name
}

val people = hashSetOf(Person("Ann"), Person("Ann"))
println(people.size) // 2, although Person("Ann") == Person("Ann") is true
```

**Correct**
```kotlin
data class Person(val name: String)

val people = hashSetOf(Person("Ann"), Person("Ann"))
println(people.size) // 1
```

## 4. An array as key

Arrays don't override `equals` and `hashCode`, so two `IntArray`s with the same content are different keys, and a
lookup with a new array always returns `null`. A `List` compares by content. For two Ints, a `data class` or a packed
`Long` works too.

Seen in: `toolbox/hashmap/CustomKeys.kt`

**Wrong**
```kotlin
val distance = hashMapOf(intArrayOf(0, 0) to 0)
println(distance[intArrayOf(0, 0)]) // null
```

**Correct**
```kotlin
val distance = hashMapOf(listOf(0, 0) to 0)
println(distance[listOf(0, 0)]) // 0
```

## 5. Relying on the iteration order of a `HashMap` or `HashSet`

A `HashSet` iterates in bucket order, not in insertion order. That order isn't guaranteed: it changes when the table
resizes, and it can differ between JVM versions. If "first" or the output order matters, use a `LinkedHashSet`
(`linkedSetOf`, `mutableSetOf`) for insertion order, or a `TreeSet` (`sortedSetOf`) for sorted order. The same holds
for `HashMap` vs `LinkedHashMap` / `TreeMap`.

Seen in: `toolbox/hashmap/MapAndSetTypes.kt`

**Wrong**
```kotlin
val fruits = hashSetOf("banana", "apple", "cherry", "date")
println(fruits) // [banana, date, apple, cherry]

val numbers = hashSetOf(17, 1, 33) // [17, 1, 33]
// add and remove 13 more numbers: the table resizes
println(numbers) // [1, 33, 17], same elements, new order
```

**Correct**
```kotlin
val fruits = linkedSetOf("banana", "apple", "cherry", "date")
println(fruits) // [banana, apple, cherry, date], insertion order

val sorted = sortedSetOf("banana", "apple", "cherry", "date")
println(sorted) // [apple, banana, cherry, date]
```

## 6. Removing from a map while iterating it

Changing a map's structure inside a `for` loop over it makes the iterator fail with
`ConcurrentModificationException`, often only for some inputs. Remove through the iterator: `removeIf` on the keys,
values or entries does that for you.

**Wrong**
```kotlin
// {a=3, b=0, c=0, d=2} -> ConcurrentModificationException
for (key in counts.keys) {
    if (counts.getValue(key) == 0) {
        counts.remove(key)
    }
}
```

**Correct**
```kotlin
// {a=3, b=0, c=0, d=2} -> {a=3, d=2}
counts.values.removeIf { it == 0 }
```

## 7. Adding to a set while iterating it

The same rule holds for adding: inserting into the set you're looping over throws
`ConcurrentModificationException`. Iterate over a copy, or collect the new elements in a second set and add them after
the loop.

**Wrong**
```kotlin
// {1, 5} -> ConcurrentModificationException
for (x in reachable) {
    reachable.add(x + 1)
}
```

**Correct**
```kotlin
// {1, 5} -> [1, 2, 5, 6]
for (x in reachable.toList()) {
    reachable.add(x + 1)
}
```

## 8. `!!` on a missing key

`map[key]` returns `null` for a key that isn't there yet. Counting with `counts[char]!! + 1` throws a
`NullPointerException` on the very first character. `getOrDefault` gives a start value instead.

**Wrong**
```kotlin
// "aba" -> NullPointerException
for (char in s) {
    counts[char] = counts[char]!! + 1
}
```

**Correct**
```kotlin
// "aba" -> {a=2, b=1}
for (char in s) {
    counts[char] = counts.getOrDefault(char, 0) + 1
}
```

## 9. `in` on a List inside a loop

`x in list` (or `list.contains(x)`) scans the whole list, O(n). Inside a loop over n elements, that's O(n²). Build a
`HashSet` once, and every lookup is O(1) on average.

Seen in: `questions/hashmap/longest_consecutive/solution/LongestConsecutiveBruteforce.kt`,
`questions/hashmap/longest_consecutive/solution/LongestConsecutiveTwoSets.kt`

**Wrong**
```kotlin
for (num in nums) {
    if (num - 1 !in nums) { // n = 10^5: ~9 s
        starts++
    }
}
```

**Correct**
```kotlin
val numSet = nums.toHashSet()
for (num in nums) {
    if (num - 1 !in numSet) { // n = 10^5: ~10 ms
        starts++
    }
}
```

## 10. Counting a run from every number

Even with a set, counting up from every number walks the same run again and again: for 1..n that's
n + (n - 1) + ... + 1, O(n²). Only start counting where the run starts, when `num - 1` isn't in the set. Then every
number is visited at most twice.

Seen in: `questions/hashmap/longest_consecutive/solution/LongestConsecutiveTwoSets.kt`

**Wrong**
```kotlin
for (num in numSet) {
    var length = 1
    while (num + length in numSet) { // 1..10^5: ~13 s
        length++
    }
    longest = maxOf(longest, length)
}
```

**Correct**
```kotlin
for (num in numSet) {
    if (num - 1 in numSet) continue // not the start of a run
    var length = 1
    while (num + length in numSet) { // 1..10^5: ~20 ms
        length++
    }
    longest = maxOf(longest, length)
}
```

## 11. Pairing an element with itself

When you look up a partner in a map built from the same input, the lookup can return the element's own index. A
palindrome like "aba" is its own reverse, so it pairs with itself. Check that the match is a different index.

Seen in: `questions/hashmap/palindrome_pairs/solution/PalindromePairsHashMap.kt`

**Wrong**
```kotlin
// [aba, ab, ba] -> [[0, 0], [1, 2], [2, 1]]
words.forEachIndexed { index, word ->
    val matched = wordIndex[word.reversed()]
    if (matched != null) {
        pairs.add(listOf(index, matched))
    }
}
```

**Correct**
```kotlin
// [aba, ab, ba] -> [[1, 2], [2, 1]]
words.forEachIndexed { index, word ->
    val matched = wordIndex[word.reversed()]
    if (matched != null && matched != index) {
        pairs.add(listOf(index, matched))
    }
}
```

## 12. Swap-remove of the last index

Removing in O(1) moves the last element into the gap and fixes its index in the index map. If the removed element *is*
the last one, it moves onto itself, and the fix adds its removed index back. The map now points behind the end of the
list: the next `remove` of that value throws `IndexOutOfBoundsException`.

Seen in: `questions/hashmap/randomized_collection/solution/RandomizedCollectionHashMap.kt`,
`toolbox/hashmap/SwapRemove.kt`

**Wrong**
```kotlin
// insert 1, insert 2, remove 2 -> numbers [1], indices {1=[0], 2=[1]}
valueIndices.remove(indexToRemove)
val moved = numbers[lastIndex]
numbers[indexToRemove] = moved
indices.getValue(moved).remove(lastIndex)
indices.getValue(moved).add(indexToRemove)
numbers.removeAt(lastIndex)
```

**Correct**
```kotlin
// insert 1, insert 2, remove 2 -> numbers [1], indices {1=[0]}
valueIndices.remove(indexToRemove)
if (indexToRemove != lastIndex) {
    val moved = numbers[lastIndex]
    numbers[indexToRemove] = moved
    indices.getValue(moved).remove(lastIndex)
    indices.getValue(moved).add(indexToRemove)
}
numbers.removeAt(lastIndex)
```

## 13. Integer overflow when counting pairs

Multiplying group sizes instead of listing pairs is fast, but the counts grow fast too. Two groups of 50,000 already
give 2.5 billion pairs, more than `Int.MAX_VALUE`, and an `Int` silently wraps around. Count in a `Long`, and convert
before multiplying.

Seen in: `questions/hashmap/naming_a_company/solution/NamingACompanySuffixGroups.kt`,
`toolbox/hashmap/GroupAndMultiply.kt`

**Wrong**
```kotlin
var count = 0
count += sizeA * sizeB // 50_000 * 50_000 -> -1794967296
```

**Correct**
```kotlin
var count = 0L
count += sizeA.toLong() * sizeB // 50_000 * 50_000 -> 2500000000
```

## 14. A map where a set belongs

A `HashMap<Int, Boolean>` to mark seen values has three states: missing, `true` and `false`. Is a value with `false`
"seen"? `containsKey` says yes, `== true` says no, and sooner or later the two get mixed up. A `HashSet` has only "in"
or "not in", and `add` returns `false` if the value was already there, so checking and inserting is one call.

Seen in: `toolbox/hashmap/SetLookups.kt`

**Wrong**
```kotlin
val seen = HashMap<Int, Boolean>()
seen[7] = false
println(seen.containsKey(7)) // true
println(seen[7] == true) // false
```

**Correct**
```kotlin
// [1, 7, 3, 7] -> duplicate
val seen = HashSet<Int>()
for (x in nums) {
    if (!seen.add(x)) {
        return true
    }
}
```
