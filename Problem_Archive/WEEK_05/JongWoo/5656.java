import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Bomb {
	int r;
	int c;
	int power;

	Bomb(int r, int c, int power) {
		this.r = r;
		this.c = c;
		this.power = power;
	}
}

public class Solution {
	static final int dx[] = { -1, 1, 0, 0 };
	static final int dy[] = { 0, 0, -1, 1 };

	static int n, w, h; // n: 떨어뜨릴 벽돌의 개수, w: 맵의 가로 크기, h: 맵의 세로 크기
	static int answer;
	static int[][] board;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine().trim());

		for (int tc = 1; tc <= t; ++tc) {
			StringTokenizer st = new StringTokenizer(br.readLine().trim());

			n = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());
			board = new int[h][w];

			int[][] backBoard = new int[h][w];

			for (int r = 0; r < h; ++r) {
				st = new StringTokenizer(br.readLine().trim());

				for (int c = 0; c < w; ++c) {
					backBoard[r][c] = board[r][c] = Integer.parseInt(st.nextToken());
				}
			}

			answer = Integer.MAX_VALUE;

			// 폭탄을 떨어뜨릴 구간
			for (int c = 0; c < w; ++c) {
				for (int r = 0; r < h; ++r) {
					if (board[r][c] != 0) {
						simulate(0, r, c);

						// 현재 보드 복원하기
						for (int x = 0; x < h; ++x) {
							for (int y = 0; y < w; ++y) {
								board[x][y] = backBoard[x][y];
							}
						}

						break;
					}
				}
			}

			sb.append(String.format("#%d %d\n", tc, answer));
		}

		System.out.println(sb);
	}

	private static void simulate(int depth, int cr, int cc) {
		if (depth == n) {
			answer = Math.min(answer, getBlockCount());
			return;
		}

		// 벽돌을 터뜨리고 아래로 내리기
		bfs(cr, cc);
		dropBlocks();

		// 현재 보드 백업하기
		int[][] backBoard = new int[h][w];

		for (int r = 0; r < h; ++r) {
			for (int c = 0; c < w; ++c) {
				backBoard[r][c] = board[r][c];
			}
		}

		// 폭탄을 떨어뜨릴 구간
		for (int c = 0; c < w; ++c) {
			for (int r = 0; r < h; ++r) {
				if ((board[r][c] != 0) || (r == h - 1)) {
					simulate(depth + 1, r, c);

					// 현재 보드 복원하기
					for (int x = 0; x < h; ++x) {
						for (int y = 0; y < w; ++y) {
							board[x][y] = backBoard[x][y];
						}
					}

					break;
				}
			}
		}
	}

	private static void bfs(int sr, int sc) {
		Queue<Bomb> q = new LinkedList<>();

		q.add(new Bomb(sr, sc, board[sr][sc]));
		board[sr][sc] = 0;

		while (!q.isEmpty()) {
			Bomb cur = q.poll();

			for (int d = 0; d < 4; ++d) {
				for (int p = 1; p < cur.power; ++p) {
					int nr = cur.r + p * dx[d];
					int nc = cur.c + p * dy[d];

					if ((nr < 0) || (nr >= h) || (nc < 0) || (nc >= w)) {
						continue;
					}

					if (board[nr][nc] == 0) {
						continue;
					}

					q.add(new Bomb(nr, nc, board[nr][nc]));
					board[nr][nc] = 0;
				}
			}
		}
	}

	private static void dropBlocks() {
		int[] tmp = new int[h];

		for (int c = 0; c < w; ++c) {
			int idx = 0;

			for (int r = h - 1; r >= 0; --r) {
				if (board[r][c] == 0) {
					continue;
				}

				tmp[idx++] = board[r][c];
				board[r][c] = 0;
			}

			for (int r = 0, i = h - 1; r < idx; ++r) {
				board[i--][c] = tmp[r];
			}
		}
	}

	private static int getBlockCount() {
		int blockCount = 0;

		for (int r = 0; r < h; ++r) {
			for (int c = 0; c < w; ++c) {
				if (board[r][c] != 0) {
					++blockCount;
				}
			}
		}

		return blockCount;
	}
}
