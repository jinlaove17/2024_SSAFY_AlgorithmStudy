import java.util.*;
class Solution {
    public int solution(int[][] data, int col, int row_begin, int row_end) {
        int answer = 0;
        int idx = col-1;
        Arrays.sort(data, (o1,o2)->{
            if(o1[idx]==o2[idx])
                return o2[0]-o1[0];
            return o1[idx]-o2[idx];
        });
        ArrayList<int[]> list = new ArrayList<>();
        for(int i=row_begin-1; i<row_end; i++){
            list.add(data[i]);
        }
        int start = row_begin;
        for(int i=0; i<list.size(); i++){
            int[] arr = list.get(i);
            int temp=0;
            for(int j=0; j<arr.length; j++){
                temp+=(arr[j] % start);

            }
            start++;
            answer = answer^temp;
        }
        return answer;
    }
}