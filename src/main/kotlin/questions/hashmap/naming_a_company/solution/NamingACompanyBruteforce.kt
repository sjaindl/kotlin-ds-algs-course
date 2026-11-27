package questions.hashmap.naming_a_company.solution

/*
Try every pair of ideas: swap their first letters and look both new names up in a hash set of all ideas. If neither
exists, the pair is valid in both orders, ideaA ideaB and ideaB ideaA, so it counts twice.

["coffee", "donuts", "time", "toffee"]
coffee, donuts -> doffee, conuts: both new  -> +2
coffee, time   -> toffee, cime:   toffee exists
donuts, time   -> tonuts, dime:   both new  -> +2
donuts, toffee -> tonuts, doffee: both new  -> +2
time, toffee   -> time, toffee:   both exist (same first letter)
…                                              = 6

n: number of ideas, k: length of the longest idea
T: O(n² · k) - n² / 2 pairs, each swapped and looked up in O(k)
S: O(n * k) - the hash set holds references to the ideas, plus O(k) for the two swapped copies

ideaSet = hash set of ideas
count = 0
for i in 0 until n:
    for j in i + 1 until n:
        swap the first letters of ideas[i] and ideas[j]
        if neither new name is in ideaSet: count += 2
return count
 */

fun distinctNamesBruteforce(ideas: Array<String>): Long {
    val ideaSet = ideas.toHashSet()
    var count = 0L

    for (first in ideas.indices) {
        for (second in first + 1 until ideas.size) {
            val ideaA = ideas[first].toCharArray()
            val ideaB = ideas[second].toCharArray()

            val temp = ideaA[0]
            ideaA[0] = ideaB[0]
            ideaB[0] = temp

            // valid in both orders: "ideaA ideaB" and "ideaB ideaA"
            if (String(ideaA) !in ideaSet && String(ideaB) !in ideaSet) {
                count += 2
            }
        }
    }

    return count
}
