/**
 * 4485 녹색 옷 입은 애가 젤다지?
 * https://www.acmicpc.net/problem/4485
 * 
 * @author minchae
 * @date 2024. 10. 8.
 * 
 * 문제 접근 아이디어 및 알고리즘 판단 사유
 * 	- bfs 사용
 *  - 최소 비용으로 제일 오른쪽 아래 칸으로 움직여야 하기 때문에 우선순위 큐 사용
 * 
 * 시간 복잡도
 * O(logN)
 * 
 * 실행 시간
 * 224 ms
 * */

import java.util.*;
import java.io.*;

public class BOJ_4485 {

	static class Node implements Comparable<Node> {
		int x;
		int y;
		int cost;
		
		public Node(int x, int y, int cost) {
			this.x = x;
			this.y = y;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	// 상하좌우
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int N;
	static int[][] map;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int tc = 1;
		
		while (true) {
			N = Integer.parseInt(br.readLine());
			
			if (N == 0) {
				break;
			}
			
			map = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			int answer = getCost();
			
			System.out.println("Problem " + (tc++) + ": " + answer);
		}
	}
	
	private static int getCost() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		boolean[][] visited = new boolean[N][N];
		
		pq.add(new Node(0, 0, map[0][0]));
		visited[0][0] = true;
		
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if (cur.x == N - 1 && cur.y == N - 1) {
				return cur.cost;
			}
			
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				if (!isRange(nx, ny) || visited[nx][ny]) {
					continue;
				}
				
				pq.add(new Node(nx, ny, cur.cost + map[nx][ny]));
				visited[nx][ny] = true;
			}
		}
		
		return 0;
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}
}
