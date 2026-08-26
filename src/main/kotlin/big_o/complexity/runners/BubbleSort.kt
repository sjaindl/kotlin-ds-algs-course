package big_o.complexity.runners

import big_o.complexity.bubbleSort
import big_o.complexity.demo

fun main() {
    demo("bubbleSort([5,1,4,2,8,3])") {
        val nums = intArrayOf(5, 1, 4, 2, 8, 3)
        bubbleSort(nums)
        nums.toList()
    }
}
