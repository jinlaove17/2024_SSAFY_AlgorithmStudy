/**
 * 2159 케익 배달
 * https://www.acmicpc.net/problem/2159
 *
 * @author minchae
 * @date 2025. 7. 30.
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static int[] dx = {0, -1, 1, 0, 0};
	static int[] dy = {0, 0, 0, -1, 1};
	
	static int N;
	
	static int[][] order;
	static long[][] dp;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine());
        
        order = new int[N + 1][2];
        dp = new long[N + 1][5];
        
        for (int i = 0; i <= N; i++) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	order[i][0] = Integer.parseInt(st.nextToken());
        	order[i][1] = Integer.parseInt(st.nextToken());
        }
        
        for (int i = 0; i <= N; i++) {
        	Arrays.fill(dp[i], Long.MAX_VALUE);
        }
        
        dp[0][0] = 0;
        
        // 첫 번째 집까지 이동 거리
        for (int i = 0; i < 5; i++) {
            int nx = order[0][0] + dx[0];
            int ny = order[0][1] + dy[0];
            
            int cx = order[1][0] + dx[i];
            int cy = order[1][1] + dy[i];
            
            dp[1][i] = dp[0][0] + getDist(nx, ny, cx, cy);
        }
        
        for (int i = 2; i <= N; i++) {
        	// 현재 위치 이동
            for (int j = 0; j < 5; j++) {
                int cx = order[i][0] + dx[j];
                int cy = order[i][1] + dy[j];

                // 이전 위치 이동
                for (int k = 0; k < 5; k++) {
                    int px = order[i - 1][0] + dx[k];
                    int py = order[i - 1][1] + dy[k];

                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + getDist(cx, cy, px, py));
                }
            }
        }

        long answer = Long.MAX_VALUE;
        
        for (int i = 0; i < 5; i++) {
        	answer = Math.min(answer, dp[N][i]);
        }
        
        System.out.println(answer);
	}

	private static long getDist(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	
}
