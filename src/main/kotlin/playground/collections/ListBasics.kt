package playground.collections

/*
Lists
listOf(1, 2), emptyList<Int>()         read-only List
mutableListOf(1, 2), ArrayList<Int>()  MutableList, backed by an ArrayList
List(3) { it * 2 } = [0, 2, 4]         MutableList(3) { 0 } for a mutable one
list.add(x)                            O(1) amortized at the end
list.add(0, x), list.removeAt(0)       O(n): every later element moves by one
list.removeAt(list.lastIndex)          O(1)
list.subList(1, 3)                     a view, no copy: changes write through to the list
list.first(), list.last()              throw on an empty list, firstOrNull() / lastOrNull() return null
list.getOrNull(i), list.getOrElse(i) { default }
list + x, list - x, list1 + list2      new lists

Kotlin pitfalls:
- list.removeLast() compiles to Java 21's List.removeLast() and crashes on older JVMs and Android below API 35.
  removeAt(lastIndex) works everywhere.
- remove(x) on a MutableList<Int> removes the value x, removeAt(i) removes the index i.
- A read-only List is not immutable: a MutableList can hide behind it and still change.

See toolbox: toolbox/hashmap/SwapRemove.kt removes from the middle in O(1) when the order does not matter.

n: number of elements
T: O(1) for get / set / add at the end, O(n) for add / remove at the front or in the middle
S: O(n)
 */

// removes and returns the last element, null for an empty list (stack pop)
fun <T> MutableList<T>.popOrNull(): T? = if (isEmpty()) null else removeAt(lastIndex)

// moves the element at from to index to, the other elements shift by one
fun <T> MutableList<T>.move(from: Int, to: Int) {
    val element = removeAt(from)
    add(to, element)
}

// the element at index i, or default when i is out of range
fun <T> List<T>.at(i: Int, default: T): T = getOrElse(i) {
    default
}
