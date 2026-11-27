package questions.hashmap.randomized_collection.solution

import java.util.Locale

// one collection's three operations, so every approach runs through the same code
private class Operations(val insert: (Int) -> Boolean, val remove: (Int) -> Boolean, val getRandom: () -> Int)

// example 1: getRandom has no single expected value, so the expected column names what it may return
private val example = listOf(
    "insert" to 1 to "true",
    "insert" to 1 to "false",
    "insert" to 2 to "true",
    "getRandom" to null to "1 (2/3) or 2 (1/3)",
    "remove" to 1 to "true",
    "getRandom" to null to "1 or 2 (1/2 each)"
)

private val approaches = listOf<Pair<String, () -> Operations>>(
    "Bruteforce" to {
        RandomizedCollectionBruteforce().let {
            Operations(it::insert, it::remove, it::getRandom)
        }
    },
    "HashMap" to {
        RandomizedCollectionHashMap().let {
            Operations(it::insert, it::remove, it::getRandom)
        }
    }
)

private const val DRAWS = 30_000

// how often each value comes out of getRandom, as a share of DRAWS
private fun frequencies(collection: Operations) = (1..DRAWS).groupingBy {
    collection.getRandom()
}.eachCount().toSortedMap().entries.joinToString(", ") { (value, count) ->
    "$value: ${"%.3f".format(Locale.ROOT, count.toDouble() / DRAWS)}"
}

fun main() {
    approaches.forEach { (name, create) ->
        println("$name:")
        runCatching {
            val collection = create()
            example.forEach { (operation, expected) ->
                val (method, value) = operation
                val result = when (method) {
                    "insert" -> collection.insert(value!!).toString()
                    "remove" -> collection.remove(value!!).toString()
                    else -> "${collection.getRandom()}, over $DRAWS draws ${frequencies(collection)}"
                }
                val call = if (value == null) "$method()" else "$method($value)"
                println("  ${call.padEnd(length = 12)}expected $expected, got $result")
            }
        }.onFailure {
            if (it is NotImplementedError) println("  not implemented yet") else throw it
        }
        println()
    }
}
