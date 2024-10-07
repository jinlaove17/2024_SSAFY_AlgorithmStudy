/**
 * 1106 호텔
 * https://www.acmicpc.net/problem/1106
 * 
 * @author minchae
 * @date 2024. 10. 7.
 * 
 * 문제 접근 아이디어 및 알고리즘 판단 사유
 * 	- dp 사용
 * 	- 주의할 점 : 적어도 C명을 늘리는 것 -> C명보다 더 많은 고객을 늘릴 때의 비용이 더 작을 경우가 있음을 고려
 * 
 * 시간 복잡도
 * O(N * (C + 1))
 * 
 * 실행 시간
 * 100 ms
 * */

import java.util.*;
import java.io.*;

public class Main {
	
	static int N, C;
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		C = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		
		// 적어도 C명을 늘이는 것 -> 한 번에 최대 100명 늘일 수 있음
		// dp[C] 보다 dp[C + 11] 이런 게 더 클 수 있음
		int[] dp = new int[C + 101];
		Arrays.fill(dp, 987654321); // 밑에서 cost를 더하면 int 범위를 벗어나게 돼서 MAX_VALUE 사용 X
		
		dp[0] = 0;
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			int cost = Integer.parseInt(st.nextToken());
			int people = Integer.parseInt(st.nextToken());
			
			for (int j = people; j < C + 101; j++) {
				// 더 작은 값을 갱신
				dp[j] = Math.min(dp[j], cost + dp[j - people]);
			}
		}

		int answer = Integer.MAX_VALUE;
		
		for (int i = C; i < C + 101; i++) {
			answer = Math.min(answer, dp[i]);
		}
		
		System.out.println(answer);
	}

}
