package big_o.complexity.runners

import big_o.complexity.demo
import big_o.complexity.minimumDifference

fun main() {
    demo("minimumDifference([9,4,1,7,15])") {
        minimumDifference(intArrayOf(9, 4, 1, 7, 15))
    }
}
