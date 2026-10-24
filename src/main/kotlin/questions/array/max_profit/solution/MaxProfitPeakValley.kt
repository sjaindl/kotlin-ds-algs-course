package questions.array.max_profit.solution

/*
Buy at every valley (tomorrow's price is higher) and sell at the following peak (tomorrow's price is lower,
or it's the last day).

n: number of days
T: O(n)
S: O(1)

profit = 0
lastBuyPrice = none
for each day:
    if holding:
        if price > lastBuyPrice and (last day or price > tomorrow's price):
            sell: profit += price - lastBuyPrice
    else:
        if not last day and price < tomorrow's price:
            buy: lastBuyPrice = price
return profit
 */

fun maxProfitPeakValley(prices: IntArray): Int {
    var profit = 0
    var lastBuyPrice: Int? = null

    for (day in prices.indices) {
        val priceAtDay = prices[day]
        val isLastDay = day == prices.size - 1

        if (lastBuyPrice != null) {
            // sell?
            val isHigher = priceAtDay > lastBuyPrice
            if (isHigher && (isLastDay || priceAtDay > prices[day + 1])) {
                profit += priceAtDay - lastBuyPrice
                lastBuyPrice = null
            }
        } else {
            // buy?
            if (!isLastDay && priceAtDay < prices[day + 1]) {
                lastBuyPrice = priceAtDay
            }
        }
    }

    return profit
}
