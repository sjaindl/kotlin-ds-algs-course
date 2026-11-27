package toolbox.hashmap

/*
Custom classes as keys
A hash map finds a key in two steps: hashCode picks the bucket, equals finds the key in it. So a key class needs
both, and they must agree: equal objects must have equal hash codes. A data class generates both from the
properties in its constructor. Keep those properties val: a key that changes while it's in the map gets a new hash
code, and the map looks for it in the wrong bucket.

For two Ints (grid cells, coordinates, pairs of indices), there is a cheaper key than an object: pack both into
one Long. No object per key, and Long already has equals and hashCode.

When to reach for it: a key made of several values, like (row, col), (x, y) or (from, to).

Seen in:
    toolbox/string/CanonicalKey.kt            List as key, not IntArray
    lectures/hashmap/10_common_bugs.md        the bugs when this goes wrong

Kotlin:
- a regular class without equals / hashCode compares by identity: two Point(1, 2) are different keys
- arrays (IntArray, Array<Int>) compare by identity too, even inside a data class. Use a List instead
- Pair(x, y) works as a key, but boxes both Ints

Point(1, 2), Point(1, 2), Point(3, 4) -> {Point(x=1, y=2)=2, Point(x=3, y=4)=1}
pointKey(1, 2) = 1 shl 32 or 2 = 4294967298

n: number of keys
T: O(1) average per lookup, plus the time of hashCode / equals
S: O(n)
 */

// a key class: data class generates equals and hashCode from x and y, and val keeps them fixed
data class Point(val x: Int, val y: Int)

// how often every point occurs: equal points land in the same entry
fun countPoints(points: List<Point>): Map<Point, Int> {
    val counts = HashMap<Point, Int>()
    for (point in points) {
        counts[point] = counts.getOrDefault(point, 0) + 1
    }
    return counts
}

// two Ints packed into one Long: x in the upper 32 bits, y in the lower 32 bits
fun pointKey(x: Int, y: Int): Long = (x.toLong() shl 32) or (y.toLong() and 0xFFFFFFFFL)

// and back again
fun xOf(key: Long): Int = (key shr 32).toInt()

fun yOf(key: Long): Int = key.toInt()
