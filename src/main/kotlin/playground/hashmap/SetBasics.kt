package playground.hashmap

/*
Sets
setOf(1, 2), mutableSetOf(), hashSetOf()   setOf / mutableSetOf keep insertion order (LinkedHashSet)
set.add(x)                                 Boolean: false when x was already there
set.remove(x), x in set, set.size
a union b, a intersect b, a subtract b     new sets, infix
a.containsAll(b)                           b is a subset of a
list.toSet(), list.toHashSet()             dedup in O(n)
set.retainAll(other)                       in place intersection

The seen-set pattern: if (!seen.add(x)) means "x was already seen", one hash lookup instead of two.

Kotlin pitfalls:
- intersect on a List returns a Set, the duplicates are gone.
- Mutating an object after putting it into a HashSet breaks the lookup, its hash code changed. Use immutable keys
  (data classes with val, String, List from listOf).

See toolbox: toolbox/hashmap/SetLookups.kt for O(1) lookups in a search.

n: number of elements
T: O(1) average for add / remove / contains, O(n + m) for union / intersect / subtract
S: O(n)
 */

// the first value that occurs a second time, null when all are distinct
fun <T> firstRepeated(items: List<T>): T? {
    val seen = HashSet<T>()
    for (item in items) {
        if (!seen.add(item)) return item
    }
    return null
}

// values that occur in both lists, each once, in the order of a
fun <T> common(a: List<T>, b: List<T>): List<T> = (a.toSet() intersect b.toSet()).toList()

// values in exactly one of the two sets (symmetric difference)
fun <T> onlyInOne(a: Set<T>, b: Set<T>): Set<T> = (a union b) subtract (a intersect b)

// true if every value of small occurs in big
fun <T> isSubset(small: Collection<T>, big: Collection<T>): Boolean = big.toHashSet().containsAll(small)
