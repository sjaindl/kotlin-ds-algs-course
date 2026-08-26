package big_o.complexity.runners

import big_o.complexity.canReachTarget
import big_o.complexity.demo

fun main() {
    demo("canReachTarget([3,34,4,12,5,2], 120)") {
        canReachTarget(intArrayOf(3, 34, 4, 12, 5, 2), 120)
    }
}
