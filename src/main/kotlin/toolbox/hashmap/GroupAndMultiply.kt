package toolbox.hashmap

/*
Group and multiply
To count pairs, don't list them. Group the items by what decides whether two of them fit, then for two groups that
fit, every item of one pairs with every item of the other: size(a) · size(b) pairs in O(1). With a set per group,
subtract drops the items both groups share, if those can't pair.

When to reach for it: "count the pairs (i, j) such that ...", when n² pairs are too many but the number of groups
is small (26 letters, a few remainders, a few lengths).

Seen in:
    questions/hashmap/naming_a_company/solution/NamingACompanySuffixGroups.kt     suffixes by first letter
    questions/hashmap/naming_a_company/solution/NamingACompanyBruteforce.kt       every pair, O(n² · k)

Kotlin: the sum of the products overflows an Int quickly: count in a Long. subtract returns a new
Set, it doesn't change the receiver.

["coffee", "donuts", "time", "toffee"] by first letter -> c: {offee}, d: {onuts}, t: {ime, offee}
crossPairs(t, c) = {ime} · {} = 0, crossPairs(t, d) = {ime, offee} · {onuts} = 2

n: number of items, g: number of groups
T: O(n) to group, O(size(a) + size(b)) per pair of groups, O(g · n) for all of them
S: O(n)
 */

// the values of the items grouped by key, every group a set: groupSets(ideas, { it[0] }, { it.drop(1) })
fun <T, K, V> groupSets(items: List<T>, key: (T) -> K, value: (T) -> V): Map<K, Set<V>> {
    val groups = HashMap<K, MutableSet<V>>()
    for (item in items) {
        groups.getOrPut(key(item)) {
            HashSet()
        }.add(value(item))
    }
    return groups
}

// pairs of one item from a and one from b, without the items that are in both
fun <T> crossPairs(a: Set<T>, b: Set<T>): Long = (a subtract b).size.toLong() * (b subtract a).size
