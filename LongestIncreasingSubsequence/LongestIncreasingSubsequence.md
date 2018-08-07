# Longest Increasing Subsequence
@(Leetcode)

```
Given an unsorted array of integers, find the length of longest increasing subsequence.

Example:

Input: [10,9,2,5,3,7,101,18]
Output: 4 
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
Note:

There may be more than one LIS combination, it is only necessary for you to return the length.
Your algorithm should run in O(n2) complexity.
```

+ [Thinking](#1)
+ [Implements](#2)

## <p id=1>Thinking</p>

+ [DP Solution One : calculate a array, array.index store the LIS max Length of end with input.index  ](#1.1)
+ [DP Solution two : maintain a tailArray, tailArray.index store the min tail value of LIS with lengh () ](#1.2)


### <p id=1.1>P Solution One : calculate a array, array.index store the LIS max Length of end with input.index</p>

***Difficult Point:***  如何快速找到离当前点最近、并且 value 小于当前点的点(未解决)。

### <p id=1.2>DP Solution two : maintain a tailArray, tailArray.index store the min tail value of LIS with lengh () -1</p>

+ we assume len is the length of LIS that we have caculated,
+ for index is 0 to (inputArray.size - 1)
 + calculate n is that : tailArray[n-1] < inputArray[index] < tailArray[n]
 + update tailArray[n] = inputArray[index] 
 + if n=len, len++ 

## <p id=2>Implements</p>

+ [DP Solution One : calculate a array, array.index store the LIS max Length of end with input.index  ](#2.1)
+ [DP Solution two : maintain a tailArray, tailArray.index store the min tail value of LIS with lengh () ](#2.2)

### <p id=2.1>P Solution One : calculate a array, array.index store the LIS max Length of end with input.index</p>

### <p id=2.2>DP Solution two : maintain a tailArray, tailArray.index store the min tail value of LIS with lengh ()</p>

+ [Java](#2.2.1)
+ [Python](#2.2.2)

#### <p id=2.2.1>Java</p>

```java
// Leetcode run time 0ms
package com.leetcode.dataStructure;

public class LongestIncreasingSubsequence {
    private int[] minTailValueOfLISWithLengthIsIndexPlusOne;
    public int lengthOfLIS(int[] nums) {
        this.minTailValueOfLISWithLengthIsIndexPlusOne = new int[nums.length];
        int len = 0;
        for(int i=0,arrLen=nums.length;i<arrLen;i++){
            if (len==0){
                minTailValueOfLISWithLengthIsIndexPlusOne[len++] = nums[i];
//                len++;
            }else if(nums[i]>minTailValueOfLISWithLengthIsIndexPlusOne[len-1]){
                minTailValueOfLISWithLengthIsIndexPlusOne[len++] = nums[i];
//                len++;
            }else{
                int index = binarySearchIndexNeedUpdateInArray(nums[i],0,len-1);
                minTailValueOfLISWithLengthIsIndexPlusOne[index] = nums[i];
            }

        }

        return len;

    }

    /**
     * @param input 待查询值
     * @param begin 待查询数组起始 index
     * @param end 待查询数组结束  index
     * @return index : nums[index-1]<=input<nums[index
     */
    public int binarySearchIndexNeedUpdateInArray(int input,int  begin ,int end){
        if (begin+1<end){
            int mid = (begin+end)/2;
            if(input == this.minTailValueOfLISWithLengthIsIndexPlusOne[mid]) return mid;
            else if(input>this.minTailValueOfLISWithLengthIsIndexPlusOne[mid]) return binarySearchIndexNeedUpdateInArray(input,mid,end);
            else return binarySearchIndexNeedUpdateInArray(input,begin,mid);
        }
        return input>this.minTailValueOfLISWithLengthIsIndexPlusOne[begin] ? end : begin;
    }
}

  //这个查询与上面方法功能一样
  public int binarySearch(int input,int begin,int end){
        int mid = (begin+end)/2;
        if(begin<end){
            if (input>minTailValueOfLISWithLengthIsIndexPlusOne[mid]) return binarySearch(input,mid+1,end);
            else return binarySearch(input,begin,mid);
        }
        return begin;
    }
```

more concise code:

```java
public int lengthOfLIS(int[] nums) {
    int[] tails = new int[nums.length];
    int size = 0;
    for (int x : nums) {
        int i = 0, j = size;
        while (i != j) {
            int m = (i + j) / 2;
            if (tails[m] < x)
                i = m + 1;
            else
                j = m;
        }
        tails[i] = x;
        if (i == size) ++size;
    }
    return size;
}
```