import java.io.*;
import java.util.*;

// 순서대로 케익을 줘야해서 처음에 시뮬레이션인줄 알았는데
// min value를 구하는거라 돌고돌아 dp로 가버림

public class Main {
    private static final int[] dx = {0, 0, 0, -1, 1};
    private static final int[] dy = {0, -1, 1, 0, 0};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] customerX = new int[N];
        int[] customerY = new int[N];
        
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        long startX = Long.parseLong(st.nextToken());
        long startY = Long.parseLong(st.nextToken());

        for (int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            customerX[i] = Integer.parseInt(st.nextToken());
            customerY[i] = Integer.parseInt(st.nextToken());
        }
        
        long[][] dp = new long[N][5];
        long answer = solve(N, startX, startY, customerX, customerY, dp);
        System.out.println(answer);
        br.close();
    }
    
    private static long solve(int N,
                              long startX,
                              long startY,
                              int[] customerX,
                              int[] customerY,
                              long[][] dp) {
        long INF = Long.MAX_VALUE/2;
        for (int i=0; i<N; i++) Arrays.fill(dp[i], INF);
        
        // init dp table
        for (int i=0; i<5; i++){
            long nx = customerX[0] + dx[i];
            long ny = customerY[0] + dy[i];
            dp[0][i] = Math.abs(startX-nx) + Math.abs(startY-ny);
        }
        
        for(int i=1; i<N; i++){
            for(int j=0; j<5; j++){
                long cx = customerX[i] + dx[j];
                long cy = customerY[i] + dy[j];
                
                for(int k=0; k<5; k++){
                    long px = customerX[i-1] + dx[k];
                    long py = customerY[i-1] + dy[k];
                    long dist = Math.abs(cx-px) + Math.abs(cy-py);
                    dp[i][j] = Math.min(dp[i][j], dp[i-1][k] + dist);
                }
            }
        }
        
        long ans = INF;
        for (int i=0; i<5; i++) ans = Math.min(ans, dp[N-1][i]);
        return ans;
    }
}
