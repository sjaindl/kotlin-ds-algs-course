package big_o.runners

import big_o.demo
import big_o.search

fun main() {
    demo("search([1,3,5,7,9,11], 7)") {
        search(intArrayOf(1, 3, 5, 7, 9, 11), 7)
    }
}
