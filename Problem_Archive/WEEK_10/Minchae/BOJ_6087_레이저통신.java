/**
 * 6087 레이저 통신
 * https://www.acmicpc.net/problem/6087
 * 
 * @author minchae
 * @date 2024. 9. 22.
 * 
 * 레이저의 시작점에서 끝점까지 가는 것이기 때문에 bfs 사용
 * 탐색하면서 방향에 따라 사용한 거울 개수 확인 -> 3차원 배열 사용 (말숭이와 비슷)
 * 거울 개수가 최소일 경우에만 큐에 추가하고 탐색 -> 다익스트라 느낌
 * 
 * 메모리 15840KB  시간 152ms
 * 
 * 시간 복잡도
 * 맵 방문 O(H*W) 우선순위 큐 삽입, 삭제 연산 O(log(H*W))
 * 총 O((H*W)O(log(H*W)))
 */

import java.io.*;
import java.util.*;

public class Main2 {
	
	static class Node implements Comparable<Node> {
		int x;
		int y;
		int dir;
		int cnt;
		
		public Node(int x, int y, int dir, int cnt) {
			this.x = x;
			this.y = y;
			this.dir = dir;
			this.cnt = cnt;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cnt, o.cnt);
		}
	}
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int W, H;
	static char[][] map;
	
	static Node[] laser;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		W = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		map = new char[H][W];
		laser = new Node[2];
		
		int idx = 0;
		
		for (int i = 0; i < H; i++) {
			map[i] = br.readLine().toCharArray();
			
			for (int j = 0; j < W; j++) {
				if (map[i][j] == 'C') {
					laser[idx++] = new Node(i, j, -1, 0); // 레이저는 방향을 -1로 저장'
				}
			}
		}

		int answer = bfs(laser[0]);
		System.out.println(answer);
	}
	
	private static int bfs(Node start) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		int[][][] mirror = new int[4][H][W]; // 4방향마다 필요한 최소 거울 개수 저장
		
		for (int d = 0; d < 4; d++) {
			for (int i = 0; i < H; i++) {
				Arrays.fill(mirror[d][i], Integer.MAX_VALUE); // 일단 최댓값 저장
			}
		}
		
		pq.add(start);
		
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			
			// 도착점에 도착한 경우
			if (cur.x == laser[1].x && cur.y == laser[1].y) {
				return cur.cnt;
			}
			
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				if (!isRange(nx, ny) || map[nx][ny] == '*') {
					continue;
				}
				
				// 현재 방향과 같거나 레이저인 경우 거울 개수 그대로, 아닌 경우 1 증가
				int cnt = cur.dir == i || cur.dir == -1 ? cur.cnt : cur.cnt + 1;
				
				// 거울 개수가 더 작은 경우에만 큐에 추가
				if (cnt < mirror[i][nx][ny]) {
					mirror[i][nx][ny] = cnt;
					pq.add(new Node(nx, ny, i, cnt));
				}
			}
		}
		
		return 0;
	}

	private static boolean isRange(int x, int y) {
		return x >= 0 && x < H && y >= 0 && y < W;
	}
}
