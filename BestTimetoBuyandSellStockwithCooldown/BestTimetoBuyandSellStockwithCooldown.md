---
title: Best Time to Buy and Sell Stock with Cooldown
notebook: Leetcode
tags:Algorithms
---

# Best Time to Buy and Sell Stock with Cooldown

```
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:

You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
Example:

Input: [1,2,3,0,2]
Output: 3 
Explanation: transactions = [buy, sell, cooldown, buy, sell]
```

 + [Thinking](#1) 
 + [Implements](#2) 

## <p id=1>Thinking</p>

 + [DP Solution](#1.1) 
 + [Greedy](#1.2) 
 + [A DP Solution which only record 0 operation(sell,buy)](#1.3) 

### <p id=1.1>DP Solution</p>

对每一天计算执行不同操作的最大值 : buy,sell,cooldown

### <p id=1.2>Greedy  (部分情况未考虑，当前方法有问题)</p>

#### 利用峰值

```
先找到第一个 trough(谷)、peak(峰) （当存在连续相同 price 时在边界处不好判断是否为峰值/谷值）

 + 找到第二个 through:
    + index(newTrough) == prices.length - 1 : 交易：oldTrough,oldPeak
    + index(newTrough) < prices.length - 1:
       + if index(newTrough) - index(oldPeak) > 1 :  第一次交易买入卖出为:oldTrough,oldPeak ,oldTrough = newTrough
       + if index(newTrough) - index(oldPeak) = 1 :
        + index(oldPeak) - index(oldTrough) = 0 : oldTrough = newTrough
        + index(oldPeak) - index(oldTrough) = 1 :
            + prices[index(oldPeak)] - prices[index(oldPeak) - 1] >= prices[index(newTrough) + 1] - prices[index(newTrough)] : 交易：oldTrough,oldPeak ,oldTrough = prices[index(newTrough)+1]
            + prices[index(oldPeak)] - prices[index(oldPeak) - 1] < prices[index(newTrough) + 1] - prices[index(newTrough)] : oldTrough = newTrough 
        + index(oldPeak) - index(oldTrough) > 1 :

            + prices[index(oldPeak)] - prices[index(oldPeak) - 1] >= prices[index(newTrough) + 1] - prices[index(newTrough)] : 交易：oldTrough,oldPeak ,oldTrough = prices[index(newTrough)+1]

            + prices[index(oldPeak)] - prices[index(oldPeak) - 1] < prices[index(newTrough) + 1] - prices[index(newTrough)] : 交易:oldTrough,prices[index(oldPeak)-1], oldTrough=newTrough
```

#### 利用最大最小值

```
when `curPrice` < `highest`,judge a transaction:
    + indexLowest = indexHighest : lowest = highest = curPrice
    + indexLowest < indexHighest : 
        + prices[indexHighest] - prices[indexHighest - 1] >= prices[curIndex+1] - prices[curIndex] : transaction(lowest,highest),then lowest=highest=prices[curIndex+1]
        + prices[indexHighest] - prices[indexHighest - 1] >= prices[curIndex+1] - prices[curIndex] : transaction(lowest,prices[indexHighest - 1]),then lowest=highest=curPrice
```

##### Pseudcode

```
profit ← 0;
len ← prices.length
lowest = highest = prices[0];
indexLowest = indexHighest = 0;
for i ← 1 to len-1 :
    if(prices[i] < prices[i-1]) :
        highest ← prices[i-1];
        indexHighest ← i-1;
        case 1 : indexHighest=indexLowest : 
            lowest=highest=prices[i];
            indexLowest=indexHighest=i;

        case 2 : indexHighest>indexLowest

            case 2.1 : i = len-1
                profit += highest-lowest;

            case 2.2 : prices[indexHighest] - prices[indexHighest - 1] > price[i+1] - prices[i]

                case 2.2.1 : highest<prices[curIndex+1]:
                    highest = prices[curIndex+1];
                    indexHighest = curIndex+1;
                    curIndex++;

                case 2.2.2 : highest>=prices[curIndex+1] :
                    profit += prices[indexHighest] - prices[indexLowest];
                    lowest = highest = prices[curIndex+1];
                    indexLowest = indexHighest = curIndex+1;

            case 2.3 : prices[indexHighest] - prices[indexHighest - 1] <= price[i+1] - prices[i]

                case 2.3.1 : prices[indexHighest] = prices[curIndex+1] :
                    profit += prices[indexHighest] - prices[indexLowest];
                    lowest = highest = prices[curIndex+1];
                    indexLowest = indexHighest = curIndex+1;

                case 2.3.2 : prices[indexHighest-1] < prices[curIndex] :
                    highest = prices[curIndex+1];
                    indexHighest = curIndex+1;
                    curIndex++;

                case 2.3.3 : prices[indexHighest-1] > prices[curIndex] :
                    profit += prices[indexHighest - 1] - prices[indexLowest];
                    lowest = highest = prices[curIndex];
                    indexLowest = indexHighest = curIndex;

                

    else if(i=len-1):
        profit += max(prices[i],highest) - lowest; 

return profit
```

### <p id=1.3>A DP Solution which only record 0 operation</p>

```
The series of problems are typical dp. The key for dp is to find the variables to represent the states and deduce the transition function.

Of course one may come up with a O(1) space solution directly, but I think it is better to be generous when you think and be greedy when you implement.

The natural states for this problem is the 3 possible transactions : buy, sell, rest. Here rest means no transaction on that day (aka cooldown).

Then the transaction sequences can end with any of these three states.

For each of them we make an array, buy[n], sell[n] and rest[n].

buy[i] means before day i what is the maxProfit for any sequence end with buy.

sell[i] means before day i what is the maxProfit for any sequence end with sell.

rest[i] means before day i what is the maxProfit for any sequence end with rest.

Then we want to deduce the transition functions for buy sell and rest. By definition we have:

buy[i]  = max(rest[i-1]-price, buy[i-1]) 
sell[i] = max(buy[i-1]+price, sell[i-1])
rest[i] = max(sell[i-1], buy[i-1], rest[i-1])
Where price is the price of day i. All of these are very straightforward. They simply represents :

(1) We have to `rest` before we `buy` and 
(2) we have to `buy` before we `sell`
One tricky point is how do you make sure you sell before you buy, since from the equations it seems that [buy, rest, buy] is entirely possible.

Well, the answer lies within the fact that buy[i] <= rest[i] which means rest[i] = max(sell[i-1], rest[i-1]). That made sure [buy, rest, buy] is never occurred.

A further observation is that and rest[i] <= sell[i] is also true therefore

rest[i] = sell[i-1]
Substitute this in to buy[i] we now have 2 functions instead of 3:

buy[i] = max(sell[i-2]-price, buy[i-1])
sell[i] = max(buy[i-1]+price, sell[i-1])
This is better than 3, but

we can do even better

Since states of day i relies only on i-1 and i-2 we can reduce the O(n) space to O(1). And here we are at our final solution:

Java

public int maxProfit(int[] prices) {
    int sell = 0, prev_sell = 0, buy = Integer.MIN_VALUE, prev_buy;
    for (int price : prices) {
        prev_buy = buy;
        buy = Math.max(prev_sell - price, prev_buy);
        prev_sell = sell;
        sell = Math.max(prev_buy + price, prev_sell);
    }
    return sell;
}
```

## <p id=2>Implements</p>

 + [DP Solution](#2.1) 
 + [Greedy](#2.2) 
 + [DP Solution which only use two operation(sell,buy)](#2.3) 

### <p id=2.1>DP Solution</p>

 + [DP Solution](#2.1.1) 
 + [Python](#2.1.2) 

#### <p id=2.1.1>Java</p>

#### <p id=2.1.2>Python</p> 

### <p id=2.2>Greedy（代码有些情况没考虑全,暂时保存下）</p>

 + [Java](#2.2.1) 
 + [Python](#2.2.2) 

#### <p id=2.2.1>Java</p>

```java
package com.leetcode.datastructure;

public class BestTimetoBuyandSellStockwithCooldown{

    public int maxProfit(int[] prices) {
        int len = prices.length;

        if(len < 2) return 0;

        int lowest=prices[0],highest = prices[0];
        int indexLowest=0,indexHighest=0,profit = 0;
        for(int curIndex=1;curIndex<len;curIndex++){
            if (prices[curIndex] - prices[curIndex - 1] <= 0){
                highest = prices[curIndex - 1];
                indexHighest = curIndex-1;
                if(indexHighest == indexLowest){
                    lowest = highest = prices[curIndex];
                    indexLowest = indexHighest = curIndex;
                }else{
                    if(curIndex == len-1) profit += highest-lowest;
                    else if(prices[indexHighest] - prices[indexHighest-1] > prices[curIndex+1] -prices[curIndex] ){
                        if(highest >= prices[curIndex]){
                            profit +=highest - lowest;
                            lowest = highest = prices[curIndex+1];
                            indexLowest = indexHighest = curIndex+1;
                            curIndex ++;
                        }else{
                            highest = prices[curIndex+1];
                            indexHighest = curIndex+1;
                            curIndex++;
                        }
                        
                    }else if(prices[indexHighest] - prices[indexHighest-1] <= prices[curIndex+1] -prices[curIndex] ){
                        if(prices[indexHighest] == prices[curIndex+1]){
                            profit +=highest - lowest;
                            lowest = highest = prices[curIndex+1];
                            indexLowest = indexHighest = curIndex+1;
                            curIndex ++;
                        }else if(prices[indexHighest-1] > prices[curIndex]){
                            profit += prices[indexHighest -1] - lowest;
                            lowest = highest = prices[curIndex];
                            indexLowest = indexHighest = curIndex; 
                        }else if (prices[indexHighest-1] < prices[curIndex]){
                            highest = prices[curIndex];
                            indexHighest = curIndex;
                            // curIndex++;
                        }
                        
                    }
                    // else{
                    //  if(highest>prices[curIndex+1]){
                    //      profit +=highest - lowest;
                    //      lowest = highest = prices[curIndex+1];
                    //      indexLowest = indexHighest = curIndex+1;
                    //  }else{
                    //      highest = prices[curIndex+1];
                    //      indexHighest = curIndex+1;
                    //      curIndex++;
                    //      // profit += prices[curIndex+1];
                    //      // indexHighest = curIndex;
                    //  }
                    // }
                }
            }
            else if(curIndex==len-1) profit += prices[curIndex] - lowest;
        }

        return profit;
    }

    public int max(int vOne,int vTwo){
        return vOne>vTwo?vOne:vTwo;
    }

    // public int[] prices;
    // public int maxProfit(int[] prices) {
    //  int len = prices.length;
    //  if(len<2) return 0;

    //  this.prices = prices;
    //  int profit = 0;
    //  int trough = prices[0];
    //  int indexTrough = 0;
    //  int peak = null;
    //  int indexPeak = null;

    //  for (int i=1; i<len; i++) {
    //      //谷值
    //      if(prices[i] - prices[i-1]<=0 && prices[i] - prices[i+1]<0){

    //      }
    //      //峰值
    //      else if((prices[i] - prices[i-1]>=0 && prices[i] - prices[i+1]>0)){

    //      }
    //      //单调
    //      else if(i = len-1){
    //          profit += (prices[len-1] - trough>0) ? (prices[len-1] - trough) : 0;
    //      }

    //  }
    // }

    // //是否为谷值 : (prices[i] <= prices[i-1] or i=0) && (prices[i]-prices[i+1]<0 or i=lengh-1)
    // public boolean isTrough(int i){

    // }

    // //是否为峰值
    // public boolean isPeak(int i){

    // }
}
```

#### <p id=2.2.2>Python</p>

### <p id=2.3>DP Solution( only use 2 operation)</p>

 + [Java](#2.3.1) 
 + [Python](#2.3.2) 

#### <p id=2.3.1>Java</p>

```
package com.leetcode.datastructure;

public class BestTimetoBuyandSellStockwithCooldown{

    public int maxProfit(int[] prices) {
        int buy=Integer.MIN_VALUE,sell=0,preBuy=Integer.MIN_VALUE,preSell=0;
        for(int curPrice : prices){
            preBuy = buy;
            buy = max(preSell - curPrice,preBuy);
            preSell = sell;
            sell = max(preSell,preBuy+curPrice);
        }
        return max(buy,sell);
    }

    public int max(int vOne,int vTwo){
        return vOne>vTwo?vOne:vTwo;
    }
}
```

#### <p id=2.3.2>Python</p>

##### Python 3.5

```
class BestTimetoBuyandSellStockwithColldown :
    def maxProfit(self, prices):
        """
        :type prices: List[int]
        :rtype: int
        """
        length = len(prices);
        if(length<2):
            return 0 ;

        sell,preSell,buy,preBuy=max(0,prices[1]-prices[0]),0,max(-prices[0],-prices[1]),-prices[0];
        for i in range(2,length):
            preBuy = buy;
            print("preBuy:" , preBuy);
            buy = max(preBuy,preSell - prices[i]);
            print("buy:" , buy);
            preSell = sell;
            print("preSell:" , preSell);
            sell = max(preSell,preBuy + prices[i]);
            print("sell:" , sell);

        return max(buy,sell);

    def max(self,vOne,vTwo):
        return vOne if vOne>vTwo else vTwo;
```