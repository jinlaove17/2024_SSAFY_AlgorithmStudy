/**
* 문제 풀이
* 	- 근무 태도 기준 내림 차순, 같을 경우 동료 평가 기준 오름 차순 정렬
* 	- 완호 점수가 다른 임의의 사원의 점수보다 낮을 경우 -1 반환
* 	- 동료 평가 기준 점수를 두고 이 점수보다 높고 점수 합이 높을 때만 등수 증가 -> 동료 평가 기준 갱신 시킴
* 
* 시간 복잡도
* O(logN)
* 
* 실행 시간
* 52.96ms
*/

import java.util.*;

class Solution {
    
    static class Person implements Comparable<Person> {
        int a;
        int b;
        
        public Person(int a, int b) {
            this.a = a;
            this.b = b;
        }
        
        @Override
        public int compareTo(Person o) {
            // 근무 태도 점수가 같을 경우 동료 평가 점수 기준으로 오름차순 정렬
            if (this.a == o.a) {
                return Integer.compare(this.b, o.b);
            }
            
            return Integer.compare(o.a, this.a);
        }
    }
    
    public int solution(int[][] scores) {
        PriorityQueue<Person> pq = new PriorityQueue<>();
        
        for (int i = 0; i < scores.length; i++) {
            pq.add(new Person(scores[i][0], scores[i][1]));
        }
        
        int answer = 1;
        
        int a = scores[0][0];
        int b = scores[0][1];
        
        int tmp = 0; // 동료 평가 기준 점수
        
        while (!pq.isEmpty()) {
            Person cur = pq.poll();
            
            // 완호의 점수가 다른 임의의 사원보다 모두 낮은 경우 인센티브 받지 못함
            if (a < cur.a && b < cur.b) {
                return -1;
            }
            
            if (tmp <= cur.b) {
                // 점수 합이 높은 경우에만 등수 증가
                if (cur.a + cur.b > a + b) {
                    answer++;
                }
                
                tmp = cur.b; // 동료 평가 기준 점수 갱신
            }
        }
        
        return answer;
    }
}
