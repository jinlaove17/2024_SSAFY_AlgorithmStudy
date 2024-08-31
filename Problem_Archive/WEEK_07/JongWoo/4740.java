import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

class Position {
	int r;
	int c;

	Position(int r, int c) {
		this.r = r;
		this.c = c;
	}
}

public class Solution {
	static final int[][] DELTA = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

	static int n, m, q; // n: 블록의 행 개수, m: 블록의 열 개수, q: 쿼리 수
	static char[][] board;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine().trim());

		for (int tc = 1; tc <= t; ++tc) {
			StringTokenizer st = new StringTokenizer(br.readLine().trim());

			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
			q = Integer.parseInt(st.nextToken());
			board = new char[n][m];

			for (int r = 0; r < n; ++r) {
				String row = br.readLine().trim();

				for (int c = 0; c < m; ++c) {
					board[r][c] = row.charAt(c);
				}
			}

			for (int i = 0; i < q; ++i) {
				st = new StringTokenizer(br.readLine().trim());

				String cmd = st.nextToken();

				switch (cmd) {
				case "U":
					moveUp(st.nextToken());
					break;
				case "D":
					moveDown();
					break;
				case "L":
					moveLeft();
					break;
				case "R":
					moveRight();
					break;
				}

				//printBoard();
			}

			sb.append("#").append(tc).append("\n");

			for (int r = 0; r < n; ++r) {
				for (int c = 0; c < m; ++c) {
					sb.append(board[r][c]);
				}

				sb.append("\n");
			}

			sb.append("\n");
		}

		System.out.println(sb);
	}

	private static void printBoard() {
		System.out.println("==================================");

		for (int r = 0; r < n; ++r) {
			for (int c = 0; c < m; ++c) {
				System.out.print(board[r][c]);
			}

			System.out.println();
		}
	}

	private static void moveUp(String block) {
		// 모든 행에 하나 이상의 블록이 존재하는지 검사한다.
		for (int c = 0; c < m; ++c) {
			if (board[0][c] != '#') {
				return;
			}
		}

		for (int c = 0; c < m; ++c) {
			// 현재 열(c)에 새로 올라온 블럭이 빈 칸('#')일 경우 continue
			if (block.charAt(c) == '#') {
				continue;
			}

			for (int r = 0; r < n - 1; ++r) {
				if (board[r + 1][c] != '#') {
					board[r][c] = board[r + 1][c];
				}
			}

			// 가장 아래 행은 새로 들어온 블록을 추가한다.
			board[n - 1][c] = block.charAt(c);
		}
	}

	private static void moveDown() {
		boolean[][] isVisited = new boolean[n][m];
		List<List<Position>> removed = new ArrayList<>();
		int maxBlockCount = 0;

		for (int r = 0; r < n; ++r) {
			for (int c = 0; c < m; ++c) {
				if (board[r][c] == '#') {
					continue;
				}

				if (isVisited[r][c]) {
					continue;
				}

				List<Position> positions = bfs(r, c, isVisited);

				if (positions.size() > maxBlockCount) {
					removed.clear();
					removed.add(positions);
					maxBlockCount = positions.size();
				} else if (positions.size() == maxBlockCount) {
					removed.add(positions);
				}
			}
		}

		for (List<Position> positions : removed) {
			for (Position p : positions) {
				board[p.r][p.c] = '#';
			}
		}

		// 모든 블록을 아래로 내린다.
		char[] tmpColumn = new char[n];

		for (int c = 0; c < m; ++c) {
			int cnt = 0;

			for (int r = n - 1; r >= 0; --r) {
				if (board[r][c] != '#') {
					tmpColumn[cnt++] = board[r][c];
					board[r][c] = '#';
				}
			}

			if (cnt > 0) {
				for (int r = n - 1; r >= n - cnt; --r) {
					board[r][c] = tmpColumn[n - r - 1];
				}
			}
		}
	}

	private static void moveLeft() {
		char[] tmpRow = new char[m];

		for (int r = 0; r < n; ++r) {
			int cnt = 0;

			for (int c = 0; c < m; ++c) {
				if (board[r][c] != '#') {
					tmpRow[cnt++] = board[r][c];
					board[r][c] = '#';
				}
			}

			for (int c = 0; c < cnt; ++c) {
				board[r][c] = tmpRow[c];
			}
		}
	}

	private static void moveRight() {
		char[] tmpRow = new char[m];

		for (int r = 0; r < n; ++r) {
			int cnt = 0;

			for (int c = m - 1; c >= 0; --c) {
				if (board[r][c] != '#') {
					tmpRow[cnt++] = board[r][c];
					board[r][c] = '#';
				}
			}

			for (int c = m - 1; c >= m - cnt; --c) {
				board[r][c] = tmpRow[m - c - 1];
			}
		}
	}

	private static List<Position> bfs(int sr, int sc, boolean[][] isVisited) {
		List<Position> positions = new ArrayList<>();
		Queue<Position> queue = new LinkedList<>();

		isVisited[sr][sc] = true;
		queue.add(new Position(sr, sc));

		while (!queue.isEmpty()) {
			Position cur = queue.poll();

			positions.add(new Position(cur.r, cur.c));

			for (int dir = 0; dir < DELTA.length; ++dir) {
				int nr = cur.r + DELTA[dir][0];
				int nc = cur.c + DELTA[dir][1];

				// 범위를 벗어났다면 continue
				if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= m)) {
					continue;
				}

				// 다른 색이거나 빈 칸이면 continue
				if (board[nr][nc] != board[cur.r][cur.c]) {
					continue;
				}

				// 이미 방문한 칸이면 continue
				if (isVisited[nr][nc]) {
					continue;
				}

				isVisited[nr][nc] = true;
				queue.add(new Position(nr, nc));
			}
		}

		return positions;
	}
}
