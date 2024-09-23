/**
 * 21609 상어 중학교
 * https://www.acmicpc.net/problem/21609
 * 
 * @author minchae
 * @date 2024. 09. 23.
 * 
 * 크기가 가장 큰 블록 그룹 찾고 그 중에서 우선순위 높은 그룹을 찾는다는 것을 보고 bfs, 정렬 or 우선순위 큐를 사용해야 겠다고 생각
 * 각 칸마다 bfs 실행하면서 블록 그룹 찾음 -> bfs 함수에서 주의할 점은 무지개 블록 방문처리를 원복 해주야 함 (무지개는 여러 번 포함 가능하기 때문)
 * 
 * bfs 실행하면서 그룹 리스트를 미리 저장해둠 -> 삭제할 때 다시 bfs를 돌리지 않고 리스트에 저장된 좌표를 꺼내 빈 칸으로 만듦
 * 
 * 중력 작용
 * 맨 위의 행부터 진행 -> -1을 만나면 현재 스택에 저장되었던 블록을 꺼내 -1 위부터 저장
 * 맨 아래 행까지 진행 후 스택에 값이 있다면 맨 밑의 행부터 스택에서 값을 꺼내 채워줌
 * 
 * 메모리 20812KB  시간 204ms
 * 
 * 시간 복잡도
 * 그룹 찾기, 삭제, 중력, 회전 O(N^2)
 * 최악의 경우 while문 안에서 N^2번 반복 가능
 * 총 시간 복잡도 : O(N^4)
 * */

import java.util.*;
import java.io.*;

public class Main2 {

	static class Node implements Comparable<Node> {
		int x;
		int y;
		int size;
		int rainbow;
		ArrayList<Node> list;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Node(int x, int y, int size, int rainbow, ArrayList<Node> list) {
			this.x = x;
			this.y = y;
			this.size = size;
			this.rainbow = rainbow;
			this.list = list;
		}

		@Override
		public int compareTo(Node o) {
			if (this.size != o.size) {
				return Integer.compare(o.size, this.size);
			}

			if (this.rainbow != o.rainbow) {
				return Integer.compare(o.rainbow, this.rainbow);
			}

			if (this.x != o.x) {
				return Integer.compare(o.x, this.x);
			}

			return Integer.compare(o.y, this.y);
		}
	}

	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	static int N, M;
	static int[][] map; // 검은 블록 -1, 무지개 블록 0, 빈 칸 -2

	static PriorityQueue<Node> groups;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][N];

		groups = new PriorityQueue<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int answer = 0;

		

		while (true) {
			findGroup();
			
			if (groups.isEmpty()) {
				break;
			}
			
			Node best = groups.poll();

			answer += Math.pow(best.size, 2); // 점수 획득

			removeGroup(best.list);

			gravity();
			rotate();
			gravity();
		}

		System.out.println(answer);
	}

	// 블록 그룹 찾기
	private static void findGroup() {
		groups.clear();
		boolean[][] visited = new boolean[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visited[i][j] && map[i][j] > 0) {
					bfs(i, j, visited);
				}
			}
		}
	}

	private static void bfs(int x, int y, boolean[][] visited) {
		Queue<Node> q = new LinkedList<>();

		q.add(new Node(x, y));
		visited[x][y] = true;

		ArrayList<Node> list = new ArrayList<>();
		list.add(new Node(x, y));

		int rainbow = 0;

		while (!q.isEmpty()) {
			Node cur = q.poll();

			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];

				if (!isRange(nx, ny) || visited[nx][ny] || map[nx][ny] < 0) {
					continue;
				}

				if (map[nx][ny] == map[x][y] || map[nx][ny] == 0) {
					q.add(new Node(nx, ny));
					visited[nx][ny] = true;

					list.add(new Node(nx, ny));

					if (map[nx][ny] == 0) {
						rainbow++;
					}
				}
			}
		}
		
		if (list.size() >= 2) {
			groups.add(new Node(x, y, list.size(), rainbow, list));
		}

		// 무지개 블록은 방문 처리 다시 false로 설정
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0 && visited[i][j]) {
					visited[i][j] = false;
				}
			}
		}
	}

	// 블록 그룹 삭제
	private static void removeGroup(ArrayList<Node> list) {
		for (Node cur : list) {
			map[cur.x][cur.y] = -2;
		}
	}

	// 중력 작용
	private static void gravity() {
		// 열마다 확인
		for (int j = 0; j < N; j++) {
			// 위의 행부터 확인 -> -1이나 범위를 벗어나기 전까지 블록을 만나면 스택에 저장
			Stack<Integer> stack = new Stack<>();

			for (int i = 0; i < N; i++) {
				if (map[i][j] == -1) {
					int idx = i - 1;

					while (!stack.isEmpty()) {
						map[idx--][j] = stack.pop();
					}
				}

				// 빈 칸, 검은색 블록이 아닐 경우에만 스택에 삽입
				if (map[i][j] >= 0) {
					stack.add(map[i][j]);
					map[i][j] = -2;
				}
			}

			int idx = N - 1;

			while (!stack.isEmpty()) {
				map[idx--][j] = stack.pop();
			}
		}
	}

	// 90도 반시계 방향으로 회전
	private static void rotate() {
		int[][] newMap = new int[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				newMap[N - j - 1][i] = map[i][j];
			}
		}

		map = newMap;
	}

	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}
}
