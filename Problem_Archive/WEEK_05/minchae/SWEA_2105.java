/**
 * 2105. 디저트 카페
 * 
 * @author minchae
 * @date 2024. 8. 18.
 */

import java.util.*;
import java.io.*;

public class Solution {
	
	// 우하, 좌하, 좌상, 우상
	static int[] dx = {1, 1, -1, -1};
	static int[] dy = {1, -1, -1, 1};
	
	static int N;
	static int[][] map;
	
	static int sx, sy;
	static boolean[] visited;
	
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			
			map = new int[N][N];
			visited = new boolean[101];
			
			answer = 0;
			
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 사각형이 되기 위한 조건 -> 시작점은 최대 (N - 2)에서 시작해야 사각형을 만들 수 있음
			for (int i = 0; i < N - 2; i++) {
				// 밑에 최소 한 칸이 있어야 하기 때문에 (N - 1)까지
				for (int j = 1; j < N - 1; j++) {
					sx = i;
					sy = j;
					
					visited[map[i][j]] = true;
					dfs(i, j, 0, 1);
					visited[map[i][j]] = false;
				}
			}
			
			System.out.println("#" + t + " " + (answer == 0 ? -1 : answer));
		}

	}
	
	private static void dfs(int x, int y, int dir, int cnt) {
		// 현재 방향부터 반복문 시작 -> 이전 방향으로는 갈 수 없음
		// 특정 칸에 방문했는지 확인하지 않는 이유도 이전 방향으로는 갈 수 없기 때문
		// -> 방문한 곳을 또 방문하는 경우는 없음
		for (int i = dir; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			// 범위를 벗어나면 넘어감
			if (!isRange(nx, ny)) {
				continue;
			}
			
			// 시작점으로 돌아온 경우 && 정상적인 사각형이 만들어진 경우
			if (nx == sx && ny == sy && cnt > 3) {
				answer = Math.max(answer, cnt);
				return;
			}
			
			// 이미 발견했던 디저트 종류인 경우
			if (visited[map[nx][ny]]) {
				continue;
			}
			
			visited[map[nx][ny]] = true;
			dfs(nx, ny, i, cnt + 1);
			visited[map[nx][ny]] = false;
		}
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}

}
