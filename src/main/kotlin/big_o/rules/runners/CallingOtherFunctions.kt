package big_o.rules.runners

import big_o.rules.deduplicate
import big_o.rules.deduplicateFast
import big_o.rules.measure
import big_o.rules.reset
import big_o.rules.steps

fun main() {
    for (n in listOf(1_000, 10_000, 50_000)) {
        val items = List(n) { it } // all distinct — worst case

        reset()
        var ms = measure {
            deduplicate(items)
        }
        println("List.contains  n=$n → $steps iterations, ${ms}ms")

        reset()
        ms = measure {
            deduplicateFast(items)
        }
        println("HashSet        n=$n → $steps iterations, ${ms}ms")
        println()
    }
}
