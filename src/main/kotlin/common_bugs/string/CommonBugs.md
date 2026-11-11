# Common bugs in string questions

Each entry in this list first shows a common bug, with a wrong snippet and a fixed one.

## 1. `substring` end is exclusive

`s.substring(start, end)` stops before `end`, like `until`. Passing the index of the last character you want drops
that character, without any exception.

Seen in: `questions/string/unique_letter_string/solution/UniqueLetterStringBruteforce.kt`

**Wrong**
```kotlin
val window = s.substring(start, end) // "abcde", start = 1, end = 3 -> "bc"
```

**Correct**
```kotlin
val window = s.substring(start, end + 1) // "abcde", start = 1, end = 3 -> "bcd"
```

## 2. Forgetting the empty string

`s[0]`, `s.first()` or `s.length - 1` assume at least one character. On `""` they throw
`StringIndexOutOfBoundsException`. If the constraints allow an empty string, handle it first.

Seen in: `questions/string/frequency_sort/solution/FrequencySortBruteforce.kt`, `toolbox/string/StringBuilding.kt`

**Wrong**
```kotlin
fun capitalize(s: String): String {
    return s[0].uppercase() + s.substring(1) // "" -> StringIndexOutOfBoundsException
}
```

**Correct**
```kotlin
fun capitalize(s: String): String {
    if (s.isEmpty()) return s

    return s[0].uppercase() + s.substring(1)
}
```

## 3. Forgetting the last run

When you cut a string into runs of equal characters, a run is only added once the next character differs. The last
run is never followed by a different character, so it has to be added after the loop.

Seen in: `questions/string/frequency_sort/solution/FrequencySortBruteforce.kt`, `toolbox/string/StringBuilding.kt`

**Wrong**
```kotlin
// "eert" -> [ee, r]
val runs = mutableListOf<String>()
val run = StringBuilder()
run.append(s[0])
for (i in 1 until s.length) {
    if (s[i] != s[i - 1]) {
        runs.add(run.toString())
        run.clear()
    }
    run.append(s[i])
}
```

**Correct**
```kotlin
// "eert" -> [ee, r, t]
val runs = mutableListOf<String>()
val run = StringBuilder()
run.append(s[0])
for (i in 1 until s.length) {
    if (s[i] != s[i - 1]) {
        runs.add(run.toString())
        run.clear()
    }
    run.append(s[i])
}
runs.add(run.toString())
```

## 4. Wrong boundary when there is no neighbour

When there is no previous or next occurrence, the boundary has to lie just outside the string: `-1` on the left and
`s.length` on the right. With `0` and `s.length - 1`, the first and last index lose their own position as a start or
end, and the count comes out too small.

Seen in: `questions/string/unique_letter_string/solution/UniqueLetterStringCharSplit.kt`,
`toolbox/string/Contribution.kt`

**Wrong**
```kotlin
// "ABC" -> 1
list.forEachIndexed { position, index ->
    val prev = if (position == 0) 0 else list[position - 1]
    val next = if (position == list.size - 1) s.length - 1 else list[position + 1]
    count += (index - prev) * (next - index)
}
```

**Correct**
```kotlin
// "ABC" -> 10
list.forEachIndexed { position, index ->
    val prev = if (position == 0) -1 else list[position - 1]
    val next = if (position == list.size - 1) s.length else list[position + 1]
    count += (index - prev) * (next - index)
}
```

## 5. Integer overflow when counting substrings

A string of length n has `n * (n + 1) / 2` substrings. For n = 10^5 the product exceeds `Int.MAX_VALUE` before the
division and silently wraps around. Convert to `Long` before multiplying.

Seen in: `toolbox/string/Contribution.kt`

**Wrong**
```kotlin
val substrings = n * (n + 1) / 2 // n = 100_000 -> 705082704
```

**Correct**
```kotlin
val substrings = n.toLong() * (n + 1) / 2 // n = 100_000 -> 5000050000
```

## 6. `+=` on a String in a loop

Strings are immutable: every `+=` creates a new string and copies everything built so far. n appends are O(n²).
A `StringBuilder` appends in O(1) amortized.

Seen in: `questions/string/frequency_sort/solution/FrequencySortHashMap.kt`, `toolbox/string/StringBuilding.kt`

**Wrong**
```kotlin
var result = ""
for (char in s) {
    result += char // n = 10^5: ~1 s
}
```

**Correct**
```kotlin
val result = StringBuilder()
for (char in s) {
    result.append(char) // n = 10^5: ~1 ms
}
```

## 7. `toString()` on a `CharArray`

`CharArray` doesn't override `toString()`, so `chars.toString()` gives something like `[C@5d8a1456`, not the
characters. As a key, every word gets its own group. Use `String(chars)` or `chars.concatToString()`.

Seen in: `questions/string/group_anagrams/solution/GroupAnagramsSorting.kt`

**Wrong**
```kotlin
// [eat, tea, ate] -> [[eat], [tea], [ate]]
for (str in strs) {
    val chars = str.toCharArray()
    chars.sort()
    val key = chars.toString()
    groups.getOrPut(key) {
        mutableListOf()
    }.add(str)
}
```

