/*
* 문제 접근 아이디어 및 알고리즘 판단 사유
*   - 문제 자체는 직관적이기 때문에 제시한 대로 구현을 하면 되지만, 블럭이 팅겨 나와 범위를 벗어나거나, 최하단에 있는 웜홀에 아래 방향으로 빠져 나오는 경우 등을 고려해야 한다.
*   - 문제를 읽다보니 최대 맵의 크기가 최대 100 * 100이고, 방향은 4방향이기 때문에 최적화 없이 O(N^4) 풀이는 시간 초과가 날 것 같아 최적화 요소를 고민하였다.
*   - 시작 위치와 방향을 정했다면 해당 방향으로 인접한 칸이, 이미 순회를 했는지 보는 것이다. 해당 칸이 블럭이거나 범위를 벗어난 칸이면 이 위치에서 시뮬레이션을 한 적이 없다는 것을 의미한다.
*   - 이와 같은 방법을 적용하여 굉장히 많은 동일한 결과를 도출하는 경우에 대한 중복을 제거할 수 있었다.
* 시간 복잡도
*   - 블록, 웜홀, 블랙홀의 개수에 따라 탐색의 시간이 달라지겠지만, 우선 유일한 시작 위치를 탐색하기 위해 각 방향별로 N * N번 탐색해야 하며, 매 탐색시에는 최악의 경우 N * N번 맵을 순회하게 될 것이다.
*   - 유일한 시작 위치를 찾아 중복되는 경우를 제거하는 것이 굉장히 효과적이기 때문에 O(N^4)보다는 훨씬 적은 시간이 걸린다고 생각한다.
* 실행 시간
*   - 370ms(Java)
* 삽질했던 내용이나 코드
*   - 공이 가장 자리에 닿아 좌표가 범위를 벗어날 경우, 0 또는 n으로 설정을 해주고 while문의 마지막 단에서 매번 좌표에 DELTA 값을 더해주었는데 이렇게 되면 한 시간에 두 칸을 이동한 것이 되므로 올바른 답을 도출할 수 없다.
*   - 또한 이 문제는 블럭에 팅겨 범위를 벗어난 후 다시 범위 안으로 들어오는 경우, 최하단에 있는 웜홀에 아래 방향으로 빠져 나오는 경우 등 고려해야 할 문제가 많기 때문에 한칸 한칸 일일이 따져보는 방식으로 구현하지 않아서
*     무한루프가 도는 문제를 겪었고 이를 해결하느라 시간이 많이 소요됐던 문제였다.
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Solution {
	static final int UP = 0;
	static final int DOWN = 1;
	static final int LEFT = 2;
	static final int RIGHT = 3;
	static final int[][] DELTA = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	static final int BLACK_HOLE = -1;
	static final int BLANK = 0;

	static int n;
	static int[][] reflectDirs = new int[6][4]; // [Type][inDir]
	static int[][] board;
	static int[][][] wormHoles;

	public static void main(String[] args) throws Exception {
		reflectDirs[1][UP] = DOWN;
		reflectDirs[1][DOWN] = RIGHT;
		reflectDirs[1][LEFT] = UP;
		reflectDirs[1][RIGHT] = LEFT;

		reflectDirs[2][UP] = RIGHT;
		reflectDirs[2][DOWN] = UP;
		reflectDirs[2][LEFT] = DOWN;
		reflectDirs[2][RIGHT] = LEFT;

		reflectDirs[3][UP] = LEFT;
		reflectDirs[3][DOWN] = UP;
		reflectDirs[3][LEFT] = RIGHT;
		reflectDirs[3][RIGHT] = DOWN;

		reflectDirs[4][UP] = DOWN;
		reflectDirs[4][DOWN] = LEFT;
		reflectDirs[4][LEFT] = RIGHT;
		reflectDirs[4][RIGHT] = UP;

		reflectDirs[5][UP] = DOWN;
		reflectDirs[5][DOWN] = UP;
		reflectDirs[5][LEFT] = RIGHT;
		reflectDirs[5][RIGHT] = LEFT;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine().trim());

		for (int tc = 1; tc <= t; ++tc) {
			n = Integer.parseInt(br.readLine().trim());
			board = new int[n][n];
			wormHoles = new int[n][n][2];

			HashMap<Integer, int[]> hashMap = new HashMap<>();

			for (int r = 0; r < n; ++r) {
				StringTokenizer st = new StringTokenizer(br.readLine().trim());

				for (int c = 0; c < n; ++c) {
					board[r][c] = Integer.parseInt(st.nextToken());

					if (board[r][c] >= 6) {
						if (hashMap.containsKey(board[r][c])) {
							int[] other = hashMap.get(board[r][c]);

							wormHoles[r][c][0] = other[0];
							wormHoles[r][c][1] = other[1];
							wormHoles[other[0]][other[1]][0] = r;
							wormHoles[other[0]][other[1]][1] = c;
						} else {
							hashMap.put(board[r][c], new int[] { r, c });
						}
					}
				}
			}

			int answer = 0;
			boolean[][][] isVisited = new boolean[4][n][n];

			// 1. 진행 방향을 위로 시작
			for (int r = 0; r < n; ++r) {
				for (int c = 0; c < n; ++c) {
					if (board[r][c] == BLANK) {
						isVisited[0][r][c] = true;

						if ((r > 0) && (isVisited[0][r - 1][c])) {
							continue;
						}

						answer = Math.max(answer, simulate(r, c, UP));
					}
				}
			}

			// 2. 진행 방향을 아래로 시작
			for (int r = n - 1; r >= 0; --r) {
				for (int c = 0; c < n; ++c) {
					if (board[r][c] == BLANK) {
						isVisited[1][r][c] = true;

						if ((r < n - 1) && (isVisited[1][r + 1][c])) {
							continue;
						}

						answer = Math.max(answer, simulate(r, c, DOWN));
					}
				}
			}

			// 3. 진행 방향을 왼쪽으로 시작
			for (int c = 0; c < n; ++c) {
				for (int r = 0; r < n; ++r) {
					if (board[r][c] == BLANK) {
						isVisited[2][r][c] = true;

						if ((c > 0) && (isVisited[2][r][c - 1])) {
							continue;
						}

						answer = Math.max(answer, simulate(r, c, LEFT));
					}
				}
			}

			// 4. 진행 방향을 오른쪽으로 시작
			for (int c = n - 1; c >= 0; --c) {
				for (int r = 0; r < n; ++r) {
					if (board[r][c] == BLANK) {
						isVisited[3][r][c] = true;

						if ((c < n - 1) && (isVisited[3][r][c + 1])) {
							continue;
						}

						answer = Math.max(answer, simulate(r, c, RIGHT));
					}
				}
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	private static int simulate(int sr, int sc, int initDir) {
		int cnt = 0;
		int r = sr, c = sc, dir = initDir;

		while (true) {
			if (r < 0) {
				r = 0;
				dir = DOWN;
				++cnt;
			} else if (r >= n) {
				r = n - 1;
				dir = UP;
				++cnt;
			} else if (c < 0) {
				c = 0;
				dir = RIGHT;
				++cnt;
			} else if (c >= n) {
				c = n - 1;
				dir = LEFT;
				++cnt;
			} else if ((1 <= board[r][c]) && (board[r][c] <= 5)) {
				dir = reflectDirs[board[r][c]][dir];
				r += DELTA[dir][0];
				c += DELTA[dir][1];
				++cnt;
			} else if (board[r][c] >= 6) {
				int tmp = r;

				r = wormHoles[tmp][c][0] + DELTA[dir][0];
				c = wormHoles[tmp][c][1] + DELTA[dir][1];
			} else {
				r += DELTA[dir][0];
				c += DELTA[dir][1];
			}

			if ((r < 0) || (r >= n) || (c < 0) || (c >= n)) {
				continue;
			} else if ((r == sr) && (c == sc) || (board[r][c] == BLACK_HOLE)) {
				break;
			}
		}

		return cnt;
	}
}
