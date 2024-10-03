/*
문제 접근 아이디어 및 알고리즘 판단 사유
	- 처음에 문제를 읽을 때, 두 점 사이의 거리 공식이 주어져서 수학적인 계산이 필요한 문제인줄 알았다.
	- 그러나, 좀만 생각해보면 거리가 딱 2가 되는 경우는 가로로 2칸이나 세로로 2칸 떨어진 위치에 콩을 심는 경우밖에 없다는 것을 알 수 있다.
	- 이 아이디어를 토대로 DP를 떠올렸다. 사실 DP라고 하기도 이상한게 해당 위치에 콩을 심었는지 확인하는 위치로만 사용한다.
	- 콩을 심을 수 있는 위치에 1을 저장하고, 최종적으로 1인 칸의 개수를 구하면 답을 도출할 수 있다.

시간 복잡도
	- M * N 크기의 맵을 순회하며, 콩을 심을 수 있는지 판단해야 하므로 O(M * N)의 시간 복잡도를 갖는다.

실행 시간
	- 154ms(Java)
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    static final int MAX_SIZE = 1_000;
     
    static int n, m;
    static int[][] dp;
     
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine().trim());
         
        for (int tc = 1; tc <= t; ++tc) {
            StringTokenizer st = new StringTokenizer(br.readLine().trim());
             
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            dp = new int[m][n];
             
            for (int r = 0; r < m; ++r) {
                for (int c = 0; c < n; ++c) {
                    if (r >= 2) {
                        if (dp[r - 2][c] == 1) {
                            continue;
                        }
                    }
                     
                    if (c >= 2) {
                        if (dp[r][c - 2] == 1) {
                            continue;
                        }
                    }
                     
                    dp[r][c] = 1;
                }
            }
             
            int answer = 0;
             
            for (int r = 0; r < m; ++r) {
                for (int c = 0; c < n; ++c) {
                    if (dp[r][c] == 1) {
                        ++answer;
                    }
                }
            }
             
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
         
        System.out.println(sb);
    }
}
