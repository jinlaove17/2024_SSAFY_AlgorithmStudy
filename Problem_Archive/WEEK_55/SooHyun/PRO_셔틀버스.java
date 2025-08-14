import java.util.*;
class Solution {
    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";
        PriorityQueue<Integer> que = new PriorityQueue<>();
        for(String time : timetable)
            que.add(get_Time(time));
        int start = 9*60;
        int cnt=0;
        int last=0;
        for(int i=0; i<n; i++){
            cnt=0;
            while(!que.isEmpty()){
                int temp = que.peek();
                if(temp <= start && cnt <m){
                    que.poll();
                    cnt++;
                }else{
                    break;
                }
                last = temp-1;
            }
            start+=t;
        }
        if(cnt <m){
            last = start-t;
        }
        String hour = String.format("%02d", last/60);
        String minute= String.format("%02d", last%60);
        answer=hour+":"+minute;
        return answer;
    }
    public int get_Time(String time){
        String[] arr = time.split(":");
        int hour = Integer.parseInt(arr[0]);
        int minute = Integer.parseInt(arr[1]);
        return hour*60 + minute;
    }
}