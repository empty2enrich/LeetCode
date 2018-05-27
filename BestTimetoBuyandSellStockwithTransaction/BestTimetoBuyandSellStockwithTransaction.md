---
title: Best Time to Buy and Sell Stock with Transaction
notebook: Leetcode
tags:Algorithms
---

# Best Time to Buy and Sell Stock with Transaction 

```
Your are given an array of integers prices, for which the i-th element is the price of a given stock on day i; and a non-negative integer fee representing a transaction fee.

You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction. You may not buy more than 1 share of a stock at a time (ie. you must sell the stock share before you buy again.)

Return the maximum profit you can make.

Example 1:
Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
Output: 8
Explanation: The maximum profit can be achieved by:
Buying at prices[0] = 1
Selling at prices[3] = 8
Buying at prices[4] = 4
Selling at prices[5] = 9
The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
Note:

0 < prices.length <= 50000.
0 < prices[i] < 50000.
0 <= fee < 50000.

```


 + [Thinking](#1)
 + [Implements](#2)

## <p id=1>Thinking</p>

 + [DP Solution](#1.1)
 + [Greedy](#1.2)

### <p id=1.1>DP Solution</p>

We calculate the most profit of sell or buy stack in every day,`buyMost`、`sellMost`. we assume the initial money is zero. `buyMost` : yesterday's most money which last transaction is buy stack,`sellMost`:yesterday most money which last transaction is sell stack. when we get i'th day,we can calculate the (i+1)'th days:

#### Pseudcode

```
... in i'th day 

buyCurMost = max(sellMost - prices[i],buyMost)
sellCurMost = max(sellMost,buyMost + prices[i] - fee)

buyMost = buyCurMost
sellMost = sellCurMost
```

### <p id=1.2>Greedy</p>

```
利用峰值：

+ 先get oldPeak, oldTrough
+ 找到新的 newTrough
    + newTrough <= oldTrough : getFit(), then oldTrough=newTrough,search peak
    + newTrough > oldTrough :
        + newTrough <= oldPeak - fee : getFit() 
                + getFit()>=0,then oldTrough=newTrough,search newPeak
                + getFit()<0 , then search newPeak
        + newTrough > oldPeak - fee : search newPeak
            + if newPeak>oldPeak : oldPeak = newPeak ,then search newTrough
            + newPeak<oldPeak : search newThrough

转换为贪心算法（greedy）：

完整买入卖出(transaction) 在下面情况下发生:
 + `curPrice` < `lowest` : `curPrice`:当前 price，`lowest` : after previous transaction,the lowest price
 + `lowest` < `curPrice` < `highest` - `fee` : `highest` : after previous transaction the highest price,`fee` : transaction with fee

```

#### Pseudcode

```
profit ← 0;
lowest,highest ← price[0]

for curPrice ← 1 to prices.length :
        if(curPrice<=lowest || curPrice<= highest-fee):
                transaction();
                lowest=highest=curPrice;
        else
                highest = max(highest,curPrice)

function transaction() :
        profit += (highest-lowest-fee)>0 ? (highest-lowest-fee) : 0;
```

## <p id=2>Implememts</p>

 + [DP Solution](#2.1)
 + [Greedy](#2.2)

### <p id=2.1>DP Solution</p>

 + [Java](#2.1.1)
 + [Python](#2.1.2)

#### <p id=2.1.1>Java</p>

```java
package com.leetcode.datastructure;

public class BestTimetoBuyandSellStockwithTransaction{
    public int maxProfit(int[] prices, int fee) {
        if(prices.length<2)
            return 0;

        int sellMost = 0,buyMost = -prices[0],buyCurMost = 0,sellCurMost = 0; 
        for(int i=1,len=prices.length;i<len;i++){
            buyCurMost = this.max(buyMost,sellMost - prices[i]);
            sellCurMost = this.max(sellMost,buyMost + prices[i] - fee);
            buyMost = buyCurMost;
            sellMost = sellCurMost;
        }

        return this.max(sellMost,0);
    }

    public int max(int vOne,int vTwo){
        return vOne>vTwo?vOne:vTwo;
    }
}
``` 

#### <p id=2.1.2>Python</p>

 + Python 3.5 (292ms)

```py
class BestTimetoBuyandSellStockwithTransaction :
    def maxProfit(self, prices, fee):
        """
        :type prices: List[int]
        :type fee: int
        :rtype: int
        """
        if len(prices)<2:
            return 0;

        sellMost = 0;
        buyMost = -prices[0];
        for i in range(1,len(prices)):
            curSellMost = max(sellMost,buyMost+prices[i]-fee);
            curBuyMost = max(buyMost,sellMost-prices[i]);
            sellMost,buyMost = curSellMost,curBuyMost;

        return max(sellMost,buyMost);


    def max(self,vOne,vTwo):
        return vOne if (vOne>vTwo) else vTwo;

```

### <p id=2.2>Greedy</p>

 + [Java](#2.2.1)
 + [python](#2.2.2)

#### <p id=2.2.1>Java</p>

```java
package com.leetcode.datastructure;

public class BestTimetoBuyandSellStockwithTransaction{

//  public int curPrice;
    public int lowest;
    public int highest;
    public int profit;

    public int maxProfit(int[] prices, int fee) {
        if(prices.length<2)
            return 0;
        profit = 0;
        lowest = highest = prices[0];
        for(int i=1,len=prices.length;i<len;i++){

        }
        for(int curPrice : prices){
            if(curPrice<=lowest || curPrice<=highest - fee){
                transaction(fee);
                lowest = highest = curPrice;
            }else{
                highest = max(highest,curPrice);
            }
        }

        transaction(fee);

        return this.profit;
        
    }

    public int max(int vOne,int vTwo){
        return vOne>vTwo?vOne:vTwo;
    }

    public void transaction(int fee){
        this.profit += (this.highest - this.lowest - fee > 0) ? (this.highest - this.lowest - fee) : 0;
    }
}



<!--  改1 -->
package com.leetcode.datastructure;

public class BestTimetoBuyandSellStockwithTransaction{
//  public int maxProfit(int[] prices, int fee) {
        public int lowest;
        public int highest;
        public int profit;

        public int maxProfit(int[] prices, int fee) {
            if(prices.length<2)
                return 0;
            profit = 0;
            lowest = highest = prices[0];
            int curPrice = 0;
            for(int i=1,len=prices.length;i<len;i++){
                curPrice = prices[i];
                if(curPrice<=lowest || curPrice<=highest - fee){
                    transaction(fee);
                    lowest = highest = curPrice;
                }else{
                    highest = max(highest,curPrice);
                    if(i == len-1) transaction(fee);
                }   
            }
            // for(int curPrice : prices){
            //  if(curPrice<=lowest || curPrice<=highest - fee){
            //      transaction(fee);
            //      lowest = highest = curPrice;
            //  }else{
            //      highest = max(highest,curPrice);
            //  }
            // }

            // transaction(fee);

            return this.profit;
            
        }

        public int max(int vOne,int vTwo){
            return vOne>vTwo?vOne:vTwo;
        }

        public void transaction(int fee){
            this.profit += (this.highest - this.lowest - fee > 0) ? (this.highest - this.lowest - fee) : 0;
        }
}

```

##### optimizate

```java
   public int maxProfit(int[] p, int fee) {
        int profit = 0;
        Integer lo = null, hi = null, n = p.length;
        for (int i = 0; i < n; i++) {
            if (lo != null && hi == null && p[i] - lo > fee) hi = p[i]; // buy in
            if (hi != null && p[i] > hi) hi = p[i]; // update highest
            if (hi != null && (hi - p[i] > fee || i == n - 1)) { // sale out
                profit += hi - lo - fee;
                hi = null;
                lo = null;
            }

            lo = lo != null ? Math.min(lo, p[i]) : p[i]; // update lowest
        }
        return profit;      
    }

```

#### <p id=2.2.2>Python</p>

 + 3.5(220ms)

```py
class BestTimetoBuyandSellStockwithTransaction :
    def maxProfit(self, prices, fee):
        """
        :type prices: List[int]
        :type fee: int
        :rtype: int
        """
        if len(prices)<2:
            return 0;
        
        profit = 0;
        lowest = highest = prices[0];
        for i in range(1,len(prices)):
            curPrice = prices[i];
            if (curPrice<=lowest) or (curPrice<highest - fee):
                profit += (highest - lowest - fee ) if (highest - lowest - fee > 0) else 0;
                lowest = highest = curPrice;
            else :
                highest = max(highest,curPrice);
                if(i==len(prices)-1) : profit += (highest - lowest - fee ) if (highest - lowest - fee > 0) else 0;

        return profit;


    def max(self,vOne,vTwo):
        return vOne if (vOne>vTwo) else vTwo;


```
