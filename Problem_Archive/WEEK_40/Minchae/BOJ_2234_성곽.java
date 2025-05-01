import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static class Pair {
		int x;
		int y;
		
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	// 좌상우하
	static int[] dx = {0, -1, 0, 1};
	static int[] dy = {-1, 0, 1, 0};
	
	static int[] dir = {1, 2, 4, 8};
	
	static int N, M;
	static int[][] map;
	static boolean[][] visited;
	
	static HashMap<Integer, Integer> hm = new HashMap<>(); // 방 번호, 방 크기 저장

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[M][N];
		visited = new boolean[M][N];
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int roomCnt = 0;
		int maxRoom = 0;
		
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				if (!visited[i][j]) {
					roomCnt++;
					
					int size = bfs(i, j, roomCnt);
					
					hm.put(roomCnt, size);
					maxRoom = Math.max(maxRoom, size);
				}
			}
		}
		
		int maxBroken = breakWall();
		
		System.out.println(roomCnt);
		System.out.println(maxRoom);
		System.out.println(maxBroken);
	}
	
	private static int bfs(int x, int y, int num) {
		int size = 1;
		
		Queue<Pair> q = new LinkedList<>();
		
		q.add(new Pair(x, y));
		visited[x][y] = true;
		
		while (!q.isEmpty()) {
			Pair cur = q.poll();
			
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				if (!isRange(nx, ny) || visited[nx][ny]) {
					continue;
				}
				
				// 현재 칸의 특정 방향이 벽이 아닌 경우에만 다음 칸을 넣어줌
				if ((map[cur.x][cur.y] & dir[i]) == 0) {
					q.add(new Pair(nx, ny));
					visited[nx][ny] = true;
					
					size++;
				}
			}
			
			map[cur.x][cur.y] = num; // 벽을 허물 때 다른 방과 구분하기 위해 방 번호를 넣어줌
		}
		
		return size;
	}
	
	private static int breakWall() {
		int max = 0;
		
		for (int i = 0; i < M; i++) {
			for (int j = 0; j < N; j++) {
				for (int d = 0; d < 4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					
					if (!isRange(nx, ny)) {
						continue;
					}
					
					// 현재 방 번호와 다른 경우
					if (map[nx][ny] != map[i][j]) {
						max = Math.max(max, hm.get(map[nx][ny]) + hm.get(map[i][j]));
					}
				}
			}
		}
		
		return max;
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < M && y >= 0 && y < N;
	}

}
