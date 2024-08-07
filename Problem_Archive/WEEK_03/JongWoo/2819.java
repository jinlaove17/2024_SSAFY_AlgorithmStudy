import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		int N = 4;
		int[] dx = { -1, 1, 0, 0 };
		int[] dy = { 0, 0, -1, 1 };

		for (int test_case = 1; test_case <= T; ++test_case) {
			char[][] board = new char[N][N];
			Set<String> set = new HashSet<>();

			for (int i = 0; i < N; ++i) {
				StringTokenizer st = new StringTokenizer(br.readLine());

				for (int j = 0; j < N; ++j) {
					board[i][j] = (char) (Integer.parseInt(st.nextToken()) + '0');
				}
			}

			for (int i = 0; i < N; ++i) {
				for (int j = 0; j < N; ++j) {
					DFS(N, i, j, new StringBuilder(), board, dx, dy, set);
				}
			}

			sb.append(String.format("#%d %d\n", test_case, set.size()));
		}

		System.out.println(sb.toString());
	}

	private static void DFS(int N, int x, int y, StringBuilder cur, char[][] board, int[] dx, int[] dy, Set<String> set) {
		if (cur.length() >= 7) {
			set.add(cur.toString());
			return;
		}

		for (int i = 0; i < 4; ++i) {
			int nx = x + dx[i];
			int ny = y + dy[i];

			if ((nx < 0) || (nx >= N) || (ny < 0) || (ny >= N)) {
				continue;
			}

			cur.append(board[nx][ny]);
			DFS(N, nx, ny, cur, board, dx, dy, set);
			cur.setLength(cur.length() - 1);
		}
	}
}
