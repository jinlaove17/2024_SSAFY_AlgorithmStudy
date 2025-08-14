
import java.util.*;

class Solution {
    
    static int[] crew;
    
    public String solution(int n, int t, int m, String[] timetable) {
        crew = new int[timetable.length];
        
        // 계산하기 쉽게 분으로 변경해서 저장
        for (int i = 0; i < crew.length; i++) {
            String time = timetable[i];
            
            int hour = Integer.parseInt(time.substring(0, 2));
            int minute = Integer.parseInt(time.substring(3, 5));
            
            crew[i] = hour * 60 + minute;
        }
        
        Arrays.sort(crew); // 오름차순 정렬
        
        int shuttle = 540; // 9시 시작
        int num = 0; // 마지막 탑승자 번호
        
        int answer = 0;
        
        for (int i = 1; i <= n; i++) {
            int cnt = 0; // 탑승 인원
            
            // 현재 크루가 셔틀에 탑승할 수 있는 경우
            while (num < crew.length && crew[num] <= shuttle && cnt < m) {
                cnt++;
                num++;
            }
            
            if (i == n) {
                // 인원이 꽉 차면 마지막보다 1분 빠르게, 아니면 셔틀 시간에 태우기
                if (cnt == m) {
                    answer = crew[num - 1] - 1;
                } else {
                    answer = shuttle;
                }
            }
            
            shuttle += t; // 다음 셔틀 시간
        }
        
        return String.format("%02d:%02d", answer / 60, answer % 60);
    }
}
