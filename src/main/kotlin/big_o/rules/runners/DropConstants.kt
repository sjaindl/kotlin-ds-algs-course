package big_o.rules.runners

import big_o.rules.minAndMax
import big_o.rules.reset
import big_o.rules.reverse
import big_o.rules.steps
import big_o.rules.topHundred

fun main() {
    for (n in listOf(1_000, 10_000, 100_000)) {
        val nums = IntArray(n) { it }

        reset()
        minAndMax(nums)
        val a = steps

        reset()
        reverse(nums)
        val b = steps

        reset()
        topHundred(nums)
        val c = steps

        println("n = $n → minAndMax: $a   reverse: $b   topHundred: $c")
    }
}
