package big_o

fun main() {
    demo("sumUpTo(1000)") {
        sumUpTo(1000)
    }

    demo("sumUpToByLooping(1000)") {
        sumUpToByLooping(1000)
    }

    demo("search([1,3,5,7,9,11], 7)") {
        search(intArrayOf(1, 3, 5, 7, 9, 11), 7)
    }

    demo("maxSubarraySum([-2,1,-3,4,-1,2,1,-5,4])") {
        maxSubarraySum(intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4))
    }

    demo("minimumDifference([9,4,1,7])") {
        minimumDifference(intArrayOf(9, 4, 1, 7))
    }

    demo("bubbleSort([5,1,4,2,8])") {
        val nums = intArrayOf(5, 1, 4, 2, 8)
        bubbleSort(nums)
        nums.toList()
    }

    demo("countTriplets([1,2,3,4,5,6,7,8,9,10], 12)") {
        countTriplets(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 12)
    }

    demo("canReachTarget([3,34,4,12,5,2], 9)") {
        canReachTarget(intArrayOf(3, 34, 4, 12, 5, 2), 9)
    }

    demo("shortestRoute(6 cities)") {
        val distances = arrayOf(
            intArrayOf(0, 10, 15, 20, 25, 30),
            intArrayOf(10, 0, 35, 25, 30, 20),
            intArrayOf(15, 35, 0, 30, 10, 25),
            intArrayOf(20, 25, 30, 0, 15, 35),
            intArrayOf(25, 30, 10, 15, 0, 40),
            intArrayOf(30, 20, 25, 35, 40, 0)
        )
        shortestRoute(distances, mutableListOf(1, 2, 3, 4, 5))
    }
}
