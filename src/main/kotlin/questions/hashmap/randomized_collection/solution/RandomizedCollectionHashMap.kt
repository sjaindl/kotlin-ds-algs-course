package questions.hashmap.randomized_collection.solution

import kotlin.random.Random

/*
Keep every number in a list, for getRandom by index, and a hash map from each value to the set of its indices in the
list, for insert and remove without a scan. Removing from the middle of a list shifts everything behind it, so remove
moves the last element into the gap instead, and only that moved element needs its index fixed in the map.

insert(1), insert(1), insert(2), insert(1)
numbers = [1, 1, 2, 1], indices = {1: {0, 1, 3}, 2: {2}}
remove(1): take index 0 from 1's set, move the last element (1 at index 3) to index 0
           1's set: drop 3, add 0 -> {1, 0}; pop the last slot
numbers = [1, 1, 2], indices = {1: {1, 0}, 2: {2}}

n: number of elements in the collection
T: O(1) average for insert, remove and getRandom - hash set operations and the list's last slot only
S: O(n) - the list and the index sets

insert(value):
    append value to numbers
    add numbers.lastIndex to indices[value], creating the set if needed
    return true if the set was empty before
remove(value):
    return false if value has no indices
    index = any index of value, remove it from value's set
    if index != lastIndex:
        moved = numbers[lastIndex]
        numbers[index] = moved
        in moved's set: remove lastIndex, add index
    remove the last slot of numbers
    drop value from indices if its set is empty
    return true
getRandom():
    return numbers[random index]
 */

class RandomizedCollectionHashMap {

    private val numbers = mutableListOf<Int>()
    private val indices = mutableMapOf<Int, MutableSet<Int>>() // value to its indices in numbers

    fun insert(value: Int): Boolean {
        numbers.add(value)
        val valueIndices = indices.getOrPut(value) {
            mutableSetOf()
        }
        val isNew = valueIndices.isEmpty()
        valueIndices.add(numbers.lastIndex)
        return isNew
    }

    fun remove(value: Int): Boolean {
        val valueIndices = indices[value] ?: return false
        val lastIndex = numbers.lastIndex

        // 1. take any index of value (a LinkedHashSet gives its first one in O(1)) out of its set
        val indexToRemove = valueIndices.first()
        valueIndices.remove(indexToRemove)

        // 2. move the last element into the gap, unless the gap is the last slot itself
        if (indexToRemove != lastIndex) {
            val moved = numbers[lastIndex]
            numbers[indexToRemove] = moved

            // 3. fix the moved element's index: remove the old one by value, it may sit anywhere in the set
            val movedIndices = indices.getValue(moved)
            movedIndices.remove(lastIndex)
            movedIndices.add(indexToRemove)
        }

        numbers.removeAt(lastIndex)

        if (valueIndices.isEmpty()) {
            indices.remove(value)
        }

        return true
    }

    fun getRandom(): Int {
        return numbers[Random.nextInt(numbers.size)]
    }
}
