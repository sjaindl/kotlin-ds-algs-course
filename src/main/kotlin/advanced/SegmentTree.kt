package advanced

class SegmentTree(
    private val defaultValue: Int = -1,
    private val leafValue: (array: IntArray, index: Int) -> Int = { _, index ->
        index
    },
    private val treeCondition: (Int, Int) -> Int, // Combine values or indices
) {

    lateinit var array: IntArray
    lateinit var tree: IntArray

    fun buildTree(nums: IntArray) {
        array = nums
        // 2 * N is only sufficient if nums is a multiple of 2, otherwise more space is needed due to
        // the complete tree being asymmetric, and not completely filled at the last level
        tree = IntArray(nums.size * 4)
        // Tree is 1-indexed (just like a heap to be able to perform index calculations)
        buildTree(nums = nums, treeIndex = 1, left = 0, right = nums.size - 1)
    }

    private fun buildTree(nums: IntArray, treeIndex: Int, left: Int, right: Int) {
        if (left == right) {
            // base case = leaf node
            tree[zeroIndex(treeIndex)] = leafValue(nums, left)
            return
        }

        val center = left + (right - left) / 2

        buildTree(nums = nums, treeIndex = treeIndex * 2, left = left, right = center)
        buildTree(nums = nums, treeIndex = treeIndex * 2 + 1, left = center + 1, right = right)

        val leftVal = tree[zeroIndex(treeIndex * 2)]
        val rightVal = tree[zeroIndex(treeIndex * 2 + 1)]
        tree[zeroIndex(treeIndex)] = treeCondition(leftVal, rightVal)
    }

    private fun zeroIndex(index: Int): Int = index - 1

    fun rangeCalc(fromIndex: Int, toIndex: Int): Int {
        return rangeCalc(treeIndex = 1, left = 0, right = array.size - 1, fromIndex = fromIndex, toIndex = toIndex)
    }

    private fun rangeCalc(treeIndex: Int, left: Int, right: Int, fromIndex: Int, toIndex: Int): Int {
        // 1. Range completely outside query range
        if (fromIndex > right || toIndex < left) {
            return defaultValue
        }

        // 2. Node range is completely within query range
        if (fromIndex <= left && right <= toIndex) {
            return tree[zeroIndex(treeIndex)]
        }

        val center = left + (right - left) / 2
        val leftResult = rangeCalc(treeIndex = treeIndex * 2, left = left, right = center, fromIndex = fromIndex, toIndex = toIndex)
        val rightResult = rangeCalc(treeIndex = treeIndex * 2 + 1, left = center + 1, right = right, fromIndex = fromIndex, toIndex = toIndex)

        // 3. Handle out-of-bounds
        if (leftResult == defaultValue) return rightResult
        if (rightResult == defaultValue) return leftResult

        return treeCondition(leftResult, rightResult)
    }

    fun update(index: Int, value: Int) {
        if (index < 0 || index >= array.size) return
        update(index = index, value = value, treeIndex = 1, left = 0, right = array.size - 1)
    }

    private fun update(index: Int, value: Int, treeIndex: Int, left: Int, right: Int) {
        if (left == right) {
            array[left] = value
            tree[zeroIndex(treeIndex)] = leafValue(array, left)
            return
        }

        val center = left + (right - left) / 2
        if (index <= center) {
            update(index = index, value = value, treeIndex = treeIndex * 2, left = left, right = center)
        } else {
            update(index = index, value = value, treeIndex = treeIndex * 2 + 1, left = center + 1, right = right)
        }

        val leftVal = tree[zeroIndex(treeIndex * 2)]
        val rightVal = tree[zeroIndex(treeIndex * 2 + 1)]
        tree[zeroIndex(treeIndex)] = treeCondition(leftVal, rightVal)
    }
}
