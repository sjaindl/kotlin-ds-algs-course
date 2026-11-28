package playground.collections

/*
Aggregations: a collection in, one value or one map out
count { }, sumOf { }                        sumOf { it.toLong() } against overflow
maxOf { }, minOf { }                        the largest value of the lambda
maxBy { }, minBy { }                        the element with the largest value, the first one on a tie
maxByOrNull { }, maxOrNull()                null on an empty collection instead of an exception
any { }, all { }, none { }                  stop at the first element that decides
fold(initial) { acc, x -> }                 any result type, works on an empty list
reduce { acc, x -> }                        starts with the first element, throws on an empty list
groupBy { }                                 Map<Key, List<T>>
groupingBy { }.eachCount()                  Map<Key, Int>, counting without an extra list per group

Kotlin pitfalls:
- max(), maxBy, reduce, first() throw NoSuchElementException on an empty collection. Use the ...OrNull versions
  when the input can be empty.
- all { } on an empty collection is true, any { } is false.

See toolbox: toolbox/string/CharCount.kt counts letters with an IntArray(26).

n: number of elements
T: O(n)
S: O(1) for a single value, O(n) for groupBy / eachCount
 */

// how often each element occurs: ["a", "b", "a"] -> {a=2, b=1}
fun <T> frequencies(items: List<T>): Map<T, Int> = items.groupingBy {
    it
}.eachCount()

// the most frequent element, the one that occurs first on a tie, null for an empty list
fun <T> mostFrequent(items: List<T>): T? = frequencies(items).maxByOrNull {
    it.value
}?.key

// the words grouped by their first letter: ["ant", "bee", "ape"] -> {a=[ant, ape], b=[bee]}
fun byFirstLetter(words: List<String>): Map<Char, List<String>> = words.filter {
    it.isNotEmpty()
}.groupBy {
    it.first()
}

// the product of all numbers as a Long, 1 for an empty list
fun product(numbers: List<Int>): Long = numbers.fold(1L) { acc, x ->
    acc * x
}
