
public class Logmin{
	public int[] nums1;
	public int[] nums2;
	// public int startIndex;
	// public int endIndex;
	public int firstIndex;
	public int secondIndex;
	public boolean isOdd;
	public Logmin(){};
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		this.nums1 = nums1.length>nums2.length?nums2:nums1;
		this.nums2 = nums1.length>nums2.length?nums1:nums2;
		this.isOdd = (nums1.length+nums2.length)%2==0?false:true;
		// this.startIndex = 0;
		// this.endIndex = nums1.length;
		this.find(0,this.nums1.length);
		return this.getResult();
	}

	// public double getResult(){
	// 	int left =0;
	// 	int right = 0;
	// 	if(firstIndex == 0){
	// 		if(nums2.length!=1){
	// 			left = nums2[secondIndex-1];
	// 		}
	// 		if(nums1.length==0){
	// 			right = nums2[secondIndex];
	// 		}else{
	// 			right = nums1[firstIndex]<nums2[secondIndex]?nums1[firstIndex]:nums2[secondIndex];
	// 		}
	// 	}else if(firstIndex == nums1.length){
	// 		left = nums1[firstIndex-1]>nums2[secondIndex-1]?nums1[firstIndex-1]:nums2[secondIndex-1];
	// 		right = nums2[secondIndex];
	// 	}else{
	// 		right = nums1[firstIndex]<nums2[secondIndex]?nums1[firstIndex]:nums2[secondIndex];
	// 		left = nums1[firstIndex-1]>nums2[secondIndex-1]?nums1[firstIndex-1]:nums2[secondIndex-1];
	// 	}
	// 	return isOdd?Double.valueOf(right):Double.valueOf(left+right)/2;
	// }

	public double getResult(){
		int left = getArrAtIndex(nums1,firstIndex-1)>getArrAtIndex(nums2,secondIndex-1)?getArrAtIndex(nums1,firstIndex-1):getArrAtIndex(nums2,secondIndex-1);
		int right = getArrAtIndex(nums1,firstIndex)<getArrAtIndex(nums2,secondIndex)?getArrAtIndex(nums1,firstIndex):getArrAtIndex(nums2,secondIndex);
		return isOdd?Double.valueOf(right):Double.valueOf(left+right)/2;
	}

	public int getArrAtIndex(int[] nums,int index){
		if(nums.length==0||index<0||index>nums.length-1){
			return 0;
		}else{
			return nums[index];
		}
	}

	public void find(int startIndex,int endIndex){
		this.firstIndex = (startIndex + endIndex)/2;
		this.secondIndex = this.findSecondIndex();
		if(firstIndex-1>0){
			if(nums1[firstIndex-1]>nums2[secondIndex-1]&&firstIndex!=startIndex)
				this.find(startIndex,firstIndex-1);
		}
		if(firstIndex!=nums1.length){
			if(nums1[firstIndex]<nums2[secondIndex-1]&&firstIndex!=startIndex)
				this.find(firstIndex,endIndex);
		}
		// if(firstIndex == 0){//nums1 长度为0,1,2
		// 	if(nums1.length==0){

		// 	}else if(nums1.length==1){
		// 		if(nums1[firstIndex]<nums2[secondIndex-1]){
		// 			find(firstIndex,endIndex);
		// 		}else{}
		// 	}
		// }
		// if(firstIndex -1 >0){
		// 	if(nums1[firstIndex-1]>nums2[secondIndex]){
		// 		return startIndex==endIndex||startIndex==(endIndex-1)||find(startIndex,firstIndex-1);
		// 	}
		// }
		// if(firstIndex<nums1.length){
		// 	if(nums1[firstIndex]<nums2[secondIndex-1]){
		// 		return startIndex==endIndex||startIndex==(endIndex-1)||find(firstIndex,endIndex);
		// 	}else{
		// 		return startIndex==endIndex||find(startIndex,firstIndex-1);
		// 	}
		// }
		// return true;
	}
	
	public int findSecondIndex(){
		return (nums1.length + nums2.length)/2 - this.firstIndex;
	}
}
