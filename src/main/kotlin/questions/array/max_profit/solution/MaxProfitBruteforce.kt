package questions.array.max_profit.solution

/*
Try every possible decision on every day: ignore the day, or buy (if not holding) / sell (if it's a profit).

n: number of days
T: O(2^n)
S: O(n) - recursion depth

maxProfitFrom(day, lastBuyPrice, profit):
    if day is past the last day:
        return profit
    best = maxProfitFrom(day + 1, lastBuyPrice, profit)                               // ignore
    if holding and price at day > lastBuyPrice:
        best = max(best, maxProfitFrom(day + 1, none, profit + price - lastBuyPrice))  // sell
    else:
        best = max(best, maxProfitFrom(day + 1, price, profit))                      // buy
    return best

return maxProfitFrom(0, none, 0)
 */

fun maxProfitBruteforce(prices: IntArray): Int {
    fun maxProfitFrom(index: Int, lastBuyPrice: Int?, profit: Int): Int {
        if (index == prices.size) {
            return profit
        }

        val canSell = lastBuyPrice != null && lastBuyPrice < prices[index]

        var localBest = maxProfitFrom(index + 1, lastBuyPrice, profit) // ignore
        localBest = if (canSell) {
            // sell
            maxOf(
                localBest,
                maxProfitFrom(index = index + 1, lastBuyPrice = null, profit = profit + prices[index] - lastBuyPrice),
            )
        } else {
            // buy
            maxOf(
                localBest,
                maxProfitFrom(index = index + 1, lastBuyPrice = prices[index], profit = profit),
            )
        }

        return localBest
    }

    return maxProfitFrom(index = 0, lastBuyPrice = null, profit = 0)
}
