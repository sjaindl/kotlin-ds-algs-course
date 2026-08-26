package big_o.rules.runners

import big_o.rules.minimumDifference
import big_o.rules.sortSteps
import big_o.rules.walkSteps

fun main() {
    for (n in listOf(100, 10_000, 1_000_000)) {
        sortSteps = 0
        walkSteps = 0
        minimumDifference(IntArray(n) { n - it })

        val share = sortSteps * 100 / (sortSteps + walkSteps)
        println("n = $n → sort: $sortSteps   walk: $walkSteps   sort is $share% of the work")
    }
}
