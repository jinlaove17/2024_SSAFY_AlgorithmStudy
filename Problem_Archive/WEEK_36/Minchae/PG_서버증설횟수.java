/**
 * 서버 증설 횟수
 * https://school.programmers.co.kr/learn/courses/30/lessons/389479?language=java
 *
 * @author minchae
 * @date 2025. 3. 24.
 *
 * 문제 풀이
 * - 필요한 서버 개수 = (player의 수 / m)
 * - 시간대별로 필요한 서버 개수를 저장할 1차원 배열 선언
 * - 해당 시간에 필요한 서버 개수 구함 -> 서버가 부족한 경우 (필요한 서버 개수 - 해당 시간 서버 개수)만큼의 서버를 추가해야 함
 * - 그런데 k시간만큼 지속되니까 반복문을 통해 현재 시간부터 k이후까지의 서버 개수를 증가시킴
 *
 * 시간 복잡도
 * O(24 * k)
 *
 * 실행 시간
 * 0.03 ms
 */

public class Server {

	public static int solution(int[] players, int m, int k) {
        int answer = 0;
        
        int[] server = new int[24];
        
        for (int time = 0; time < 24; time++) {
        	int cnt = players[time] / m; // 필요한 서버 개수
        	
        	// 서버가 더 필요한 경우
        	if (cnt > server[time]) {
        		int sum = cnt - server[time]; // 추가되는 서버 개수
        		
        		// k시간만큼 지속되므로 k만큼 반복 -> (현재 시간 + i)에 추가되는 서버 개수를 더함
        		for (int i = 0; i < k; i++) {
        			if (time + i < 24) {
        				server[time + i] += sum;
        			}
        		}
        		
        		answer += sum; // 서버 증설 횟수 증가
        	}
        }
        
        return answer;
    }

}
