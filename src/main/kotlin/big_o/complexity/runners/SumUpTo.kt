package big_o.complexity.runners

import big_o.complexity.demo
import big_o.complexity.sumUpTo
import big_o.complexity.sumUpToByLooping

fun main() {
    demo("sumUpTo(1000)") {
        sumUpTo(1000)
    }

    demo("sumUpToByLooping(1000)") {
        sumUpToByLooping(1000)
    }
}
