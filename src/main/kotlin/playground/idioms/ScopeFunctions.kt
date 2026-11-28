package playground.idioms

/*
Scope functions
            the object is   returns          typical use
let         it              lambda result    x?.let { }: work with a non-null value
run         this            lambda result    compute a value from an object's members
with(x)     this            lambda result    many calls on one object
apply       this            the object       configure an object: Node(1).apply { next = Node(2) }
also        it              the object       side effects, logging, the swap a = b.also { b = a }

Rule of thumb: apply / also return the object, let / run / with return the lambda result. this-functions read
like member calls, it-functions keep the outer this visible.

Kotlin pitfall: a = b.also { b = a } works because also first evaluates to the old b, then the lambda sets b to a,
and only then is a assigned. It works for array elements too, arr[i] = arr[j].also { arr[j] = arr[i] }, but it
is easy to misread: in an interview, say what it does or use a temp variable.

T: O(1) overhead, the functions are inline
S: O(1)
 */

class ListNode(val value: Int) {
    var next: ListNode? = null
}

// a linked list from the values, built with apply: [1, 2, 3] -> 1 -> 2 -> 3
fun linkedListOf(vararg values: Int): ListNode? {
    var head: ListNode? = null
    for (value in values.reversed()) {
        head = ListNode(value).apply {
            next = head
        }
    }
    return head
}

// the values of a linked list, run on each node
fun ListNode?.values(): List<Int> {
    val result = mutableListOf<Int>()
    var node = this
    while (node != null) {
        node = node.run {
            result.add(value)
            next
        }
    }
    return result
}

// swaps two array elements with also, no temp variable
fun IntArray.swapWithAlso(i: Int, j: Int) {
    this[i] = this[j].also {
        this[j] = this[i]
    }
}

// a summary of the numbers, with gives access to all members without repeating the receiver
fun summary(numbers: List<Int>): String = with(numbers) {
    if (isEmpty()) "empty" else "size=$size min=${min()} max=${max()}"
}