**Correct**
```kotlin
// [eat, tea, ate] -> [[eat, tea, ate]]
for (str in strs) {
    val chars = str.toCharArray()
    chars.sort()
    val key = String(chars)
    groups.getOrPut(key) {
        mutableListOf()
    }.add(str)
}
```

## 8. `IntArray` as hash map key

Arrays use reference equality and hashCode, so two count arrays with the same content are different keys, and
anagrams end up in separate groups. A `List<Int>` compares by content.

Seen in: `questions/string/group_anagrams/solution/GroupAnagramsCharCount.kt`, `toolbox/string/CanonicalKey.kt`

**Wrong**
```kotlin
// [eat, tea, ate] -> [[eat], [tea], [ate]]
val groups = strs.groupBy { str ->
    val counts = IntArray(26)
    for (char in str) {
        counts[char - 'a']++
    }
    counts
}
```

**Correct**
```kotlin
// [eat, tea, ate] -> [[eat, tea, ate]]
val groups = strs.groupBy { str ->
    val counts = IntArray(26)
    for (char in str) {
        counts[char - 'a']++
    }
    counts.toList()
}
```

## 9. `char - 'a'` on characters outside a-z

`char - 'a'` is only a valid index for lowercase letters. For uppercase it's negative (`'A' - 'a' = -32`), and
the array access throws `ArrayIndexOutOfBoundsException`. Check which characters the constraints allow.

Seen in: `questions/string/unique_letter_string/UniqueLetterString.kt` (uppercase only),
`questions/string/close_strings/solution/CloseStringsFrequencyArray.kt` (lowercase only)

**Wrong**
```kotlin
val counts = IntArray(26)
for (char in s) {
    counts[char - 'a']++ // "ABC" -> ArrayIndexOutOfBoundsException: Index -32
}
```

**Correct**
```kotlin
val counts = IntArray(26)
for (char in s) {
    counts[char - 'A']++ // "ABC" -> [1, 1, 1, 0, ...]
}
```

## 10. Digit character vs. digit value

`char.code` is the character code, not the digit: `'1'.code` is 49. Subtract `'0'` or use `digitToInt()`.

**Wrong**
```kotlin
var sum = 0
for (char in "123") {
    sum += char.code // 49 + 50 + 51 = 150
}
```

**Correct**
```kotlin
var sum = 0
for (char in "123") {
    sum += char - '0' // 1 + 2 + 3 = 6, or char.digitToInt()
}
```

## 11. `split("")` to get the characters

Splitting by the empty string also matches before the first and after the last character, so the result has an
empty string at both ends.

**Wrong**
```kotlin
val chars = "abc".split("") // [, a, b, c, ], size 5
```

**Correct**
```kotlin
val chars = "abc".toList() // [a, b, c], size 3, or iterate the string directly
```

## 12. Precedence of `and` / `shl`

`and`, `or` and `shl` are ordinary infix functions with the same precedence, evaluated left to right. So
`mask and 1 shl i` is `(mask and 1) shl i`, which only looks at the bit for 'a'.

Seen in: `questions/string/close_strings/solution/CloseStringsBitMask.kt`, `toolbox/string/CharBitMask.kt`

**Wrong**
```kotlin
// mask of "uau", 's' -> true, mask of "bc", 'b' -> false
fun hasLetter(mask: Int, char: Char): Boolean = mask and 1 shl (char - 'a') != 0
```

**Correct**
```kotlin
// mask of "uau", 's' -> false, mask of "bc", 'b' -> true
fun hasLetter(mask: Int, char: Char): Boolean = mask and (1 shl (char - 'a')) != 0
```

## 13. `getOrDefault` doesn't store the default

`getOrDefault` returns the default without putting it into the map. Adding to that list changes a list that is thrown
away, and the map stays empty. `getOrPut` stores it.

Seen in: `questions/string/unique_letter_string/solution/UniqueLetterStringCharSplit.kt`,
`toolbox/string/Contribution.kt`

**Wrong**
```kotlin
// "ABA" -> {}
s.forEachIndexed { index, char ->
    indices.getOrDefault(char, mutableListOf()).add(index)
}
```

**Correct**
```kotlin
// "ABA" -> {A=[0, 2], B=[1]}
s.forEachIndexed { index, char ->
    indices.getOrPut(char) {
        mutableListOf()
    }.add(index)
}
```

## 14. Removing the first element of an `ArrayList`

`removeAt(0)` on an `ArrayList` (what `mutableListOf()` creates) shifts every remaining element one position to
the left. Taking all n elements from the front is O(n²). `ArrayDeque.removeFirst()` is O(1).

Seen in: `questions/string/unique_letter_string/solution/UniqueLetterStringNextOccurrences.kt`

**Wrong**
```kotlin
val indices = MutableList(n) { it }
while (indices.isNotEmpty()) {
    indices.removeAt(0) // n = 10^5: ~500 ms
}
```

**Correct**
```kotlin
val indices = ArrayDeque(List(n) { it })
while (indices.isNotEmpty()) {
    indices.removeFirst() // n = 10^5: a few ms
}
```
