/**
 * 2146 다리 만들기
 * https://www.acmicpc.net/problem/2146
 *
 * @author minchae
 * @date 2025. 3. 18.
 * 
 * 문제 풀이
 * - 일단 섬 번호 매김 -> bfs나 dfs 이용
 * - 그러고 섬들 둘러보면서 다리 놓아보기 -> bfs 이용
 * - 둘 다 써보고 싶어서 섬 번호는 dfs, 다리 놓는 건 bfs 사용
 * 
 * 시간 복잡도
 * O(N^2)
 * 
 * 실행 시간
 * 1344 ms
 **/

import java.io.*;
import java.util.*;

public class Main2 {
	
	static class Node {
		int x;
		int y;
		int cnt;
		
		public Node(int x, int y, int cnt) {
			this.x = x;
			this.y = y;
			this.cnt = cnt;
		}
	}
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int N;
	
	static int[][] map;
	static boolean[][] visited;
	
	static int num = 1;
	static int answer = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		visited = new boolean[N][N];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visited[i][j] && map[i][j] == 1) {
					dfs(i, j);
					num++; // 섬 하나 발견했으니까 섬 번호 증가
				}
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] > 0) {
					bfs(i, j, map[i][j]);
				}
			}
		}
		
		System.out.println(answer);
	}
	
	private static void dfs(int x, int y) {
		visited[x][y] = true;
		map[x][y] = num;
		
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if (!isRange(nx, ny) || visited[nx][ny] || map[nx][ny] == 0) {
				continue;
			}
			
			dfs(nx, ny);
		}
	}
	
	private static void bfs(int x, int y, int curNum) {
		Queue<Node> q = new LinkedList<>();
		visited = new boolean[N][N];
		
		q.add(new Node(x, y, 0));
		visited[x][y] = true;
		
		while (!q.isEmpty()) {
			Node cur = q.poll();
			
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				if (!isRange(nx, ny) || visited[nx][ny] || map[nx][ny] == curNum) {
					continue;
				}
				
				visited[nx][ny] = true;
				
				if (map[nx][ny] == 0) {
					q.add(new Node(nx, ny, cur.cnt + 1));
				} else {
					answer = Math.min(answer, cur.cnt);
				}
			}
		}
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}

}
