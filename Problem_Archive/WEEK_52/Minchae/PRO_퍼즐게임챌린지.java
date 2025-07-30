class Solution {
    public int solution(int[] diffs, int[] times, long limit) {
		int minLevel = 1;
		int maxLevel = 100000;
		
		while (minLevel <= maxLevel) {
			int mid = (minLevel + maxLevel) / 2;
			long time = calTime(mid, diffs, times);
			
			if (time > limit) {
				minLevel = mid + 1;
			} else {
				maxLevel = mid - 1;
			}
			
		}
		
		return minLevel;
    }
	
	private static long calTime(int level, int[] diffs, int[] times) {
		long result = 0;
		
		for (int i = 0; i < diffs.length; i++) {
			if (diffs[i] <= level) {
				result += times[i];
			} else {
				result += (times[i] + times[i - 1]) * (diffs[i] - level) + times[i];
			}
		}
		
		return result;
	}
}
