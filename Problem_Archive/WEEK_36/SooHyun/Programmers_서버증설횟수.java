/*
문제 접근 아이디어 및 알고리즘 판단 사유
    - 문제 분류는 시뮬레이션입니다. 각 시점에 접속한 사용자 수(players[i])에 따라 필요한 서버 수를 계산합니다.
    - Server에 저장하기 위해 class를 생성합니다 time은 서버가 종료되는 시간 size는 필요한 서버 갯수를 의미합니다. 이후 que를 활용해 관리합니다.
    - 서버 1대당 m명의 사용자를 감당할 수 있으므로 현재 시점에서 필요한 서버 수는 players[i] / m (올림)입니다.
    - 현재 사용 중인 서버 수(size)와 비교하여 부족하면 서버를 증설하고 que에 종료 시점(time = i + k)과 함께 저장합니다.
    - 매 시간마다 que를 확인해 만료된 서버는 제거하고 size를 갱신합니다.
    - 필요 서버 수가 부족할 때만 서버를 증설하고 answer에 누적합니다.

시간 복잡도
    - 큐를 통한 서버의 증설 로직 : O(N)
    - 전체 시간복잡도 : O(N)

실행 시간
   - 0.87ms(java)
*/
import java.util.*;
class Solution {
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        int size=0;
        Queue<Server> que = new LinkedList<>();
        for(int i=0; i<players.length; i++){
            while(!que.isEmpty() && que.peek().time ==i){
                size-=que.poll().size;
            }
            int need = players[i] / m;
            int extra = size-need;
            if(extra <0){
                extra = -extra;
                size+=extra;
                answer += extra;
                que.add(new Server(i+k, extra));
            }
        }
        return answer;
    }
    public class Server{
        int time;
        int size;

        public Server(int time, int size){
            this.time = time;
            this.size = size;
        }
    }
}