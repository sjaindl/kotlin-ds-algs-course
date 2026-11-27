package questions.hashmap.naming_a_company.solution

/*
Only the first letter changes in a swap, so group the ideas by their first letter and keep only the rest, the
suffix, in a hash set per letter. Two ideas from groups a and b make a valid name exactly when the suffix of the a
idea is not in group b, and the suffix of the b idea is not in group a. So for every pair of groups, drop the
suffixes they share and multiply what's left. Ideas with the same first letter never work: the swap changes nothing.

["coffee", "donuts", "time", "toffee"]
c: {offee}, d: {onuts}, t: {ime, offee}
c, d: no shared suffix         -> c keeps {offee}, d keeps {onuts}       -> 1 · 1 = 1
c, t: "offee" is shared        -> c keeps {},      t keeps {ime}         -> 0 · 1 = 0
d, t: no shared suffix         -> d keeps {onuts}, t keeps {ime, offee}  -> 1 · 2 = 2
every pair also counts in the other order (d, c / t, c / t, d)           -> 2 · (1 + 0 + 2) = 6

n: number of ideas, k: length of the longest idea
T: O(26^2 · n · k) - every group is compared with the 25 others, each suffix hashed in O(k)
S: O(n · k) - the suffixes in the 26 hash sets

groups = 26 empty hash sets
for each idea: add idea without its first letter to groups[first letter]
count = 0
for each group a:
    for each group b != a:
        count += size(a - b) · size(b - a)
return count
 */

private const val ALPHABET_SIZE = 26

fun distinctNamesSuffixGroups(ideas: Array<String>): Long {
    var count = 0L
    val suffixGroups = Array(ALPHABET_SIZE) {
        HashSet<String>()
    }

    ideas.forEach { idea ->
        suffixGroups[idea[0] - 'a'].add(idea.substring(1))
    }

    suffixGroups.forEachIndexed { index, suffixes ->
        suffixGroups.forEachIndexed { otherIndex, otherSuffixes ->
            // same first letter: the swap changes nothing
            if (index == otherIndex) return@forEachIndexed

            // a shared suffix turns either swapped name into an existing idea
            val distinct = suffixes subtract otherSuffixes
            val otherDistinct = otherSuffixes subtract suffixes
            count += distinct.size.toLong() * otherDistinct.size
        }
    }

    return count
}
