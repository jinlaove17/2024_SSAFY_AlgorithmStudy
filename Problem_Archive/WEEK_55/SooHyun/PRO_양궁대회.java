import java.util.*;
class Solution {
    public int[] answer;
    public int[] res ={-1};
    public int max = -1000;
    public int[] solution(int n, int[] info) {
        answer = new int[11];
        dfs(info, 0, n);
        return res;
    }
    public void dfs(int[] info, int cnt, int n){
        if(cnt == n){
            int lion_point=0;
            int appeach_point=0;
            for(int i=0; i<11; i++){
                if(answer[i] !=0 || info[i] !=0){
                    if(info[i] >= answer[i]){
                        appeach_point+=10-i;
                    }
                    else{
                        lion_point += 10-i;
                    }
                }
            }
            if(lion_point > appeach_point){
                if(lion_point-appeach_point >= max){
                    res = answer.clone();
                    max = lion_point-appeach_point;
                }
            }
            return;
        }
        for(int j=0; j<11 && answer[j] <= info[j]; j++){
            answer[j]++;
            dfs(info, cnt+1, n);
            answer[j]--;
        }
    }
}