/*
 * 문제 접근 아이디어 및 알고리즘 판단 사유
 * 		- 각 칸마다 인접한 8칸에 지뢰가 몇개 있는지를 저장한 다음, BFS 알고리즘을 사용하여 해결할 수 있을 것이라고 생각했다.
 * 		  BFS 순회 시에는 인접 칸의 인접 지뢰 개수가 0일 때만 큐에 넣어주고 그 외의 칸은 방문 여부만 true로 만들어주었다.
 *		  처음에는 맵을 순회하며 인접 지뢰 개수가 0인 칸에 대해서 BFS를 수행한 다음, 지뢰가 아니면서 방문하지 않은 칸의 개수를 세주는 식으로 문제를 해결할 수 있었다. 
 * 시간 복잡도
 * 		- 이 코드는 N x N 크기의 맵을 순회하며 BFS를 돌아 최종적으로 N x N 맵을 순회한다.
 *		- 따라서 O(N^2)의 시간 복잡도를 갖는다.
 * 실행 시간
 * 		- 229ms(Java)
 * 개선 방법
 * 		- 우선순위 큐 -> 2중 for문(인접 지뢰가 0인 칸에 대한 BFS 이후 방문하지 않은 지뢰가 아닌 칸 계산) 
 * 삽질했던 내용이나 코드
 * 		- 처음에는 인접 지뢰 개수가 0인 칸들을 우선적으로 순회해야 된다고 생각했기 때문에 인접 지뢰 개수가 적은 것을 기준으로 좌표를 저장하는 최소힙(우선순위 큐)을 선언하여 해결하였다.
 * 		- 또한 문제를 잘못 이해한 것이 선택한 칸이 지뢰가 아닌 경우 인접 8칸에 지뢰가 한개도 없는 경우에만 주위 8칸이 열리게 된다는 것이다.
 * 		- 나는 선택한 칸이 지뢰가 아닌 경우 인접 8칸의 지뢰의 개수와 상관없이 8칸이 열리게 된다고 생각했었다.
 * 		- 이를 꺠닫고 인접 지뢰가 0인 칸에 대해 모든 순회를 한 후, 지뢰가 아니면서 방문하지 않은 칸의 개수를 세주면 된다는 것을 알게 되었다.
 * 		- 이와 같은 풀이를 적용하여 실행 시간을 422ms에서 229ms까지 줄일 수 있었다.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class Solution {
	static final int[][] DELTA = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

	static int n;
	static char[][] board;
	static int[][] nearCount;
	static boolean[][] isVisited;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int t = Integer.parseInt(br.readLine().trim());

		for (int tc = 1; tc <= t; ++tc) {
			n = Integer.parseInt(br.readLine().trim());
			board = new char[n][n];
			nearCount = new int[n][n];
			isVisited = new boolean[n][n];

			for (int r = 0; r < n; ++r) {
				String row = br.readLine().trim();

				for (int c = 0; c < n; ++c) {
					board[r][c] = row.charAt(c);
				}
			}

			for (int r = 0; r < n; ++r) {
				for (int c = 0; c < n; ++c) {
					if (board[r][c] == '*') {
						continue;
					}

					for (int dir = 0; dir < DELTA.length; ++dir) {
						int nr = r + DELTA[dir][0];
						int nc = c + DELTA[dir][1];

						if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= n)) {
							continue;
						}

						if (board[nr][nc] == '*') {
							++nearCount[r][c];
						}
					}
				}
			}

			int answer = 0;

			for (int r = 0; r < n; ++r) {
				for (int c = 0; c < n; ++c) {
					if ((board[r][c] == '*') || (nearCount[r][c] != 0) || (isVisited[r][c])) {
						continue;
					}

					bfs(r, c);
					++answer;
				}
			}

			for (int r = 0; r < n; ++r) {
				for (int c = 0; c < n; ++c) {
					if ((board[r][c] == '*') || (isVisited[r][c])) {
						continue;
					}

					++answer;
				}
			}

			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		System.out.println(sb);
	}

	private static void bfs(int sr, int sc) {
		Queue<int[]> q = new ArrayDeque<>();

		isVisited[sr][sc] = true;
		q.add(new int[] { sr, sc });

		while (!q.isEmpty()) {
			int[] curPos = q.poll();

			for (int dir = 0; dir < DELTA.length; ++dir) {
				int nr = curPos[0] + DELTA[dir][0];
				int nc = curPos[1] + DELTA[dir][1];

				if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= n)) {
					continue;
				}

				if ((board[nr][nc] == '*') || (isVisited[nr][nc])) {
					continue;
				}

				isVisited[nr][nc] = true;

				if (nearCount[nr][nc] == 0) {
					q.add(new int[] { nr, nc });
				}
			}
		}
	}
}
