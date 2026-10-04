package advanced

fun main() {
    val nums = intArrayOf(1, 4, 5, 9, 10, 12)
    val sumSegmentTree = SegmentTree(
        defaultValue = 0,
        leafValue = { array, index ->
            array[index]
        },
    ) { a, b ->
        a + b
    }

    sumSegmentTree.buildTree(nums = nums)

    println("Initial array: ${nums.contentToString()}")
    println("Sum range [0..5]: ${sumSegmentTree.rangeCalc(fromIndex = 0, toIndex = 5)}") // 41
    println("Sum range [0..2]: ${sumSegmentTree.rangeCalc(fromIndex = 0, toIndex = 2)}") // 10
    println("Sum range [3..5]: ${sumSegmentTree.rangeCalc(fromIndex = 3, toIndex = 5)}") // 31
    println("Sum range [1..4]: ${sumSegmentTree.rangeCalc(fromIndex = 1, toIndex = 4)}") // 28

    sumSegmentTree.update(index = 2, value = 6)
    println("\nUpdated index 2 to 6. Array is now: ${nums.contentToString()}")
    println("Sum range [0..5]: ${sumSegmentTree.rangeCalc(fromIndex = 0, toIndex = 5)}") // 42
}
