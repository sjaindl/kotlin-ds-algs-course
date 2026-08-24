package big_o.runners

import big_o.demo
import big_o.sumUpTo
import big_o.sumUpToByLooping

fun main() {
    demo("sumUpTo(1000)") {
        sumUpTo(1000)
    }

    demo("sumUpToByLooping(1000)") {
        sumUpToByLooping(1000)
    }
}
