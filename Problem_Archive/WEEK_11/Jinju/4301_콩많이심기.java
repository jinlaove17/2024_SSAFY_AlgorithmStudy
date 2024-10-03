/*
    문제 접근
    - 콩을 놓을 수 있는 자리에 일단 다 넣는다
    - 거리가 2인 곳에 콩이 있을 때만 빼준다

    알고리즘: 완탐
    시간 복잡도: O(N*M)
    실행 시간: 200ms

*/
import java.io.*;
import java.util.*;

public class Solution {
    static int R, C, ret;
    static boolean[][] grid; // int 배열 대신 boolean 배열 사용
    static int[] dr = {2, -2, 0, 0};
    static int[] dc = {0, 0, 2, -2};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            C = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());

            grid = new boolean[R][C]; // boolean 배열로 변경
            ret = 0;

            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    // 일단 콩 놓기
                    if (!grid[i][j]) {
                        grid[i][j] = true;
                        ret++;

                        for (int d = 0; d < 4; d++) {
                            int nr = i + dr[d];
                            int nc = j + dc[d];

                            if (nr >= R || nr < 0 || nc >= C || nc < 0) continue;
                            if (grid[nr][nc]) { //2칸 이내에 콩이 존재할 때 빼줘야함
                                grid[i][j] = false;
                                ret--;
                                break; // 한 번만 빼면 되므로 break
                            }
                        }
                    }
                }
            }
            sb.append("#").append(t).append(" ").append(ret).append("\n");
        }
        System.out.println(sb);
    }
}
