/**
 * 22255 호석사우루스
 * https://www.acmicpc.net/problem/22255
 *
 * @author minchae
 * @date 2025. 3. 18.
 * 
 * 문제 풀이
 * - bfs 이용하면 되겠다고 생각. 그런데 충격량이 최소가 되어야 하니까 우선순위큐 사용
 * - 방문체크도 2차원 배열로 하면 안됨 -> 턴수마다 방문하는 곳이 다르기 때문에 3차원 배열 사용
 * 
 * 시간 복잡도
 * O(NMlog(N * M))
 * 
 * 실행 시간
 * 184 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static class Node implements Comparable<Node> {
		int x;
		int y;
		int dmg;
		int cnt;
		
		public Node(int x, int y, int dmg, int cnt) {
			this.x = x;
			this.y = y;
			this.dmg = dmg;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Node o) {
			return this.dmg - o.dmg;
		}
	}
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int N, M;
	static int sx, sy, ex, ey;
	
	static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		
		st = new StringTokenizer(br.readLine());
		
		sx = Integer.parseInt(st.nextToken()) - 1;
		sy = Integer.parseInt(st.nextToken()) - 1;
		ex = Integer.parseInt(st.nextToken()) - 1;
		ey = Integer.parseInt(st.nextToken()) - 1;
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		System.out.println(find());
	}
	
	private static int find() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		boolean[][][] visited = new boolean[3][N][M];
		
		pq.add(new Node(sx, sy, 0, 1)); // 최초의 이동이 1번째니까 cnt를 1로 넣어줌
		
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if (cur.x == ex && cur.y == ey) {
				return cur.dmg;
			}
			
			int turn = cur.cnt % 3;
			
			for (int i = 0; i < 4; i++) {
				// 3K+1번째 이동 시에는 상하로 인접한 곳 중 한 칸으로 이동
				if (turn == 1 && (i == 2 || i == 3)) {
					continue;
				}
				
				//  3K+2번째 이동 시에는 좌우로 인접한 곳 중 한 칸으로 이동
				if (turn == 2 && (i == 0 || i == 1)) {
					continue;
				}
				
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				// 범위 벗어나거나, 이미 방문했거나, 벽인 경우
				if (!isRange(nx, ny) || visited[turn][nx][ny] || map[nx][ny] == -1) {
					continue;
				}
				
				pq.add(new Node(nx, ny, cur.dmg + map[nx][ny], cur.cnt + 1));
				visited[turn][nx][ny] = true;
			}
		}

		return -1;
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}

}
