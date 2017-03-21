
package common;

import java.util.ArrayList;
import java.util.List; 

public class BeginEndWord{
	String beginWord;
	String endWord;
	List<String> wordList;// = new ArrayList<String>();
	List<Integer> notUse;
	List<Integer> used;
	List<Integer> tempUse;
	List<Integer> tempNotUse;
	// int[] used;
	List<List<Integer>> linkArr;
	List<Integer> beginIndex;
	List<List<String>> result;
	List<String> tempResult;
	int shortestLength = 2;
	int deepth = 0;
	boolean con = true;
	// int beginIndex = -1;
	public BeginEndWord(){};
	public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // int[] isUsed = new int[wordList.length];
        this.wordList = wordList;
        this.beginWord = beginWord;
        this.endWord = endWord;
        this.used = new ArrayList<Integer>();
        this.notUse = new ArrayList<Integer>();
        this.linkArr = new ArrayList<List<Integer>>(wordList.size()+1);
       	//初始化linkarr
       	this.wordList.add(this.beginWord);
       	this.wordList.add(this.endWord);
       	this.used.add(wordList.size()-1);
       	for(int i=0;i<wordList.size();i++){
       		if(i<wordList.size()-2)
       			this.notUse.add(i);
       		linkArr.add(new ArrayList<Integer>());
       	}
       	if(isCanTransformByOneTime(this.beginWord,this.endWord)){
       		this.tempResult.add(this.beginWord);
       		this.tempResult.add(this.endWord);
       		this.result.add(this.tempResult);
       		return this.result;
       	}else{
       		//找到begin to end 的路劲
	       	this.find();
	       	//获取结果
	       	this.getResult();
       	}
       	return this.result;
       	
    }

    public void getResult(){
    	this.deepth = 0;
    	tempResult = new ArrayList<String>();
    	for(int i=0;i<shortestLength;i++){
    		tempResult.add("");
    	}
    	this.getNextIndex(wordList.size()-2);
    }

    public void getNextIndex(int i){
    	for(int j=0;j<linkArr.get(i).size();j++){
    		this.tempResult.set(deepth++,wordList.get(linkArr.get(i).get(j)));
    		if(linkArr.get(i).get(j)!=wordList.size()-1)
    			getNextIndex(linkArr.get(i).get(j));
    		else{
    			add();
    			deepth--;
    		}
    	}
    	deepth--;
    }

    public void add(){
    	List<String> tem = new ArrayList<String>();
    	for(int i=0;i<this.tempResult.size();i++){
    		tem.add(this.tempResult.get(i));
    	}
    	this.result.add(tem);
    }

    public void find(){
    	this.tempUse = new ArrayList<Integer>();
    	this.tempNotUse = new ArrayList<Integer>();
    	for(int i=0;i<this.notUse.size();i++){
    		if(findPre(i))
    			this.tempUse.add(this.notUse.get(i));
    		else
    			this.tempNotUse.add(this.notUse.get(i));
    	}
    	this.used = this.tempUse;
    	this.notUse = this.tempNotUse;
    	//判断能否到底起始单词
    	for(int i=0;i<this.used.size();i++){
    		if(isCanTransformByOneTime(beginWord,wordList.get(this.used.get(i)))){
    			con = false;
    			linkArr.get(wordList.size()-2).add(this.used.get(i));
    		}	
    	}
    	if(con&&this.notUse.size()>0&&this.used.size()>0){
    		shortestLength++;
    		this.find();
    	}
    }
    //是否能经过一次变换转换为上次使用单词
    public boolean findPre(int i){
    	boolean hasused = false;
		for(int j=0;j<this.used.size();j++){
    		if(isCanTransformByOneTime(wordList.get(this.notUse.get(i)),wordList.get(this.used.get(j)))){
    			hasused = true;
    			linkArr.get(this.notUse.get(i)).add(this.used.get(j));
    		}
    	}
    	return hasused;
    }

    //两个单词能否经过一次变换得到
    public boolean isCanTransformByOneTime(String targetStr ,String sourceStr){
    	int sameNum = 0;
    	for(int i=0;i<sourceStr.length();i++){
    		if(targetStr.charAt(i)==sourceStr.charAt(i))
    			sameNum++;
    	}
    	if(sameNum == sourceStr.length()-1)
    		return true;
    	else
    		return false;
    }
}
