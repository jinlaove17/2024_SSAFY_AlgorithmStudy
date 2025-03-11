/**
 * 2662 기업투자
 * https://www.acmicpc.net/problem/2662
 *
 * @author minchae
 * @date 2025. 3. 11.
 *
 * 문제 풀이
 * - dp 느낌이 나는 문제...
 * - dp[i][j] -> j번째 기업에 i만큼 투자했을 때의 최대 이익 저장
 * - 투자 내역을 출력하기 위해 path 2차원 배열 사용
 * 
 * 시간 복잡도
 * O(M * N)
 *
 * 실행 시간
 * 176 ms
 **/

import java.io.*;
import java.util.*;

public class Main {

	static int N, M;
	
	static int[][] company; // 투자했을 때 이익 저장
	static int[][] dp;
	static int[][] path; // 투자 내역 저장
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		company = new int[N + 1][M + 1];
		dp = new int[N + 1][M + 1];
		path = new int[N + 1][M + 1];
		
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for (int j = 0; j <= M; j++) {
				company[i][j] = Integer.parseInt(st.nextToken());
			}
			
			Arrays.fill(dp[i], -1);
		}
		
		System.out.println(invest(1, N)); // 최대 이익
		
		int rest = N;
		
		// 각 기업에 투자한 액수 출력
		for (int i = 1; i <= M; i++) {
			System.out.print(path[rest][i] + " ");
			
			rest -= path[rest][i]; // 금액 감소
		}
	}
	
	// 투자하기
	private static int invest(int depth, int money) {
		// 모든 회사에 다 투자했거나, 투자 금액을 다 쓴 경우 종료
		if (depth > M || money == 0) {
			return 0;
		}
		
		// 이미 값이 저장되어 있다면 바로 값 반환
		if (dp[money][depth] > -1) {
			return dp[money][depth];
		}
		
		int max = 0;
		
		// 모든 투자 금액에 대해서 최대 이익 계산
		for (int i = 0; i <= money; i++) {
			// 현재 회사에 i만큼 투자하는 경우 (다음 기업으로 갈 때는 현재 금액에서 i 빼고 보냄 + 현재 기업에 i만큼 투자했을 때의 금액)
			int cur = invest(depth + 1, money - i) + company[i][depth];
			
			// 계산된 값이 더 큰 경우 투자 내역 저장
			if (max < cur) {
				path[money][depth] = i;
				max = cur; // 최대 이익 갱신
			}
		}
		
		dp[money][depth] = max; // 현재 기업에 최대 이익 저장
		
		return max;
	}

}
