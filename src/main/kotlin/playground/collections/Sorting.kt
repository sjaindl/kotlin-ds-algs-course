package playground.collections

/*
Sorting
                          new list                          in place (MutableList / Array)
natural order             sorted(), sortedDescending()      sort(), sortDescending()
by a key                  sortedBy { }, sortedByDescending  sortBy { }, sortByDescending { }
by a Comparator           sortedWith(comparator)            sortWith(comparator)

compareBy({ it.age }, { it.name })                  age first, then name on a tie
compareBy<P> { it.age }.thenByDescending { it.name }
compareByDescending { it.score }, reverseOrder(), naturalOrder()
intervals.sortBy { it[0] }                          Array<IntArray> by start

Sorting objects is stable: equal elements keep their input order. That is why sorting twice works: sort by the
secondary key first, then by the primary key.

Kotlin pitfalls:
- Comparator { a, b -> a - b } overflows for large or negative values: compare(Int.MIN_VALUE, 1) becomes positive.
  Use a.compareTo(b) or compareBy.
- sortedBy on an IntArray returns a List<Int>, not an IntArray.

n: number of elements
T: O(n log n)
S: O(n) for the new list, O(log n) .. O(n) inside the sort
 */

data class Person(val name: String, val age: Int)

// oldest first, by name on equal age
fun oldestFirst(people: List<Person>): List<Person> {
    val order = compareByDescending<Person> {
        it.age
    }.thenBy {
        it.name
    }
    return people.sortedWith(order)
}

// intervals sorted by start, then by end: [[3, 4], [1, 5], [1, 2]] -> [[1, 2], [1, 5], [3, 4]]
fun sortIntervals(intervals: Array<IntArray>) {
    val order = compareBy<IntArray> {
        it[0]
    }.thenBy {
        it[1]
    }
    intervals.sortWith(order)
}

// the words sorted by length, alphabetically on equal length
fun byLengthThenAlphabet(words: List<String>): List<String> {
    val order = compareBy<String> {
        it.length
    }.thenBy {
        it
    }
    return words.sortedWith(order)
}

// the k largest numbers, largest first
fun topK(numbers: List<Int>, k: Int): List<Int> = numbers.sortedDescending().take(k)
