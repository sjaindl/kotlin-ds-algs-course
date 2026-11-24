package questions.hashmap.palindrome_pairs.solution

/*
Try every pair of words in both orders: concatenate them and check the result with two pointers, one from each
end, moving inward until they meet or find a mismatch.

["lls", "s", "sssll"]
"lls" + "s"     = "llss"     -> no
"s" + "lls"     = "slls"     -> palindrome, [1, 0]
"lls" + "sssll" = "llssssll" -> palindrome, [0, 2]
…

n: number of words, k: length of the longest word
T: O(n² · k) - n² pairs, each concatenated and checked in O(k)
S: O(k) - the concatenation, not counting the output

pairs = empty list
for i in 0 until n:
    for j in i + 1 until n:
        if isPalindrome(words[i] + words[j]): add [i, j]
        if isPalindrome(words[j] + words[i]): add [j, i]
return pairs
 */

fun palindromePairsBruteforce(words: Array<String>): List<List<Int>> {
    val palindromePairs = mutableListOf<List<Int>>()

    for (word1Index in words.indices) {
        for (word2Index in word1Index + 1 until words.size) {
            val word1 = words[word1Index]
            val word2 = words[word2Index]
            if (isPalindrome(word1 + word2)) {
                palindromePairs.add(listOf(word1Index, word2Index))
            }
            if (isPalindrome(word2 + word1)) {
                palindromePairs.add(listOf(word2Index, word1Index))
            }
        }
    }

    return palindromePairs
}

// two pointers, from both ends inward
private fun isPalindrome(word: String): Boolean {
    var left = 0
    var right = word.length - 1
    while (left < right) {
        if (word[left] != word[right]) return false
        left++
        right--
    }
    return true
}
