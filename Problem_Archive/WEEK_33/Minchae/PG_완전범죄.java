/**
 * 완전범죄
 * https://school.programmers.co.kr/learn/courses/30/lessons/389480
 *
 * @author minchae
 * @date 2025. 3. 5.
 *
 * 문제 풀이
 * - 일단 b가 다 훔친다고 생각하고 거기서 m미만으로 줄여가기
 * - 그런데 시간초과 발생 -> 어떻게 시간초과를 줄일까..
 * - dp? 근데 어떻게 쓸지 모르겠다.
 * 
 * - dp 2차원 배열 선언 -> dp[훔친 물건 개수][b의 흔적] = a의 흔적
 * - 여기서 a의 흔적이 최소가 되면 된다. -> 그럼 dp 배열의 초기값은 다 Integer.MAX_VALUE로 설정해야 하나?
 * - 그럼 dp 배열 어떻게 값 계산할 건데.. 일단 아이템 길이만큼 반복
 * - 그다음 a를 선택한 경우, b를 선택한 경우 두 가지를 나눔
 * 
 * 테케에서 틀리는 이유를 못찾겠어서 한번 봐달라고 올린 거니까 혼내지 말아줘..ㅜ
 *
 * 시간 복잡도
 * O()
 *
 * 실행 시간
 *  ms
 */

import java.util.*;

public class Solution {
	
	  static int answer;

    public static int solution(int[][] info, int n, int m) {
    	int len = info.length;
    	
    	int[][] dp = new int[len + 1][m];
    	
    	// 일단 다 최댓값으로 초기화 -> n이 어차피 최댓값이니까 n으로 초기화
    	for (int i = 0; i <= len; i++) {
    		Arrays.fill(dp[i], n);
    	}
    	
    	dp[0][0] = 0;
    	
    	for (int i = 1; i <= len; i++) {
    		int aCnt = info[i - 1][0];
    		int bCnt = info[i - 1][1];
    		
    		// 물건 훔치기
    		for (int j = 0; j < m; j++) {
    			// a를 선택하는 경우 -> 이전 물건 훔친 거에서 a의 흔적 추가
    			dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + aCnt);
    			
    			// b를 선택하는 경우 -> a의 흔적 개수는 똑같으니까 이전 물건 훔친 거랑 현재 거랑 비교해서 작은 걸로 갱신
    			if (j + bCnt < m) {
    				dp[i][j + bCnt] = Math.max(dp[i][j + bCnt], dp[i - 1][j]);
    			}
    		}
    	}
    	
    	answer = n;
    	
    	for (int i = 0; i < m; i++) {
    		answer = Math.min(answer, dp[len][i]);
    	}
    	
    	return answer >= n ? -1 : answer;
    	
    	// 시간초과
//        int bSum = 0;
//        
//        for (int[] cnt : info) {
//        	bSum += cnt[1];
//        }
//        
//        select(0, 0, bSum, info, n, m);
//
//        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    // 시간초과 나는 코드
    private static void select(int depth, int aCnt, int bCnt, int[][] info, int n, int m) {
        // 이미 최솟값을 초과한다면 종료
        if (aCnt >= answer) {
            return;
        }
        
        // 둘 다 흔적이 초과하지 않는 경우 a의 흔적 개수 갱신
        if (depth == info.length && aCnt < n && bCnt < m) {
        	answer = Math.min(answer, aCnt);
        	return;
        }
        
        // b가 훔치는 경우는 그대로 둠
        select(depth + 1, aCnt, bCnt, info, n, m);
        
        // a가 훔치는 경우
        select(depth + 1, aCnt + info[depth][0], bCnt - info[depth][1], info, n, m);
    }

}
