package playground.idioms

/*
Null safety
x?.length                  null if x is null, else the length
x ?: default               the default when x is null, also x ?: return / x ?: throw
x!!                        "I know it is not null": NullPointerException otherwise
x?.let { use(it) }         run a block only when x is not null
requireNotNull(x) { }      throws IllegalArgumentException with a message
x.takeIf { it > 0 }        x when the condition holds, else null
list.filterNotNull(), list.firstOrNull(), map[key]
runCatching { }            a Result: success with the value, or failure with the exception
  .getOrNull(), .getOrElse { e -> }, .getOrDefault(x), .onSuccess { }, .onFailure { e -> }

When !! is fine in an interview: right after a check the compiler cannot see, e.g. pq.poll()!! after
pq.isNotEmpty(), or map[key]!! when the key was just inserted. Say so out loud.

Kotlin pitfalls:
- A value from Java (platform type, e.g. PriorityQueue.poll()) can be null even though Kotlin lets you use it
  as non-null. Treat it as nullable.
- runCatching catches every Throwable, also errors you did not expect. Rethrow what you cannot handle, like the
  course runners do: getOrElse { if (it is NotImplementedError) "not implemented yet" else throw it }.

T: O(1)
S: O(1)
 */

// the length of the string, 0 for null
fun lengthOrZero(s: String?): Int = s?.length ?: 0

// the number in s if it is positive, else null: "42" -> 42, "-3" -> null, "x" -> null
fun positiveOrNull(s: String): Int? = s.toIntOrNull()?.takeIf {
    it > 0
}

// a / b, or the message of the exception when b is 0
fun safeDivide(a: Int, b: Int): String = runCatching {
    (a / b).toString()
}.getOrElse {
    if (it is ArithmeticException) "error: ${it.message}" else throw it
}

// the first word that starts with prefix, uppercased, or "none"
fun firstWithPrefix(words: List<String>, prefix: String): String = words.firstOrNull {
    it.startsWith(prefix)
}?.uppercase() ?: "none"
