import java.util.*;

class Solution {
    public int solution(int[][] data, int col, int row_begin, int row_end) {
		ArrayList<int[]> list = new ArrayList<>(Arrays.asList(data));
		
		list.sort((v1, v2) -> {
			if (v1[col - 1] == v2[col - 1]) {
				return Integer.compare(v2[0], v1[0]);
			}
			
			return Integer.compare(v1[col - 1], v2[col - 1]);
		});
		
		int len = data[0].length;
		
		int answer = 0;
		
		for (int i = row_begin; i <= row_end; i++) {
			int[] cur = list.get(i - 1);
			int sum = 0;
			
			for (int j = 0; j < len; j++) {
				sum += cur[j] % i;
			}
			
			answer ^= sum;
		}
		
        
        return answer;
    }
}
