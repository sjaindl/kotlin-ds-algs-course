package playground.collections

/*
Transformations: every call returns a new collection, the input stays as it is
map { }, mapIndexed { i, x -> }, mapNotNull { }        one result per element, mapNotNull drops nulls
filter { }, filterNot { }, filterIndexed { i, x -> }
flatMap { }, flatten()                                 [[1, 2], [3]] -> [1, 2, 3]
zip(other)                                             [1, 2] zip [a, b] -> [(1, a), (2, b)], as long as the shorter
zipWithNext()                                          [1, 4, 9] -> [(1, 4), (4, 9)], neighbours
chunked(2), windowed(2)                                [1, 2, 3] -> [[1, 2], [3]] / [[1, 2], [2, 3]]
partition { }                                          Pair(matching, rest)
distinct(), distinctBy { }                             first occurrence wins, order kept
associate { k to v }, associateBy { }, associateWith { }   list -> map, a later duplicate key wins

Kotlin pitfall: a chain like list.map { }.filter { }.take(1) builds a full list at every step. For long chains on
big inputs, or when you only need a few results, use asSequence() (see Sequences.kt).

n: number of elements
T: O(n) per call (windowed(k): O(n * k))
S: O(n) for the new collection
 */

// the differences between neighbours: [1, 4, 9] -> [3, 5]
fun differences(numbers: List<Int>): List<Int> = numbers.zipWithNext { a, b ->
    b - a
}

// splits numbers into (even, odd), both in input order
fun evenOdd(numbers: List<Int>): Pair<List<Int>, List<Int>> = numbers.partition {
    it % 2 == 0
}

// each word mapped to its length: ["hi", "you"] -> {hi=2, you=3}
fun lengths(words: List<String>): Map<String, Int> = words.associateWith {
    it.length
}

// the matrix turned by 90 degrees on its diagonal: rows become columns
fun transpose(matrix: List<List<Int>>): List<List<Int>> {
    if (matrix.isEmpty()) return emptyList()
    return matrix[0].indices.map { col ->
        matrix.map { row ->
            row[col]
        }
    }
}
