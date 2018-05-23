---
title: Best Time to Buy and Sell Stock
notebook: Leetcode
tags:Algorithms
---

# Best Time to Buy and Sell Stock

```
Say you have an array for which the ith element is the price of a given stock on day i.

If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), design an algorithm to find the maximum profit.

Note that you cannot sell a stock before you buy one.

Example 1:

Input: [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
             Not 7-1 = 6, as selling price needs to be larger than buying price.
Example 2:

Input: [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.
```

 + [Thinking](#1)
 + [Implemets](#2)

## <p id=1>Thinking</p>

 + [Normal DP Solution](#1.1)
 + [Kadane's Algorithm](#1.2)

### <p id=1.1>Normal DP Solution</p>

Assume `maxPro` : most profit,`minPri` : min price.we traverse the array,each time : maxPro = max(maxPro,this.price-minPri),minPri = min(minPri,this.price).

计算每一天卖出可以获取利润：最大利润=今日价格 - 前面出现的最低价格。找出所有日子中最大利润（由于最多1次交易,利润全为负时不交易,利润为0）

 + Pseudcode

```
if(array.size()<2)
    return 0

maxPro ← 0，minPri ← array[0]

for(int i=1;i<array.size();i++):
    maxPro = max(maxPro,array[i]-minPri);
    minPri = min(minPri,array[i])

return maxPro
``` 

### <p id=1.2>Kadane's Algorithm</p>

Kadane's Algorithm is a algorithm to calculate the most summary of a continouse subarray . this problem can be converted to a most summary of a continuous subarray: we construct a new array which values is the difference of neighboring values.

Assume `max` is the result which we need,`maxCur` is the max current situation.we traverse the whole array, if `maxCur + array[cur] <0` then max = max(max,maxCur),maxCur = 0.

 + Pseudcode

```
max ← 0，maxCur ← 0,

for i ← 1 to array.size()-1:
    if(maxCur + (array[i] - array[i-1])) < 0 :
        max = max(max,maxCur);
        maxCur = 0;
    else : 
        maxCur += array[i] - array[i-1]

return max(max,maxCur)

```

## <p id=2>Implements</p>

 + [Normal DP Solution](#2.1)
 + [Kadane's Algorithm](#2.2)

### <p id=2.1>Normal DP Solution</p>

 + [Java](2.1.1)
 + [Python](2.1.2)

#### <p id=2.1.1>Java</p>

```java
package com.leetcode.datastructure;

public class BestTimetoBuyandSellStock{
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int minPrice = prices.length>0 ? prices[0]:0;

        for(int i=1,len = prices.length;i<len;i++){
            maxProfit = Math.max(maxProfit,prices[i] - minPrice);
            minPrice = Math.min(minPrice,prices[i]);
        }

        return maxProfit;   
    }
}
```

#### optization

```java
package com.leetcode.datastructure;

public class BestTimetoBuyandSellStock{
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int minPrice = Integer.MAX_VALUE;

        for(int price : prices){
            if(price<minPrice)
                minPrice = price;
            else
                maxProfit = maxProfit>(price - minPrice) ? maxProfit:(price - minPrice);
        }

        return maxProfit;   
    }
}
```

#### <p id=2.1.2>python</p>

##### python 3.5

```py
import sys

class BestTimetoBuyandSellStock:

    """
    :type prices: List[int]
    :rtype: int
    """

    def maxProfit(self, prices):
        maxProfit = 0;
        minPrice = sys.maxsize;
        for price in prices:
            if(minPrice<price):
                maxProfit = maxProfit if maxProfit>(price-minPrice) else (price-minPrice);
            else:
                minPrice = price;

        return maxProfit;

    # def test() :
    #   print("test")


```


### <p id=2.2>Kadane's Algorithm</p>

 + [Java](2.2.1)
 + [Python](2.2.2)

#### <p id=2.2.1>Java</p>

```java
package com.leetcode.datastructure;

public class BestTimetoBuyandSellStock{
    public int maxProfit(int[] prices) {
        int maxSoFar = 0;
        int maxEndThere = 0;
        int profit = 0;
        for(int i=1, len=prices.length;i<len;i++){
            profit = prices[i] - prices[i-1];
            maxEndThere = maxEndThere>0?(maxEndThere+profit):profit;
            maxSoFar = maxSoFar>maxEndThere?maxSoFar:maxEndThere;
            // maxEndThere = Math.max(maxEndThere+profit,profit);
            // maxSoFar = Math.max(maxSoFar,maxEndThere);
        }

        return maxSoFar;   
    }
}
``` 

#### <p id=2.2.2>python</p>

###### python 3

```py
import sys

class BestTimetoBuyandSellStock:

    """
    :type prices: List[int]
    :rtype: int
    """

    def maxProfit(self, prices):
        maxSofar,maxEndingThere,profit = 0,0,0;
        for i in range (1,len(prices)):
            profit = prices[i] - prices[i-1];
            maxEndingThere = (maxEndingThere + profit) if maxEndingThere>0 else profit;
            maxSofar = maxSofar if maxSofar>maxEndingThere else maxEndingThere;

        return maxSofar if maxSofar>maxEndingThere else maxEndingThere;

    # def test() :
    #   print("test")


```