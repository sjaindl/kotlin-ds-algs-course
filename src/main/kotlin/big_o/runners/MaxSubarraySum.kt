package big_o.runners

import big_o.demo
import big_o.maxSubarraySum

fun main() {
    demo("maxSubarraySum([-2,1,-3,4,-1,2,1,-5,4,2])") {
        maxSubarraySum(intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4, 2))
    }
}
