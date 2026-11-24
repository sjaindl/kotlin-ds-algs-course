package questions.hashmap.palindrome_pairs.solution

/*
Instead of trying every partner, ask the hash map for the one partner that can work. A map from word to index finds
a word in O(k). For every word, three kinds of partners make a palindrome:
1. its full reverse: word + reverse(word)
2. after a palindromic prefix, the reverse of the rest goes in front: reverse(rest) + prefix + rest
3. before a palindromic suffix, the reverse of the front goes behind: front + suffix + reverse(front)

No pair is found twice: in 1 both words have the same length, in 2 the partner is shorter than word, in 3 too, and
2 for word i and 3 for word j would need each word to be shorter than the other. The empty word is found by 2 and
3, when the whole word is the palindromic prefix or suffix.

["lls", "s", "sssll"], wordIndex = {lls: 0, s: 1, sssll: 2}
"lls":   reverse "sll" missing; prefix "l" + "ls" -> "sl" missing; prefix "ll" + "s" -> "s" found: [1, 0] "slls"
         suffix "s", front "ll" -> "ll" missing
"s":     reverse "s" is the word itself; prefix "s" + "" -> "" missing; suffix "s", front "" -> "" missing
"sssll": reverse "llsss" missing; prefix "ss" + "sll" -> "lls" found: [0, 2] "llssssll"
         prefixes "s", "sss" and suffixes "l", "ll" find nothing

n: number of words, k: length of the longest word
T: O(n · k²) - k prefixes and k suffixes per word, each with an O(k) palindrome check, substring and lookup
S: O(n · k) - the map, not counting the output

wordIndex = map of word to index
for each word at index:
    if reverse(word) is in wordIndex at another index: add [index, that]
    for each prefix length 1..len that is a palindrome:
        if reverse(rest after prefix) is in wordIndex at another index: add [that, index]
    for each suffix length 1..len that is a palindrome:
        if reverse(front before suffix) is in wordIndex at another index: add [index, that]
return pairs

The task asks for O(sum of word lengths). That needs a trie of the reversed words with palindrome information per
node, beyond what this section covers. O(n · k²) is what interviews usually expect.
 */

fun palindromePairsHashMap(words: Array<String>): List<List<Int>> {
    val palindromePairs = mutableListOf<List<Int>>()
    val wordIndex = mutableMapOf<String, Int>()
    words.forEachIndexed { index, word ->
        wordIndex[word] = index
    }

    words.forEachIndexed { index, word ->
        val reverse = word.reversed()
        val matched = wordIndex[reverse]
        if (matched != null && matched != index) {
            palindromePairs.add(listOf(index, matched))
        }

        // palindromic prefix word[0..prefixIndex]: the reverse of the rest goes in front
        for (prefixIndex in word.indices) {
            if (isPalindrome(word, 0, prefixIndex)) {
                val searched = word.substring(prefixIndex + 1, word.length).reversed()
                val prefixMatch = wordIndex[searched]
                if (prefixMatch != null && prefixMatch != index) {
                    palindromePairs.add(listOf(prefixMatch, index))
                }
            }
        }

        // palindromic suffix word[suffixIndex..]: the reverse of the front goes behind
        for (suffixIndex in word.length - 1 downTo 0) {
            if (isPalindrome(word, suffixIndex, word.length - 1)) {
                val searched = word.substring(0, suffixIndex).reversed()
                val suffixMatch = wordIndex[searched]
                if (suffixMatch != null && suffixMatch != index) {
                    palindromePairs.add(listOf(index, suffixMatch))
                }
            }
        }
    }

    return palindromePairs
}

// two pointers on word[from..to], without creating a substring
private fun isPalindrome(word: String, from: Int, to: Int): Boolean {
    var left = from
    var right = to
    while (left < right) {
        if (word[left] != word[right]) return false
        left++
        right--
    }
    return true
}
