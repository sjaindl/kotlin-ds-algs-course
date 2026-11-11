package toolbox.string

/*
Set of letters as bit mask
Store which of the 26 letters occur as the bits of one Int: bit i is set when letter 'a' + i occurs. Building it is
one or per character, and comparing two sets is a single == instead of a loop over 26 letters.
The stdlib counts the set bits (= distinct letters) with mask.countOneBits().

When to reach for it: "same set of characters", "no common letters" (mask1 and mask2 == 0), distinct letters of
many words that are compared often.

Seen in:
    questions/string/close_strings/solution/CloseStringsBitMask.kt

Kotlin: there are no <<, | and & operators, use the infix functions shl, or and and: mask or (1 shl i).
26 bits fit into an Int, the highest one is 1 shl 25 for 'z'.

"cabbba" -> a, b, c -> 0b111
"abbccc" -> a, b, c -> 0b111 -> same set of letters

n: length of the string
T: O(n) to build, O(1) to compare
S: O(1)
 */

// bit i is set if letter 'a' + i occurs: letterMask("cab") = 0b111, letterMask("d") = 0b1000
fun letterMask(s: String): Int {
    var mask = 0
    for (char in s) {
        mask = mask or (1 shl (char - 'a'))
    }
    return mask
}

fun hasLetter(mask: Int, char: Char): Boolean = (mask and (1 shl (char - 'a'))) != 0
