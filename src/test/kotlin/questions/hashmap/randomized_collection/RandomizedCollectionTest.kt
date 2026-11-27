package questions.hashmap.randomized_collection

import questions.hashmap.randomized_collection.solution.RandomizedCollectionBruteforce
import questions.hashmap.randomized_collection.solution.RandomizedCollectionHashMap
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RandomizedCollectionTest {

    // one collection's three operations, so every approach runs through the same checks
    private class Operations(val insert: (Int) -> Boolean, val remove: (Int) -> Boolean, val getRandom: () -> Int)

    // operations with the expected return of insert / remove; getRandom is checked against the expected contents
    private sealed interface Step {
        val value: Int
        val expected: Boolean
    }
    private data class Insert(override val value: Int, override val expected: Boolean) : Step
    private data class Remove(override val value: Int, override val expected: Boolean) : Step

    private val cases = listOf(
        // example 1, without its two getRandom calls
        listOf(Insert(1, true), Insert(1, false), Insert(2, true), Remove(1, true)),
        // remove from an empty collection, and a value that was never inserted
        listOf(Remove(1, false), Insert(1, true), Remove(2, false)),
        // remove the last added element: nothing has to move
        listOf(Insert(1, true), Insert(2, true), Remove(2, true), Remove(2, false), Remove(1, true)),
        // the moved element has the same value as the removed one
        listOf(Insert(1, true), Insert(1, false), Insert(2, true), Insert(1, false), Remove(1, true), Remove(1, true)),
        // remove every copy, then the value is new again
        listOf(Insert(5, true), Insert(5, false), Remove(5, true), Remove(5, true), Remove(5, false), Insert(5, true)),
        // several moves in a row across two values
        listOf(
            Insert(1, true), Insert(2, true), Insert(1, false), Insert(2, false), Insert(3, true),
            Remove(1, true), Remove(2, true), Remove(1, true), Remove(3, true), Remove(2, true), Remove(2, false)
        ),
        // constraint bounds
        listOf(
            Insert(Int.MIN_VALUE, true), Insert(Int.MAX_VALUE, true), Insert(Int.MIN_VALUE, false),
            Remove(Int.MAX_VALUE, true), Remove(Int.MIN_VALUE, true), Remove(Int.MIN_VALUE, true)
        )
    )

    // a reference multiset: value to how often it is in the collection
    private fun MutableMap<Int, Int>.apply(step: Step) {
        when (step) {
            is Insert -> merge(step.value, 1, Int::plus)
            is Remove -> if (step.expected) {
                computeIfPresent(step.value) { _, count ->
                    (count - 1).takeIf { it > 0 }
                }
            }
        }
    }

    // after every step, getRandom may only return values that are in the collection
    private fun checkRandom(collection: Operations, contents: Map<Int, Int>, context: String) {
        if (contents.isEmpty()) return
        repeat(5) {
            val random = collection.getRandom()
            assertTrue(random in contents, "getRandom() returned $random, contents $contents after $context")
        }
    }

    private fun verify(create: () -> Operations) {
        cases.forEach { steps ->
            val collection = create()
            val contents = mutableMapOf<Int, Int>()
            steps.forEachIndexed { i, step ->
                val context = steps.take(i + 1).toString()
                val result = when (step) {
                    is Insert -> collection.insert(step.value)
                    is Remove -> collection.remove(step.value)
                }
                assertEquals(step.expected, result, "last step of $context")
                contents.apply(step)
                checkRandom(collection, contents, context)
            }
        }
        verifyRandomOperations(create)
        verifyDistribution(create)
    }

    // 5000 random operations on few values (many duplicates), against the reference multiset
    private fun verifyRandomOperations(create: () -> Operations) {
        val random = Random(seed = 381)
        val collection = create()
        val contents = mutableMapOf<Int, Int>()
        repeat(5000) { i ->
            val value = random.nextInt(-3, 4)
            val step = if (random.nextBoolean()) {
                Insert(value, value !in contents)
            } else {
                Remove(value, value in contents)
            }
            val result = when (step) {
                is Insert -> collection.insert(step.value)
                is Remove -> collection.remove(step.value)
            }
            assertEquals(step.expected, result, "random operation $i: $step")
            contents.apply(step)
            checkRandom(collection, contents, "random operation $i: $step")
        }
    }

    // [1, 1, 2]: getRandom returns 1 two times out of three, after remove(1) half of the time
    private fun verifyDistribution(create: () -> Operations) {
        val collection = create()
        listOf(1, 1, 2).forEach {
            collection.insert(it)
        }
        assertEquals(2.0 / 3, share(collection, 1), 0.03, "share of 1 in [1, 1, 2]")
        collection.remove(1)
        assertEquals(0.5, share(collection, 1), 0.03, "share of 1 in [1, 2]")
    }

    private fun share(collection: Operations, value: Int): Double {
        val draws = 30_000
        return (1..draws).count {
            collection.getRandom() == value
        }.toDouble() / draws
    }

    @Test
    fun `starter solution`() {
        verify {
            RandomizedCollection().let {
                Operations(it::insert, it::remove, it::getRandom)
            }
        }
    }

    @Test
    fun `bruteforce solution`() {
        verify {
            RandomizedCollectionBruteforce().let {
                Operations(it::insert, it::remove, it::getRandom)
            }
        }
    }

    @Test
    fun `hash map solution`() {
        verify {
            RandomizedCollectionHashMap().let {
                Operations(it::insert, it::remove, it::getRandom)
            }
        }
    }
}
