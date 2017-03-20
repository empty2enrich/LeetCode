

package common;
public class BeginEndWord{
	String beginWord;
	String endWord;
	List<String> wordList;
	List<Integer> notUse;
	List<Integer> used;
	List<Integer> tempUse;
	List<Integer> tempNotUse;
	// int[] used;
	List<list<Integer>> linkArr;
	List<List<String>> result;
	int shortestLength = 0;
	boolean continue = true;
	int beginIndex = -1;

	public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // int[] isUsed = new int[wordList.length];
        this.wordList = wordList;
        this.used = new ArrayList<Integer>();
        this.notUse = new ArrayList<Integer>();
       	//初始化linkarr
       	this.linkArr = new ArrayList<List<Integer>>(wordList.length()+1);
       	for(int i=0;i<wordList.length()+1;i++){
       		linkArr.add(new ArrayList<Integer>());
       	}

       	for(int i=0;i<wordList.length();i++){
       		if(isCanTransformByOneTime(wordList[i],endWord)){
       			this.used.add(i);
       			linkArr.get(i).add(-1);
       		}
       		else
       			this.notUse.add(i);
       	}
       	this.find();
       	this.getResult();


    }

    public void getResult(){
    	this.result = new ArrayList<ArrayList<String>>();
    	int[] index = new int[];
    	if(linkArr.get(wordList.size()).size()>0){

    	}
    }

    public void find(){
    	this.tempUse = new ArrayList<Integer>();
    	this.tempNotUse = new ArrayList<Integer>();
    	for(int i=0;i<this.notUse.size();i++){
    		if(findPre(i))
    			this.tempUse.add(this.notUse.get(i));
    		else
    			this.tempNotUse.add(this.tempNotUse.get(i));
    	}
    	this.used = this.tempUse;
    	this.notUse = this.tempNotUse;

    	if(continue&&this.notUse.size>0&&this.used.size()>0)
    		this.find();
    }

    public boolean findPre(int j){
    	boolean hasused = false;
    	if(isCanTransformByOneTime(wordList.get(this.notUse.get(j)),beginWord)){
    		continue = false;
    		linkArr.get(wordList.size()).add(this.notUse.get(i));
    	}    		
    	for(int i=0;i<this.used.size();i++){
    		if(isCanTransformByOneTime(wordList.get(this.notUse.get(j)),wordList.get(this.used.get(i)))){
    			hasused = true;
    			linkArr.get(this.notUse.get(j)).add(this.used.get(i));
    		}
    	}
    	return hasused;
    }

    //
    public boolean isCanTransformByOneTime(String targetStr ,String sourceStr){
    	int sameNum = 0;
    	for(int i=0;i<sourceStr.length();i++){
    		if(targetStr[i].equals(sourceStr[i]))
    			sameNum++;
    	}
    	if(sameNum == 1)
    		return true;
    	else
    		return false;
    }
}
