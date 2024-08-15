import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine().trim());
        for(int t=1; t<=T; t++) {
            st = new StringTokenizer(br.readLine().trim());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int[][] map = new int[N][M];
            for(int i=0; i<N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                for(int j=0; j<M; j++) {
                    map[i][j]=Integer.parseInt(st.nextToken());
                }
            }
            int[][][][] dp = new int[N+1][M+1][N+1][M+1];
            for(int[][][] d1 : dp) {
                for(int[][] d2 : d1) {
                    for(int[] d3 : d2) {
                        Arrays.fill(d3, Integer.MAX_VALUE);
                    }
                }
            }
            int result = dfs(0,0,N,M,map,dp);
            sb.append("#"+t+" "+result+"\n");
        }
        System.out.println(sb.toString());
    }

    public static int dfs(int x, int y, int h, int w, int[][] map, int[][][][] dp) {
        if(h==1 && w==1)
            return 0;
        int sum=0;
        for(int i =x; i<x+h; i++) {
            for(int j=y; j<y+w; j++) {
                sum += map[i][j];
            }
        }
        for(int i=1; i<h; i++) {
            if(dp[x][y][i][w] == Integer.MAX_VALUE) {
                dp[x][y][i][w] = dfs(x,y,i,w,map,dp);
            }
            if(dp[x+i][y][h-i][w] == Integer.MAX_VALUE) {
                dp[x+i][y][h-i][w] = dfs(x+i,y,h-i,w,map,dp);
            }
            dp[x][y][h][w] = Math.min(dp[x][y][h][w], sum+dp[x][y][i][w]+dp[x+i][y][h-i][w]);
        }
        for (int i = 1; i < w; i++) {
            if (dp[x][y][h][i] == Integer.MAX_VALUE) {
                dp[x][y][h][i] = dfs(x, y, h, i,map,dp);
            }
            if (dp[x][y + i][h][w - i] == Integer.MAX_VALUE) {
                dp[x][y + i][h][w - i] = dfs(x, y + i, h, w - i,map,dp);
            }
            dp[x][y][h][w] = Math.min(dp[x][y][h][w], sum + dp[x][y][h][i] + dp[x][y + i][h][w - i]);
        }
        return dp[x][y][h][w];
    }
}