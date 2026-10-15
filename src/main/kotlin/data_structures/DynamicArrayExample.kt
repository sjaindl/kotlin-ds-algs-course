package data_structures

fun main() {
    val array = DynamicArray<Int>()
    println("Empty: $array, size=${array.size}, capacity=${array.capacity}") // [], size=0, capacity=4

    println("\nAdding 1..9 (capacity doubles when full):")
    for (value in 1..9) {
        array.add(value)
        println("add($value) -> size=${array.size}, capacity=${array.capacity}") // capacity 4 -> 8 -> 16
    }
    println("Array: $array") // [1, 2, 3, 4, 5, 6, 7, 8, 9]

    array.add(index = 0, element = 0)
    println("\nInserted 0 at index 0: $array") // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

    array[5] = 50
    println("Set index 5 to 50: $array") // [0, 1, 2, 3, 4, 50, 6, 7, 8, 9]
    println("Get index 5: ${array[5]}") // 50
    println("indexOf(7): ${array.indexOf(7)}") // 7
    println("Contains 42: ${42 in array}") // false

    println("\nRemoved index 0: ${array.removeAt(0)} -> $array") // 0 -> [1, 2, 3, 4, 50, 6, 7, 8, 9]

    println("\nRemoving from the end (capacity halves when a quarter full):")
    while (array.size > 1) {
        val removed = array.removeLast()
        println("removeLast() = $removed -> size=${array.size}, capacity=${array.capacity}") // capacity 16 -> 8 -> 4
    }

    print("\nIterating: ")
    for (value in array) print("$value ") // 1
    println()
}
