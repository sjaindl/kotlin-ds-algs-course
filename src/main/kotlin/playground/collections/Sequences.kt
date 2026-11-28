package playground.collections

/*
Sequences: lazy collections
list.asSequence().map { }.filter { }.first()   one element at a time through the whole chain, stops early
generateSequence(1) { it * 2 }                 1, 2, 4, 8, ... infinite until a null or take(n)
generateSequence(seed) { next(it) }            follow a chain until next returns null
sequence { yield(x) }                          a generator
.toList()                                      runs the sequence (a terminal operation)

A List chain is eager: every step builds a full list. A Sequence chain is lazy: nothing runs until a terminal
operation (toList, first, sum, count, ...), and then only as far as needed.

When to reach for it: long chains on large inputs, an early stop (first, take, any), infinite or generated
streams. For a short chain on a small list, plain list calls are just as fast or faster.

Kotlin pitfall: a sequence runs again on every terminal operation. Calling .toList() twice does the work twice.

n: number of elements actually pulled through
T: O(n)
S: O(1) per step, plus what the terminal operation collects
 */

// the first n powers of two: 1, 2, 4, ...
fun powersOfTwo(n: Int): List<Long> = generateSequence(1L) {
    it * 2
}.take(n).toList()

// the Collatz steps from start until 1: 6 -> [6, 3, 10, 5, 16, 8, 4, 2, 1]
fun collatz(start: Int): List<Int> = generateSequence(start) {
    when {
        it == 1 -> null
        it % 2 == 0 -> it / 2
        else -> 3 * it + 1
    }
}.toList()

// the first element whose square is larger than limit, checking as few elements as needed
fun firstSquareAbove(numbers: List<Int>, limit: Int): Int? = numbers.asSequence().map {
    it * it
}.firstOrNull {
    it > limit
}
