package big_o.complexity.runners

import big_o.complexity.demo
import big_o.complexity.maxSubarraySum

fun main() {
    demo("maxSubarraySum([-2,1,-3,4,-1,2,1,-5,4,2])") {
        maxSubarraySum(intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4, 2))
    }
}
