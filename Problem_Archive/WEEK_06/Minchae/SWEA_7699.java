/**
 * D4 7699. 수지의 수지 맞는 여행
 * 
 * @author minchae
 * @date 2024. 8. 26.
 * */

import java.util.*;
import java.io.*;

public class Solution {
	
	// 상하좌우
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int R, C;
	
	static char[][] map;
	static boolean[] visited;
	
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			
			map = new char[R][C];
			
			for (int i = 0; i < R; i++) {
				map[i] = br.readLine().toCharArray();
			}
			
			visited = new boolean[26];
			
			answer = 0;
			
			dfs(0, 0, 1);
			
			System.out.println("#" + t + " " + answer);
		}
	}
	
	private static void dfs(int x, int y, int cnt) {
		visited[map[x][y] - 'A'] = true;
		
		// 알파벳 총 개수가 볼 수 있는 명물의 최대 개수이기 때문에 26을 넘어가면 바로 종료
		if (answer >= 26) {
			return;
		}
		
		answer = Math.max(answer, cnt);
		
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if (!isRange(nx, ny) || visited[map[nx][ny] - 'A']) {
				continue;
			}
			
			dfs(nx, ny, cnt + 1);
		}
		
		visited[map[x][y] - 'A'] = false;
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < R && y >= 0 && y < C;
	}

}
