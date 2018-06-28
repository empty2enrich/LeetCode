---
title: Word Break II
notebook: Leetcode
tags:Algorithms
---

# Word Break II

```
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences.

Note:

The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.
Example 1:

Input:
s = "catsanddog"
wordDict = ["cat", "cats", "and", "sand", "dog"]
Output:
[
  "cats and dog",
  "cat sand dog"
]
Example 2:

Input:
s = "pineapplepenapple"
wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
Output:
[
  "pine apple pen apple",
  "pineapple pen apple",
  "pine applepen apple"
]
Explanation: Note that you are allowed to reuse a dictionary word.
Example 3:

Input:
s = "catsandog"
wordDict = ["cats", "dog", "sand", "and", "cat"]
Output:
[]
```


 + [Thinking](#1)
 + [Implements](#2)

## <p id=1>Thinking</p>

 + [BFS](#1.1)
 + [DFS](#1.2)

### <p id=1.1>BFS</p>



### <p id=1.2>DFS</p>

We construct list of string use every word in dictionary in order.when matching
 a word in dictionary we continue match first world from the dictionary from the index : cur + len(curWord),when this word completes we macth the next word.

*** but using DFS directly will lead to TLE  ***

#### Pseudcode

 + [Using DFS Directly](#1.2.1)
 + [Using DFS + DP(HashMap)](#1.2.2)

##### <p id=1.2.1>Using DFS Directly</p>

```
len = length(inputStr)
dict
resultList   

construct(curStr,curIndex):
    if(curIndex=len) : resultList.add(curStr) ; >= 处理输入为空
    for word in dict:
        if word.equals(inputStr.sub(curIndex,len(word))):
            construct(curStr+""+word,curIndex+len(word))

```

##### <p id=1.2.2>Using DFS + DP(HashMap)</p>

```
len = length(input)
HashMap res = HashMap<String,List<String>>();
firstList.add("");
res.put("",firstList);

function constructList(input,dic):
    if(res.containsKey(input)) return res.get(input);
    List curList;
    <!-- if(input.length==0) {
        curList.add("");
        return curList;
    } -->
    for(str:dic):
        if(input.startWith(str)):
            List<String> tmp = constructList(input.substring(str.length),dic);
            for(childResult:tmp):
                curList.add("".equals(childResult) ? str : str + "" + childResult) ;

    res.put(input,curResult);
    return curResult;
            


```

## <p id=2>Implements</p>

 + [DFS](#2.1)

### <p id=2.1>DFS</p>

 + [Using DFS Directly](#2.1.1)
 + [Using DFS & DP](#2.1.2)

#### <p id=2.1.1>Using DFS Directly</p>

 + [Java](#2.1.1.1)
 + [Python](#2.1.1.2)

##### <p id=2.1.1.1>Java</p>

```
package com.leetcode.dataStructure;

import java.util.ArrayList;
import java.util.List;

public class WordBreakII {

    private List<String> result;
    private String s;
    private int strLen;
    private String[] wordDict;

    public List<String> wordBreak(String s, List<String> wordDict) {
        //初始化
        this.result = new ArrayList<String>();
        this.s = s;
        this.strLen = this.s.length();
        this.wordDict = wordDict.toArray(new String[0]);
        
        //深度优先搜索
        constructFromDicAndConstructedStrBeginWithIndex("",0);

        return  result;
        
    }

    public void constructFromDicAndConstructedStrBeginWithIndex(String curConstructed,int index){
        if(index>=this.strLen) this.result.add(curConstructed);
        for(String str:this.wordDict){
            if(str.length()+index<=this.strLen && str.equals(this.s.substring(index,index+str.length()))){
                String tmp = "".equals(curConstructed) ? str:curConstructed+" "+str;
                constructFromDicAndConstructedStrBeginWithIndex(tmp,index+str.length());
            }
        }
    }
}

```

#### <p id=2.1.2>Using DFS & DP</p>

 + [Java](#2.1.2.1)
 + [Python](#2.1.2.2)

##### <p id=2.1.2.1>Java</p>

```java
private String[] wordDict;
    private HashMap<String,List<String>> map;

    {
        this.map = new HashMap<String,List<String>>();//初始化 map
        List<String> firstList = new ArrayList();
        firstList.add("");
        map.put("",firstList);
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        //初始化
        this.wordDict = wordDict.toArray(new String[0]);

        //深度优先搜索
        return constructListByDic(s);

    }

    public List<String> constructListByDic(String str){
        if(map.containsKey(str)) return map.get(str);
        List<String> constrList = new ArrayList();
        for(String wordofDic:this.wordDict){
            if(str.startsWith(wordofDic)){
                List<String> tmpList = constructListByDic(str.substring(wordofDic.length()));
                for(int i=0;i<tmpList.size();i++){
                    constrList.add("".equals( tmpList.get(i)) ? wordofDic:wordofDic + " " + tmpList.get(i));
                }
            }
        }
        this.map.put(str,constrList);
        return constrList;
    }
```

##### <p id=2.1.2.2>Python</p>

```py
class WordBreakII:

    wordDict=[]
    strMap = {'':['']}

    def test(self):
        print("test");

    def wordBreak(self, s, wordDict):
        """
        :type s: str
        :type wordDict: List[str]
        :rtype: List[str]
        """
        self.wordDict = wordDict
        return self.wordBreakWithStr(s)

    def wordBreakWithStr(self,str):
        """
        :type s: str
        :type wordDict: List[str]
        :rtype: List[str]
        """     
        if self.strMap.__contains__(str) : return self.strMap.get(str)
        consStrList = []
        for word in self.wordDict : 
            if word==str[:len(word)]:
                childList = self.wordBreakWithStr(str[len(word):])
                for childWord in childList:
                    consStrList.append(word if childWord=='' else word+' ' + childWord)

        self.strMap[str] = consStrList
        return consStrList


# 本地跑例子是正确的，但 leetcode 运行结果错误

```

Others concise code

```py
# sentences(i) returns a list of all sentences that can be built from the suffix s[i:]
def wordBreak(self, s, wordDict):
    memo = {len(s): ['']}
    def sentences(i):
        if i not in memo:
            memo[i] = [s[i:j] + (tail and ' ' + tail)
                       for j in range(i+1, len(s)+1)
                       if s[i:j] in wordDict
                       for tail in sentences(j)]
        return memo[i]
    return sentences(0)
```