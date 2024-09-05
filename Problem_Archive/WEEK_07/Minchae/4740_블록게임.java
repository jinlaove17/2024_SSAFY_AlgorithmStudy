/**
 * 4740. 밍이의 블록게임
 * 
 * @author minchae
 * @date 2024. 9. 4.
 * */

import java.util.*;
import java.io.*;

public class Solution {

	static class Pair {
		int x;
		int y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	// 상하좌우
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	static int N, M, Q;
	static char[][] map; // ‘R’, ‘Y’, ‘G’, ‘B’, ‘P’, 블록이 존재하지 않는 칸은 ‘#’

	static HashMap<Integer, Queue<Pair>> blockMap = new HashMap<>();
	static boolean[][] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			Q = Integer.parseInt(st.nextToken());

			map = new char[N][M];

			for (int i = 0; i < N; i++) {
				map[i] = br.readLine().toCharArray();
			}

			while (Q-- > 0) {
				st = new StringTokenizer(br.readLine());

				String step = st.nextToken();

				switch (step) {
				case "U":
					up(st.nextToken().toCharArray());
					break;
				case "L":
					left();
					break;
				case "R":
					right();
					break;
				case "D":
					down();
					break;
				}
			}

			System.out.println("#" + t);

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					System.out.print(map[i][j]);
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	private static void up(char[] add) {
		// 특정 열에 빈 공간이 없을 경우 추가 X
		for (int j = 0; j < M; j++) {
			int cnt = 0;

			for (int i = 0; i < N; i++) {
				if (map[i][j] == '#') {
					cnt++;
				}
			}

			// 빈 칸이 없을 경우 종료
			if (cnt == 0) {
				return;
			}
		}

		// 모든 열의 블록 한 칸씩 위로 올리기
		for (int j = 0; j < M; j++) {
			for (int i = 0; i < N - 1; i++) {
				map[i][j] = map[i + 1][j];
			}

			map[N - 1][j] = add[j];
		}

		// 빈 공간 확인하고 아래로 내리기
		check();
	}

	// 각 열마다 빈 공간 확인하고 밑으로 내리기
	private static void check() {
		// 열마다 확인 -> 스택 이용해서 블록 가장 아래로 내리기
		Stack<Character> s = new Stack<>();
		
		for (int j = 0; j < M; j++) {
			for (int i = 0; i < N; i++) {
				// 빈 공간이 아닐 때만 스택에 추가하고 빈 칸으로 만듦
				if (map[i][j] != '#') {
					s.push(map[i][j]);
					map[i][j] = '#';
				}
			}

			// 가장 밑부터 채우기
			int idx = N - 1;

			while (!s.isEmpty()) {
				map[idx--][j] = s.pop();
			}
		}
	}

	// 모든 블록이 왼쪽으로 이동
	private static void left() {
		// 행마다 확인
		Stack<Character> s = new Stack<>();
		for (int i = 0; i < N; i++) {
			// 오른쪽 열부터 확인하고 빈 칸이 아닐 때만 스택에 추가하고 빈 칸으로 만듦
			for (int j = M - 1; j >= 0; j--) {
				if (map[i][j] != '#') {
					s.push(map[i][j]);
					map[i][j] = '#';
				}
			}

			// 왼쪽부터 다시 채움
			int idx = 0;

			while (!s.isEmpty()) {
				map[i][idx++] = s.pop();
			}
		}
	}

	// 모든 블록이 오른쪽으로 이동
	private static void right() {
		// 행마다 확인
		Stack<Character> s = new Stack<>();
		
		for (int i = 0; i < N; i++) {
			// 왼쪽 열부터 확인하고 빈 칸이 아닐 때만 스택에 추가하고 빈 칸으로 만듦
			for (int j = 0; j < M; j++) {
				if (map[i][j] != '#') {
					s.push(map[i][j]);
					map[i][j] = '#';
				}
			}

			int idx = M - 1;

			while (!s.isEmpty()) {
				map[i][idx--] = s.pop();
			}
		}
	}

	// 블록 그룹 삭제하고 아래로 내리기
	private static void down() {
		blockMap.clear();
		visited = new boolean[N][M];

		int max = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (!visited[i][j]) {
					max = Math.max(max, bfs(i, j));
				}
			}
		}

		// 최대 블록 개수를 가진 모든 블록 개수 삭제
		remove(blockMap.get(max));

		// 빈 공간 없이 아래로 내림
		check();
	}

	// 블록 그룹 삭제
	private static void remove(Queue<Pair> q) {
		while (!q.isEmpty()) {
			Pair cur = q.poll();

			map[cur.x][cur.y] = '#';
		}
	}

	// 특정 블록 그룹의 개수 세기
	private static int bfs(int x, int y) {
		Queue<Pair> q = new LinkedList<>();

		q.add(new Pair(x, y));
		visited[x][y] = true;

		int cnt = 1;

		ArrayList<Pair> list = new ArrayList<>();
		list.add(new Pair(x, y));

		while (!q.isEmpty()) {
			Pair cur = q.poll();

			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];

				if (!isRange(nx, ny) || visited[nx][ny]) {
					continue;
				}

				if (map[nx][ny] == '#' || map[nx][ny] != map[x][y]) {
					continue;
				}

				q.add(new Pair(nx, ny));
				visited[nx][ny] = true;

				list.add(new Pair(nx, ny));
				cnt++;
			}
		}

		if (!blockMap.containsKey(cnt)) {
			blockMap.put(cnt, new LinkedList<>());
		}

		blockMap.get(cnt).addAll(list);

		return cnt;
	}

	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}
}
