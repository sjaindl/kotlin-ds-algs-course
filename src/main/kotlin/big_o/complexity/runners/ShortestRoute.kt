package big_o.complexity.runners

import big_o.complexity.demo
import big_o.complexity.shortestRoute

fun main() {
    demo("shortestRoute(6 cities)") {
        val distances = arrayOf(
            intArrayOf(0, 10, 15, 20, 25, 30),
            intArrayOf(10, 0, 35, 25, 30, 20),
            intArrayOf(15, 35, 0, 30, 10, 25),
            intArrayOf(20, 25, 30, 0, 15, 35),
            intArrayOf(25, 30, 10, 15, 0, 40),
            intArrayOf(30, 20, 25, 35, 40, 0)
        )
        shortestRoute(distances = distances, unvisited = mutableListOf(1, 2, 3, 4, 5))
    }
}
