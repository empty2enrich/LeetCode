public class MedianOfTwoSortedArray {
	int firstArrStartIndex;
	int firstArrMiddleIndex;
	int firstArrEndIndex;
	int secondArrStartIndex;
	int secondArrMiddleIndex;
	int secondArrEndIndex;
  int nowIndex;
	int sortIndex;
	boolean isOdd;

	int index;//
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        this.firstArrStartIndex = 0;
        this.firstArrEndIndex = nums1.length - 1;
        this.firstArrMiddleIndex = (this.firstArrStartIndex + this.firstArrEndIndex)/2;
        this.secondArrStartIndex = 0;
        this.secondArrEndIndex = nums2.length -1;
        this.secondArrMiddleIndex = (this.secondArrStartIndex + this.secondArrEndIndex)/2;
        this.isOdd = (nums1.length+nums2.length)%2==0?false : true;
        //nums1 nums2 不重叠
       	if(nums2[secondArrEndIndex]<nums1[firstArrStartIndex]||nums2[secondArrStartIndex]>nums1[firstArrEndIndex]){
          this.sortIndex = (nums1.length + nums2.length) / 2 - nums1.length;
          boolean firstArrIsLargerSecond = nums1[firstArrStartIndex]>nums2[secondArrEndIndex]?true : false;
          int nextIndex = firstArrIsLargerSecond?1:-1;
          return isOdd?Double.valueOf(this.getInSortIndex(sortIndex)):Double.valueOf(this.getInSortIndex(sortIndex,firstArrIsLargerSecond) + this.getInSortIndex(sortIndex + nextIndex,firstArrIsLargerSecond));
       		// if(firstArrIsLargerSecond){
         //    this.sortIndex = (nums1.length + nums2.length) / 2 - nums2.length;
       		// 	if(isOdd)
       		// 		return sortIndex>0?nums1[sortIndex]:nums2[secondArrEndIndex + 1 + sortIndex];
         //    else{
         //      return sortIndex>0?Double.valueOf(nums1[sortIndex]+nums1[sortIndex-1])/2:Double.valueOf(nums2[secondArrEndIndex+1+sortIndex]+nums2[secondArrEndIndex+sortIndex])/2;
         //    }
       		// }else{
         //    this.sortIndex = (nums1.length + nums2.length)/2 - nums1.length;
         //    if(isOdd)
         //      return sortIndex>0?nums2[sortIndex] : nums1[firstArrEndIndex + 1 + sortIndex];
         //    else{
         //      return sortIndex>0?Double.valueOf(nums2[sortIndex] + nums2[sortIndex-1])/2:
         //              Double.valueOf(nums1[firstArrEndIndex+1+sortIndex]+nums1[firstArrStartIndex+sortIndex])/2;
         //    }
         //  }
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
          return sortIndex>0?nums2[sortIndex - 1]:nums1[firstArrEndIndex + sortIndex];
        else
          return sortIndex>-1?nums2[sortIndex] : nums1[firstArrEndIndex+sortIndex+1];
      }
        // return sortIndex>0?nums2[sortIndex]:nums1[firstArrEndIndex+1+sortIndex];
    }

    public double getResult(){
      int firstNum = 0;
      int secondNum = 0;
      while(sortIndex<(nums2.length + nums1.length)/2){
        this.getNowLeast();
        sortIndex++;
        if(sortIndex == (nums1.length + nums2.length)/2){
          firstNum = this.getNowLeast();
          secondNum = this.getNowLeast();
        }
        return isOdd ? Double.valueOf(firstNum) : (Double.valueOf(firstNum)+Double.valueOf(secondNum))/2;
      }
    }

    public int getNowLeast(){
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

    public boolean find(){
      if(nums1[firstArrMiddleIndex]>nums2[secondArrMiddleIndex]){
        if(secondArrMiddleIndex - secondArrStartIndex + nowIndex < (nums1.length + nums2.length)/2){
          this.nowIndex += secondArrMiddleIndex - secondArrStartIndex;
          this.firstArrEndIndex = this.firstArrMiddleIndex + 1;
          this.firstArrMiddleIndex = (firstArrStartIndex + firstArrEndIndex)/2;
          this.secondArrStartIndex = this.secondArrMiddleIndex;
          this.secondArrMiddleIndex = (secondArrStartIndex + secondArrEndIndex)/2;
          return this.firstArrMiddleIndex==this.firstArrStartIndex||this.secondArrMiddleIndex==this.secondArrStartIndex||find();//nowIndex<(nums1.length + nums2.length)/2||
        }else{
          return false;
        }
      }else{
        if(nowIndex + firstArrMiddleIndex - firstArrStartIndex <(nums1.length + nums2.length)/2){
          this.nowIndex += firstArrMiddleIndex - firstArrStartIndex;
          this.firstArrStartIndex = this.firstArrMiddleIndex;
          this.firstArrMiddleIndex = (this.firstArrStartIndex + this.firstArrEndIndex)/2;
          this.secondArrEndIndex = this.secondArrMiddleIndex + 1;
          this.secondArrMiddleIndex = (this.secondArrStartIndex + this.secondArrEndIndex)/2;
          return this.firstArrMiddleIndex==this.firstArrStartIndex||this.secondArrMiddleIndex==this.secondArrStartIndex||find();
        }else{
          return false;
        }
      }

    }
}
