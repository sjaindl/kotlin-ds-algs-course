package big_o.runners

import big_o.countTriplets
import big_o.demo

fun main() {
    demo("countTriplets([1,2,3,4,5,6,7,8,9,10], 12)") {
        countTriplets(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 12)
    }
}
