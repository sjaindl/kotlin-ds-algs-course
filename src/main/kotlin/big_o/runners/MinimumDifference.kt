package big_o.runners

import big_o.demo
import big_o.minimumDifference

fun main() {
    demo("minimumDifference([9,4,1,7,15])") {
        minimumDifference(intArrayOf(9, 4, 1, 7, 15))
    }
}
