package big_o.runners

import big_o.canReachTarget
import big_o.demo

fun main() {
    demo("canReachTarget([3,34,4,12,5,2], 120)") {
        canReachTarget(intArrayOf(3, 34, 4, 12, 5, 2), 120)
    }
}
