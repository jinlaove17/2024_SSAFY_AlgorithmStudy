import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    static final int[][] delta = { { -1, 1 }, { 1, 1 }, { 1, -1 }, { -1, -1 } };
 
    static int n, answer;
    static int[][] board;
    static boolean[][] isVisited;
    static boolean[] isSelected = new boolean[100 + 2];
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine().trim());
 
        for (int tc = 1; tc <= t; ++tc) {
            n = Integer.parseInt(br.readLine().trim());
            board = new int[n][n];
            isVisited = new boolean[n][n];
 
            for (int i = 1; i <= 100; ++i) {
                isSelected[i] = false;
            }
 
            for (int r = 0; r < n; ++r) {
                StringTokenizer st = new StringTokenizer(br.readLine().trim());
 
                for (int c = 0; c < n; ++c) {
                    board[r][c] = Integer.parseInt(st.nextToken());
                }
            }
 
            // 디저트를 먹을 수 없는 경우 정답은 -1이다.
            // 최댓값을 구하는 문제이므로 초기값을 -1로 설정해도 무방하다.
            answer = -1;
 
            int[] dirCount = new int[4];
 
            for (int r = 1, rEnd = n - 1; r < rEnd; ++r) {
                for (int c = 0, cEnd = n - 2; c < cEnd; ++c) {
                    isVisited[r][c] = true;
                    isSelected[board[r][c]] = true;
                    dfs(1, r, c, r, c, 0, dirCount);
                    isVisited[r][c] = false;
                    isSelected[board[r][c]] = false;
                }
            }
 
            sb.append(String.format("#%d %d\n", tc, answer));
        }
 
        System.out.println(sb);
    }
 
    // 사각형이 되려면 시작 방향에서 총 3번을 꺾어 시작점으로 돌아와야 한다.
    private static void dfs(int depth, int sr, int sc, int r, int c, int dir, int[] dirCount) {
        for (int d = dir; d < delta.length; ++d) {
            int nr = r + delta[d][0];
            int nc = c + delta[d][1];
 
            if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= n)) {
                // 현재 방향으로 갔던 이력이 있다면 방향을 바꿔서 시도해본다.
                if (dirCount[d] >= 1) {
                    continue;
                }
 
                return;
            }
 
            if ((isVisited[nr][nc]) || (isSelected[board[nr][nc]])) {
                if ((nr == sr) && (nc == sc)) {
                    answer = Math.max(answer, depth);
                    return;
                }
 
                // 현재 방향으로 갔던 이력이 있다면 방향을 바꿔서 시도해본다.
                if (dirCount[d] >= 1) {
                    continue;
                }
 
                return;
            }
 
            isVisited[nr][nc] = true;
            isSelected[board[nr][nc]] = true;
            ++dirCount[d];
            dfs(depth + 1, sr, sc, nr, nc, d, dirCount);
            isVisited[nr][nc] = false;
            isSelected[board[nr][nc]] = false;
            --dirCount[d];
 
            if (dirCount[d] < 1) {
                break;
            }
        }
    }
}
