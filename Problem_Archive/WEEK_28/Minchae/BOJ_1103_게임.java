/**
 * 1103 게임
 * https://www.acmicpc.net/problem/1103
 *
 * @author minchae
 * @date 2025. 1. 30.
 *
 * 문제 풀이
 * - 예전에 한 번 풀어봤던 문제로 쉽게 풀 수 있었다.
 * - dfs 이용 -> 그런데 횟수가 최대가 되어야 하기 때문에 탐색하면서 다음 칸에 저장된 횟수가 현재 횟수보다 큰 경우는 탐색하지 않음
 * - 무한번 움직일 수 있는 경우도 고려 -> 탐색을 하다가 방문한 곳을 다시 방문하게 되는 경우가 무한번 움직일 수 있는 경우
 *
 * 시간 복잡도
 * O(N * M)
 *
 * 실행 시간
 * 128 ms
 **/

import java.io.*;
import java.util.*;

public class Main2 {
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int N, M;
	static char[][] map;
	
	static int[][] dp;
	static boolean[][] visited;
	static boolean infinite;
	
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new char[N][M];
		visited = new boolean[N][M];
		dp = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
		}
		
		visited[0][0] = true;
		
		dfs(1, 0, 0);
		
		System.out.println(infinite ? -1 : answer);
	}
	
	private static void dfs(int cnt, int x, int y) {
		answer = Math.max(answer, cnt);
		dp[x][y] = cnt;
		
		for (int i = 0; i < 4; i++) {
			int move = map[x][y] - '0';
			
			int nx = x + (dx[i] * move);
			int ny = y + (dy[i] * move);
			
			// 범위를 벗어나거나, 구멍인 경우
			if (!isRange(nx, ny) || map[nx][ny] == 'H') {
				continue;
			}
			
			// 방문한 곳을 다시 방문한 경우 무한번 움직이게 됨 -> 종료
			if (visited[nx][ny]) {
				infinite = true;
				return;
			}
		
			// 다음에 이동할 칸의 횟수가 현재 횟수보다 큰 경우 그냥 지나감
			if (dp[nx][ny] > cnt) {
				continue;
			}
			
			visited[nx][ny] = true;
			dfs(cnt + 1, nx, ny);
			visited[nx][ny] = false;
		}
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}

}
