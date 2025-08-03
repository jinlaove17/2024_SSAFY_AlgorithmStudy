import java.util.*;
class Solution {
    int min = 1;
    int max = Integer.MIN_VALUE;
    public int solution(int[] diffs, int[] times, long limit) {
        int answer = 0;
        for(int d : diffs){
            max = Math.max(max, d);
        }
        while(min < max) {
            int mid = (min + max) /2;
            long time = calculate(mid, diffs, times);
            if(time <=limit) {
                max = mid;
            } else{
                min = mid+1;
            }
        }
        return max;
    }
    public long calculate(int mid, int[] diffs, int[] times){
        long result = 0;
        for(int i=0; i<diffs.length; i++){
            int sub = mid - diffs[i];
            if(sub >= 0) {
                result += times[i];
            } else{
                int prev=0;
                if(i >0) {
                    prev = times[i-1];
                }
                result+=(prev+times[i])*sub*-1+times[i];
            }
        }
        return result;
    }
}