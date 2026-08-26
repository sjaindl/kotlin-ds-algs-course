package big_o.rules.runners

import big_o.rules.common
import big_o.rules.commonNoExtraSpace
import big_o.rules.reset
import big_o.rules.steps

fun main() {
    val a = List(10_000) { "user$it" }
    val b = List(20) { "user$it" }

    reset()
    common(a, b)
    println("O(a + b) → $steps steps")

    reset()
    commonNoExtraSpace(a, b)
    println("O(a × b) → $steps steps")
}
