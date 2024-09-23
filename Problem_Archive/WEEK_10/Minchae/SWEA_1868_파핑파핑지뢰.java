/**
 * D4 1868. 파핑파핑 지뢰찾기
 * 
 * @author Minchae
 * @date 2024. 9. 22.
 * 
 * 지뢰가 없는 칸에서 8방향을 계속 확인하면서 지뢰가 없는지 확인하는 것이기 때문에 bfs 사용
 * 1. 맨 처음 주변에 지뢰가 몇 개 있는지 확인하고 맵 세팅
 * 2. 주변에 지뢰가 없는 칸에서 bfs 시작 -> 클릭 개수 증가
 * 3. 탐색을 다 했는데 지뢰가 아니고 아직 방문하지 않는 곳이 남은 경우 클릭 개수 증가
 * 
 * 메모리 39,300 kb  시간 213 ms
 * 
 * 시간 복잡도 : 근처 지뢰 개수 세는 부분, bfs 탐색 둘 다 O(N^2)
 */

import java.io.*;
import java.util.*;

public class Solution {
	
	static class Pair {
		int x;
		int y;
		
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	// 8방향
	static int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
	static int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};
	
	static int N;
	static int[][] map; // 지뢰 -1, 주변에 지뢰 없으면 0, 근처에 있는 지뢰 개수
	static boolean[][] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			
			map = new int[N][N];
			visited = new boolean[N][N];
			
			for (int i = 0; i < N; i++) {
				String input = br.readLine();
				
				for (int j = 0; j < N; j++) {
					map[i][j] = input.charAt(j) == '*' ? -1 : 0;
				}
			}
			
			// 주변 지뢰 개수 확인하고 맵에 저장
			setMap();
			
			int answer = 0; // 클릭 개수
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// 주변에 지뢰가 없고 아직 방문하지 ㅇ낳은 칸인 경우
					if (map[i][j] == 0 && !visited[i][j]) {
						bfs(i, j);
						answer++;
					}
				}
			}
			
			// 다 탐색했는데도 아직 남은 칸이 있는 경우
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// 지뢰가 아닌 칸 && 아직 방문하지 않은 경우
					if (map[i][j] > 0 && !visited[i][j]) {
						answer++;
					}
				}
			}
			
			System.out.println("#" + t + " " + answer);
		}
	}
	
	// 지뢰를 발견하면 주변 8방향에 지뢰 개수 증가
	private static void setMap() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// 지뢰가 아니면 넘어감
				if (map[i][j] != -1) {
					continue;
				}
				
				for (int d = 0; d < 8; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					
					// 범위를 벗어나거나 지뢰인 경우 넘어감
					if (!isRange(nx, ny) || map[nx][ny] == -1) {
						continue;
					}
					
					map[nx][ny]++;
				}
			}
		}
	}
	
	private static void bfs(int x, int y) {
		Queue<Pair> q = new LinkedList<>();
		
		q.add(new Pair(x, y));
		visited[x][y] = true;
		
		while (!q.isEmpty()) {
			Pair cur = q.poll();
			
			for (int i = 0; i < 8; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				if (!isRange(nx, ny) || visited[nx][ny] || map[nx][ny] == -1) {
					continue;
				}
				
				visited[nx][ny] = true;
				
				// 인접한 칸의 주변에 지뢰가 없는 경우 그 칸에서 또 탐색할 수 있도록 큐에 추가
				if (map[nx][ny] == 0) {
					q.add(new Pair(nx, ny));
				}
			}
		}
	}

	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}
}
