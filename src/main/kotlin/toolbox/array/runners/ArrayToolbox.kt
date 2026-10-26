package toolbox.array.runners

import toolbox.array.circularIndex
import toolbox.array.circularSlice
import toolbox.array.doubled
import toolbox.array.isPresent
import toolbox.array.markPresent
import toolbox.array.nextIndicesCircular
import toolbox.array.placeAt
import toolbox.array.presence
import toolbox.array.replaceOutOfRange
import toolbox.array.reverseRange
import toolbox.array.rotateLeft
import toolbox.array.rotateRight
import toolbox.array.swap

fun main() {
    println("Cyclic arrays (modulo)")
    println("  -1 % 5 = ${-1 % 5}, circularIndex(-1, 5) = ${circularIndex(-1, 5)}, circularIndex(7, 5) = ${circularIndex(7, 5)}")
    println("  circularIndex(10, 3) = ${circularIndex(10, 3)}, nextIndicesCircular(3, 5) = ${nextIndicesCircular(3, 5)}")
    println()

    val numbers = intArrayOf(1, 2, 3, 4)
    println("Double array trick on ${numbers.contentToString()}")
    println("  doubled             = ${doubled(numbers).contentToString()}")
    println("  circularSlice(3, 3) = ${circularSlice(numbers, 3, 3).contentToString()}")
    println()

    val values = intArrayOf(3, 4, -1, 1)
    println("Trade space for time on ${values.contentToString()}")
    println("  presence(maxValue = 4) = ${presence(values, 4).contentToString()}")
    val shifted = placeAt(values) { index ->
        (index + 1) % values.size
    }
    println("  placeAt((index + 1) mod n) = ${shifted.contentToString()}")
    println()

    val marks = intArrayOf(3, 4, -1, 1)
    println("In-place mutation on ${marks.contentToString()}")
    val replaced = replaceOutOfRange(marks, marks.size, 1)
    println("  replaceOutOfRange(1..4, with 1): $replaced replaced -> ${marks.contentToString()}")
    marks.copyOf().forEach { value ->
        markPresent(marks, value)
    }
    val present = (1..marks.size).filter { value ->
        isPresent(marks, value)
    }
    println("  after sign marking: ${marks.contentToString()}, present: $present")
    val swapped = intArrayOf(1, 2, 3)
    swapped.swap(0, 2)
    println("  [1, 2, 3].swap(0, 2) = ${swapped.contentToString()}")
    println()

    val rotated = intArrayOf(1, 2, 3, 4, 5, 6, 7)
    println("Reverse trick on ${rotated.contentToString()}")
    rotated.reverseRange(1, 3)
    println("  reverseRange(1, 3) = ${rotated.contentToString()}")
    rotated.reverseRange(1, 3)
    rotateRight(rotated, 3)
    println("  rotateRight(3)     = ${rotated.contentToString()}")
    rotateLeft(rotated, 3)
    println("  rotateLeft(3)      = ${rotated.contentToString()}")
}
