package playground.idioms

import kotlin.random.Random

/*
Random numbers (kotlin.random.Random)
Random.nextInt(n)              0 until n, an index into a list of size n
Random.nextInt(from, until)    from until until, the upper bound is exclusive
Random.nextDouble(), Random.nextBoolean()
list.random(), (1..6).random() a random element, throws on an empty collection
list.shuffled(), list.shuffle() a new shuffled list / in place
Random(42)                     a seeded generator: the same sequence on every run, for tests you can repeat

When to reach for it: getRandom() in O(1) (pick a random index into a list), random pivots in quickselect,
shuffling, reservoir sampling.

Kotlin pitfalls:
- nextInt(n) never returns n, nextInt(0) throws. Make sure the list is not empty before picking from it.
- list.random() on an empty list throws NoSuchElementException, randomOrNull() returns null.
- Pass a Random as a parameter (default Random) so tests can hand in a seeded one.

n: size of the collection
T: O(1) for nextInt and random() on a List, O(n) for shuffled
S: O(1), O(n) for shuffled
 */

// a uniformly random element in O(1), null for an empty list
fun <T> pick(items: List<T>, random: Random = Random): T? {
    if (items.isEmpty()) return null
    return items[random.nextInt(items.size)]
}

// a random int in [low, high], both ends inclusive
fun between(low: Int, high: Int, random: Random = Random): Int = random.nextInt(low, high + 1)

// k distinct elements chosen at random (reservoir sampling): every element ends up in the result with chance k / n
fun <T> sample(items: List<T>, k: Int, random: Random = Random): List<T> {
    val reservoir = items.take(k).toMutableList()
    for (i in k until items.size) {
        val j = random.nextInt(i + 1)
        if (j < k) {
            reservoir[j] = items[i]
        }
    }
    return reservoir
}
