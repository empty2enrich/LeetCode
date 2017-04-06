public class MedianOfTwoSortedArray {
	int firstArrStartIndex;
	int firstArrMiddleIndex;
	int firstArrEndIndex;
	int secondArrStartIndex;
	int secondArrMiddleIndex;
	int secondArrEndIndex;
	int[] nums1;
	int[] nums2;
	int nowIndex;
	int sortIndex;
	boolean isOdd;
  	boolean isNumsLonger;

	int index;//
	public MedianOfTwoSortedArray(){};
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
      this.isNumsLonger = nums1.length>nums2.length?true:false;
    	this.nums1 = this.isNumsLonger?nums2:nums1;
    	this.nums2 = this.isNumsLonger?nums1:nums2;
        this.firstArrStartIndex = 0;
        this.firstArrEndIndex = this.nums1.length - 1;
        this.firstArrMiddleIndex = (this.firstArrStartIndex + this.firstArrEndIndex)/2;// + (this.firstArrStartIndex + this.firstArrEndIndex)%2;
        this.secondArrStartIndex = 0;
        this.secondArrEndIndex = this.nums2.length -1;
        this.secondArrMiddleIndex = (this.secondArrStartIndex + this.secondArrEndIndex)/2;// + (this.secondArrStartIndex + this.secondArrEndIndex)%2;
        this.isOdd = (this.nums1.length+this.nums2.length)%2==0?false : true;

        //如果nums1或nums2为空：
        if(this.nums1.length == 0||this.nums2.length == 0){
          if(isOdd)
            return this.nums1.length==0?Double.valueOf(this.nums2[this.nums2.length/2]):Double.valueOf(nums1[nums1.length/2]);
          else
            return this.nums1.length==0?Double.valueOf(this.nums2[this.nums2.length/2]+this.nums2[this.nums2.length/2-1])/2:Double.valueOf(nums1[nums1.length/2]+nums1[nums1.length/2-1])/2;
        }
        //nums1 nums2 不重叠
       	if(this.nums2[secondArrEndIndex]<this.nums1[firstArrStartIndex]||this.nums2[secondArrStartIndex]>this.nums1[firstArrEndIndex]){
          this.sortIndex = (this.nums1.length + this.nums2.length) / 2 - this.nums1.length;
          boolean firstArrIsLargerSecond = this.nums1[firstArrStartIndex]>this.nums2[secondArrEndIndex]?true : false;
          int nextIndex = firstArrIsLargerSecond?-1:1;
          return isOdd?Double.valueOf(this.getInSortIndex(sortIndex,firstArrIsLargerSecond)):Double.valueOf(this.getInSortIndex(sortIndex,firstArrIsLargerSecond) + this.getInSortIndex(sortIndex + nextIndex,firstArrIsLargerSecond))/2;
       	
       	}else{
          this.find();
          return this.getResult();
        }
        //



    }
  //获取 sortIndex 所指示的数
    public int getInSortIndex(int sortIndex,boolean firstArrIsLargerSecond){
      if(firstArrIsLargerSecond)
        return sortIndex>-1?nums2[secondArrEndIndex - sortIndex]:nums1[firstArrStartIndex - 1 - sortIndex];
      else{
        if(isOdd)
          return sortIndex>-1?nums2[sortIndex]:nums1[firstArrEndIndex + sortIndex +1];
        else
          return sortIndex>0?nums2[sortIndex-1] : nums1[firstArrEndIndex+sortIndex];
      }
        // return sortIndex>0?nums2[sortIndex]:nums1[firstArrEndIndex+1+sortIndex];
    }

    public double getResult(){
      int firstNum = 0;
      int secondNum = 0;
      while(nowIndex<(nums2.length + nums1.length)/2){
    	  firstNum = this.getNowLeast();
    	  nowIndex++;
        if(nowIndex == (nums1.length + nums2.length)/2){
//          firstNum = this.getNowLeast();
          secondNum = this.getNowLeast();
        }
        
      }
      return isOdd ? Double.valueOf(secondNum) : (Double.valueOf(firstNum)+Double.valueOf(secondNum))/2;
    }

    public int getNowLeast(){
      if(firstArrStartIndex>firstArrEndIndex ||secondArrStartIndex>secondArrEndIndex)
        return firstArrStartIndex>firstArrEndIndex?nums2[secondArrStartIndex++]:nums1[firstArrStartIndex++];
      if(nums1[firstArrStartIndex]>nums2[secondArrStartIndex])
        return nums2[secondArrStartIndex++];
      else
        return nums1[firstArrStartIndex++];
    }

    // public deleteBehindHalf(int minorStart,int minorMiddle,int minorEnd,int largerStart,int largerMiddle,int largerEnd){
    //   Field[] fields = this.getClass().getDeclaredFields();
    //   fields[minorStart].setAccessible(true);
    //   fields[minorMiddle].setAccessible(true);
    //   fields[minorStart] = fields[minorMiddle];
    //   fields[minorMiddle] = ()
    // }

    // public boolean find(){
    //   if(nums1[firstArrMiddleIndex]>nums2[secondArrMiddleIndex]){
    //     if(secondArrMiddleIndex - secondArrStartIndex + nowIndex < (nums1.length + nums2.length)/2){
    //       this.nowIndex += secondArrMiddleIndex - secondArrStartIndex;
    //       this.firstArrEndIndex = this.firstArrMiddleIndex + (this.firstArrStartIndex + this.firstArrEndIndex)%2 ;
    //       this.firstArrMiddleIndex = (firstArrStartIndex + firstArrEndIndex)/2 ;//+ (firstArrStartIndex + firstArrEndIndex)%2 ;
    //       this.secondArrStartIndex = this.secondArrMiddleIndex;
    //       this.secondArrMiddleIndex = (secondArrStartIndex + secondArrEndIndex)/2 ;//+ (secondArrStartIndex + secondArrEndIndex)%2;
    //       return this.firstArrMiddleIndex==this.firstArrStartIndex||this.secondArrStartIndex==this.secondArrMiddleIndex||find();//nowIndex<(nums1.length + nums2.length)/2||
    //     }else{
    //       return false;
    //     }
    //   }else{
    //     if(nowIndex + firstArrMiddleIndex - firstArrStartIndex <(nums1.length + nums2.length)/2){
    //       this.nowIndex += firstArrMiddleIndex - firstArrStartIndex;
    //       this.firstArrStartIndex = this.firstArrMiddleIndex;
    //       this.firstArrMiddleIndex = (this.firstArrStartIndex + this.firstArrEndIndex)/2;
    //       this.secondArrEndIndex = this.secondArrMiddleIndex + (this.secondArrStartIndex+this.secondArrEndIndex)%2;
    //       this.secondArrMiddleIndex = (this.secondArrStartIndex + this.secondArrEndIndex)/2;
    //       return this.firstArrMiddleIndex==this.firstArrStartIndex||this.secondArrMiddleIndex==this.secondArrStartIndex||find();
    //     }else{
    //       return false;
    //     }
    //   }

    // }

    public boolean find(){
      if(nums1[firstArrMiddleIndex]>nums2[secondArrMiddleIndex]){
        int dropNum = this.firstArrEndIndex - (this.firstArrMiddleIndex + (this.firstArrStartIndex+this.firstArrEndIndex)%2);
        nowIndex += dropNum;
        this.secondArrStartIndex += dropNum;
        this.secondArrMiddleIndex = (this.secondArrStartIndex + this.secondArrEndIndex)/2;
        this.firstArrEndIndex = this.firstArrEndIndex - dropNum;
        this.firstArrMiddleIndex = (this.firstArrStartIndex + this.firstArrEndIndex)/2;
        return firstArrStartIndex==firstArrMiddleIndex||secondArrStartIndex==secondArrMiddleIndex||find();
      }else{
        int dropNum = this.firstArrMiddleIndex - this.firstArrStartIndex;
        nowIndex += dropNum;
        this.secondArrEndIndex -= dropNum;
        this.secondArrMiddleIndex = (this.secondArrStartIndex+this.secondArrEndIndex)/2;
        this.firstArrStartIndex = this.firstArrMiddleIndex;
        this.firstArrMiddleIndex = (this.firstArrStartIndex+this.firstArrEndIndex)/2;
        return firstArrStartIndex==firstArrMiddleIndex||secondArrStartIndex==secondArrMiddleIndex||find();
      }
    }
}
