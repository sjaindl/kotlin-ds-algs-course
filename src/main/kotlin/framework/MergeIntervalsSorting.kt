package framework

/*
N: number of intervals
T: O(N log N)
S: O(N)

merged = empty list
sorted = sort intervals by start
for each interval in sorted:
    if interval start > last end in merged:
        append interval to merged
    else:
        extend last end in merged to max(last end, interval end)

return merged
 */

fun mergeBySorting(intervals: Array<IntArray>): Array<IntArray> {
    val merged = mutableListOf<IntArray>()
    val sorted = intervals.sortedBy { it[0] }

    sorted.forEach { interval ->
        val last = merged.lastOrNull()
        if (last == null || interval[0] > last[1]) {
            merged.add(intArrayOf(interval[0], interval[1])) // copy input array
        } else {
            last[1] = maxOf(last[1], interval[1])
        }
    }

    return merged.toTypedArray()
}
