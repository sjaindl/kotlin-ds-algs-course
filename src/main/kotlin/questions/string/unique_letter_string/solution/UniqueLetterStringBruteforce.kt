package questions.string.unique_letter_string.solution

/*
Generate every substring, count the characters in each of them with a hash map and add the number of characters
that occur exactly once.

"ABA" -> "A", "AB", "ABA", "B", "BA", "A" -> 1 + 2 + 1 + 1 + 2 + 1 = 8

n: length of s
T: O(n³) - n(n + 1) / 2 substrings, building and checking each one is O(length)
S: O(n³) - all substrings are stored, their total length is about n³ / 6 (plus O(n) recursion stack)
Too slow and too much memory for n = 10^5, and the recursion would overflow the stack.

substrings = []
for each start index:
    extend the substring by one character at a time, add every one to substrings
count = 0
for each substring t:
    counts = count of every character in t
    count += number of characters with count 1
return count
 */

fun uniqueLetterStringBruteforce(s: String): Int {

    val substrings = mutableListOf<String>()

    fun generateSubstrings(index: Int, sb: StringBuilder) {
        if (index == s.length) {
            return
        }

        sb.append(s[index])
        substrings.add(sb.toString())
        generateSubstrings(index + 1, sb)
    }

    fun generateSubstrings() {
        for (index in s.indices) {
            val sb = StringBuilder()
            generateSubstrings(index, sb)
        }
    }

    fun uniqueChars(s: String): Int {
        val map = mutableMapOf<Char, Int>()
        s.forEach {
            val count = map.getOrDefault(it, 0)
            map[it] = count + 1
        }

        return map.count { (_, count) ->
            count == 1
        }
    }

    generateSubstrings()

    var count = 0
    substrings.forEach {
        count += uniqueChars(it)
    }

    return count
}
