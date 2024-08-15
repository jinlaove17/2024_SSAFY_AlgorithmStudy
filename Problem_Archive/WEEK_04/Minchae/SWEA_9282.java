/**
 * D4 9282. 초콜릿과 건포도
 * 
 * @author minchae
 * @date 2024. 8. 14.
 */

import java.util.*;
import java.io.*;

public class SWEA_9282 {
	
	static int n, m;
	static int[][] map;
	
	static int[][] sumMap; // 누적합 저장 배열
	static int[][][][] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			
			map = new int[n][m];
			sumMap = new int[n + 1][m + 1];
			dp = new int[n + 1][m + 1][n + 1][m + 1];
			
			for (int i = 0; i < n; i++) {
				st = new StringTokenizer(br.readLine());
				
				for (int j = 0; j < m; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					
					// 2차원 배열에서의 누적합 저장
					// 이전 행의 누적합 + 이전 열의 누적합 - 중복값 + 현재 칸의 값
					sumMap[i + 1][j + 1] = sumMap[i + 1][j] + sumMap[i][j + 1] - sumMap[i][j] + map[i][j];
				}
			}
			
			int answer = solve(1, 1, n, m);
			
			// 동현이가 주어야 하는 건포도 개수의 최솟값을 출력
			System.out.println("#" + t + " " + answer);
		}

	}
	
	private static int solve(int x, int y, int ex, int ey) {
		// 종료 조건
		if (x == ex && y == ey) {
			return 0;
		}
		
		// 이미 값이 정해진 경우 바로 리턴
		if (dp[x][y][ex][ey] > 0) {
			return dp[x][y][ex][ey];
		}
		
		// 일단 최댓값 저장
		dp[x][y][ex][ey] = Integer.MAX_VALUE;
		
		// x ~ ex, y ~ ey만큼 잘랐을 때의 누적합 구함
		// 현재 누적합 - (이전 행의 누적합 + 이전 열의 누적합 - 중복값)
		int cur = sumMap[ex][ey] - (sumMap[x - 1][ey] + sumMap[ex][y - 1] - sumMap[x - 1][y - 1]);
		
		// 수평으로 자르는 경우
		for (int i = x; i < ex; i++) {
			// x ~ i, (i + 1) ~ ex까지 두 개로 나눠서 계산
			int sum1 = solve(x, y, i, ey);
			int sum2 = solve(i + 1, y, ex, ey);
			
			// 어느 행에서 잘랐을 때 최솟값인지 구함
			dp[x][y][ex][ey] = Math.min(dp[x][y][ex][ey], sum1 + sum2 + cur);
		}
		
		// 수직으로 자르는 경우 (수평으로 자르는 경우와 반대로 생각)
		for (int i = y; i < ey; i++) {
			int sum1 = solve(x, y, ex, i);
			int sum2 = solve(x, i + 1, ex, ey);
			
			dp[x][y][ex][ey] = Math.min(dp[x][y][ex][ey], sum1 + sum2 + cur);
		}
		
		return dp[x][y][ex][ey];
	}

}