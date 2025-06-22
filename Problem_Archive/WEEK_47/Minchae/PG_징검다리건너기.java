class Solution {
	public static int solution(int[] stones, int k) {
		int low = Integer.MAX_VALUE;
		int high = Integer.MIN_VALUE;
		
		for (int s : stones) {
			low = Math.min(low, s); // 징검다리를 건널 수 있는 친구들의 최소값
			high = Math.max(high, s); // 징검다리를 건널 수 있는 친구들의 최대값
		}
        
		if (low == high) {
			return low;
		}
        
       while (low < high) {
       	int mid = (low + high + 1) / 2;
   		
       	if (isPossible(stones, mid, k)) {
       		low = mid;
       	} else {
       		high = mid - 1;
       	}
       }
       
       return low;
    }
	
	public static boolean isPossible(int[] stones, int mid, int k) {
		int jump = 0;
		
		for (int s : stones) {
			// 0일때는 건너뛰지 않고 지나갈 수 있다는 의미이기 때문에 =를 붙이지 않고 마이너스일때만 jump++ 해줌
			if (s - mid < 0) { // if (s < mid)
				jump++;
			} else {
				jump = 0;
			}
			
			if (jump == k) {
				return false;
			}
		}
		
		return true;
	}
}
