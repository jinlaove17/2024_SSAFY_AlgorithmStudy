import java.util.*;

class Solution {
    public int solution(int[][] data, int col, int row_begin, int row_end) {
        int ret = 0;
        Arrays.sort(data, Comparator
                    .comparingInt((int[] row) -> row[col-1])
                    .thenComparing(row -> row[0], Comparator.reverseOrder()));
        
        for (int i = row_begin; i <= row_end; i++) {
            int sum = 0;
            for (int val : data[i-1]) sum += val % i;
            ret ^= sum;
        }
        return ret;
    }
}
