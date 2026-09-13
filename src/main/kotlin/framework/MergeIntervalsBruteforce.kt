package framework

/*
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].

n: number of intervals
T: O(n^2)
S: O(n)

merged = empty list
for each interval in intervals:
    overlapping ← all intervals in merged that overlap interval
    if overlapping is empty:
        add interval to merged
    else:
        replace all overlapping intervals with merged one
return merged
 */

fun mergeBruteforce(intervals: Array<IntArray>): Array<IntArray> {
    val merged = mutableListOf<IntArray>()

    intervals.forEach { interval ->
        val matching = merged.filter {
            !(it[0] > interval[1] || it[1] < interval[0])
        }

        if (matching.isEmpty()) {
            merged.add(interval)
        } else {
            val start = minOf(
                interval[0],
                matching.minOf { it[0] }
            )

            val end = maxOf(
                interval[1],
                matching.maxOf { it[1] }
            )

            merged.removeAll(matching)
            merged.add(intArrayOf(start, end))
        }
    }

    return merged.toTypedArray()
}
