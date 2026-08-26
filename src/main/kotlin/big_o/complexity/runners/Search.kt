package big_o.complexity.runners

import big_o.complexity.demo
import big_o.complexity.search

fun main() {
    demo("search([1,3,5,7,9,11], 7)") {
        search(intArrayOf(1, 3, 5, 7, 9, 11), 7)
    }
}
