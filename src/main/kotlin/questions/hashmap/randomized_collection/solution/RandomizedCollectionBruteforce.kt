package questions.hashmap.randomized_collection.solution

import kotlin.random.Random

/*
Keep every number in one list. A random index gives a fair random element: a value that is in the list twice is
returned twice as often. But insert has to scan the list to know whether the value is new, and remove has to find
the value and close the gap behind it.

insert(1), insert(1), insert(2) -> [1, 1, 2]
remove(1): indexOf(1) = 0, removeAt(0) shifts 1 and 2 to the left -> [1, 2]

n: number of elements in the collection
T: insert O(n) - the contains scan, remove O(n) - the scan and the shift, getRandom O(1)
S: O(n) - the list

insert(value):
    isNew = value not in numbers
    append value
    return isNew
remove(value):
    index = indexOf(value), return false if -1
    removeAt(index)
    return true
getRandom():
    return numbers[random index]
 */

class RandomizedCollectionBruteforce {

    private val numbers = mutableListOf<Int>()

    fun insert(value: Int): Boolean {
        val isNew = value !in numbers
        numbers.add(value)
        return isNew
    }

    fun remove(value: Int): Boolean {
        val index = numbers.indexOf(value)
        if (index == -1) return false
        numbers.removeAt(index)
        return true
    }

    fun getRandom(): Int {
        return numbers[Random.nextInt(numbers.size)]
    }
}
