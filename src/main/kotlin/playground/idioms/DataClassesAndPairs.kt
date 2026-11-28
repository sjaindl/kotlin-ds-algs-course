package playground.idioms

/*
Data classes, pairs and when
data class Point(val x: Int, val y: Int)   equals, hashCode, toString, copy and componentN for free
p.copy(y = 0)                              a changed copy
val (x, y) = p                             destructuring
1 to "a"                                   Pair(1, "a"), pair.first / pair.second
Triple(1, 2, 3)
for ((i, x) in list.withIndex()), for ((key, value) in map)
when (x) { in 1..9 -> ...; 10, 20 -> ...; is String -> ...; else -> ... }

A data class is the right key for a HashMap or HashSet: two points with the same x and y are equal. A plain class
compares by reference, so a new object never finds the old one.

Kotlin pitfalls:
- Only the properties in the primary constructor count for equals and hashCode.
- Use val, not var: changing a key inside a HashMap breaks its lookups.
- Pair is fine for a quick result, a data class with named fields reads better once it travels further.

T: O(1) for equals / hashCode / copy (O(number of fields))
S: O(1)
 */

data class Point(val x: Int, val y: Int)

// the four neighbours of p in a grid: up, down, left, right
fun neighbours(p: Point): List<Point> = listOf(
    p.copy(y = p.y - 1),
    p.copy(y = p.y + 1),
    p.copy(x = p.x - 1),
    p.copy(x = p.x + 1),
)

// the smallest and largest value together, null for an empty list
fun minMax(numbers: List<Int>): Pair<Int, Int>? {
    if (numbers.isEmpty()) return null
    return numbers.min() to numbers.max()
}

// a grade from a score, when with ranges
fun grade(score: Int): Char = when (score) {
    in 90..100 -> 'A'
    in 75..<90 -> 'B'
    in 50..<75 -> 'C'
    in 0..<50 -> 'F'
    else -> throw IllegalArgumentException("score out of range: $score")
}
