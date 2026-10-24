package questions.array.max_profit.solution

/*
Every price increase from one day to the next can be captured by buying yesterday and selling today.
A long climb 1 -> 5 equals the sum of its daily steps, so summing all positive differences is optimal.

n: number of days
T: O(n)
S: O(1)

profit = 0
for each day after the first:
    if price > yesterday's price:
        profit += price - yesterday's price
return profit
 */

fun maxProfitGreedy(prices: IntArray): Int {
    var profit = 0

    for (day in 1 until prices.size) {
        if (prices[day] > prices[day - 1]) {
            profit += prices[day] - prices[day - 1]
        }
    }

    return profit
}
