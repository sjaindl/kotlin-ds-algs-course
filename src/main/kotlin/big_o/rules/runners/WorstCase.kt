package big_o.rules.runners

import big_o.rules.contains
import big_o.rules.reset
import big_o.rules.steps

fun main() {
    val nums = IntArray(1_000) { it } // 0, 1, 2, ... 999

    reset()
    contains(nums, 0)
    println("Target at the front → $steps steps")

    reset()
    contains(nums, 999)
    println("Target at the back  → $steps steps")

    reset()
    contains(nums, -1)
    println("Target not present  → $steps steps")
}
