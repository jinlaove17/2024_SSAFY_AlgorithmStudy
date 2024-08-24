import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    static final int[][] delta = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
 
    static int h, w, answer;
    static char[][] board;
    static boolean[] freq;
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine().trim());
 
        for (int tc = 1; tc <= t; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
 
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            board = new char[h][w];
            freq = new boolean[26]; // A ~ Z
 
            for (int r = 0; r < h; ++r) {
                String row = br.readLine().trim();
 
                for (int c = 0; c < w; ++c) {
                    board[r][c] = row.charAt(c);
                }
            }
 
            int sr = 0, sc = 0;
 
            // 최소 1개의 명물은 볼 수 있다.
            answer = 1;
            freq[board[sr][sc] - 'A'] = true;
            dfs(1, sr, sc);
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
 
        System.out.println(sb);
    }
 
    private static void dfs(int depth, int r, int c) {
        // 모든 명물을 본 경우
        if (answer == 26) {
            return;
        }
 
        if (depth > answer) {
            answer = depth;
        }
 
        for (int dir = 0; dir < delta.length; ++dir) {
            int nr = r + delta[dir][0];
            int nc = c + delta[dir][1];
 
            if ((nr < 0) || (nr >= h) || (nc < 0) || (nc >= w)) {
                continue;
            }
 
            if (freq[board[nr][nc] - 'A']) {
                continue;
            }
 
            freq[board[nr][nc] - 'A'] = true;
            dfs(depth + 1, nr, nc);
            freq[board[nr][nc] - 'A'] = false;
        }
    }
}
