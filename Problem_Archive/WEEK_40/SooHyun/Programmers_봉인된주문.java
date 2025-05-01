/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 오랜만에 수학적인 접근이 필요한 문제였습니다.
    - 알파벳의 갯수가 26개 였기에 26진법을 활용해서 N번째 문자열을 초기에 구합니다.
    - 그리고 bans안에 있는 문자열을 우선 순위 큐에 저장하는데 정렬 조건은 문자열의 길이로 오름차순, 만약 문자열의 길이가 같다면 사전 순으로 오름차순으로 정렬합니다.
    - 이후 우선 순위 큐에서 가장 작은 문자열을 꺼내서 현재 N번째의 문자열과 비교했을 때 ban의 문자열이 N번쨰 문자열 보다 길이가 짧거나 만약 길이가 같아도 사전 순으로 먼저 오게된다면 N을 하나 증가시키고 우선 순위 큐에서 하나를 제거합니다.
    - 이를 반복하여 최종 N을 구할 수 있고 최종적으로 N번째 문자열을 알아내 답을 도출합니다.

시간 복잡도
    - 입력 및 초기화 : O(B * logB) (B은 bans의 길이)
    - while 반복문
        - getString : O(log26 N)
        - 문자열 비교 : O(log N)
        - pq.poll : O(logB)
    - 전체 시간복잡도 : O(B * logB + B * (log26 N + log N + logB))

실행 시간
   - 559.19ms(java)
*/
import java.util.*;
class Solution {
    public String solution(long n, String[] bans) {
        PriorityQueue<String> pq = new PriorityQueue<>((o1, o2) -> {
            if(o1.length() == o2.length())
                return o1.compareTo(o2);
            return o1.length() - o2.length();
        });
        for(String b : bans)
            pq.add(b);
        while(!pq.isEmpty()) {
            String str = getString(n);
            String ban = pq.peek();
            if(ban.length() < str.length() || (ban.length() == str.length() && ban.compareTo(str) <= 0)) {
                pq.poll();
                n++;
            } else {
                break;
            }
        }
        return getString(n);
    }

    public String getString(long n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            n--;
            int remain = (int) (n % 26);
            sb.append((char) ('a' + remain));
            n /= 26;
        }
        return sb.reverse().toString();
    }
}