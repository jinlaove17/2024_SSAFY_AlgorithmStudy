import java.util.*;
import java.io.*;

public class Main {
    static int C, N;
    static int MAX = 100*1000+1;
    static int dp[] = new int[2001];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        // dp table MAX로 초기화
        for(int i=0; i<2001; i++){
            dp[i] = MAX;
        }

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());
            int num = Integer.parseInt(st.nextToken());

            //1차적으로 입력에 대한 dp table 갱신
            fill_dp(cost, num);
        }

        //2차적으로 이중 for문을 통해 dp 값 최종 갱신
        for(int i=1; i <= 2000; i++){
            for(int j=1; j<i; j++){
                dp[i] = Math.min(dp[i], dp[i-j]+dp[j]);
            }
        }

        //C 이상부터 끝까지 돌면서 최소 찾기
        int ret = MAX;
        for(int i=C; i < 2001; i++){
            ret = Math.min(dp[i], ret);
        }
        System.out.println(ret);
    }

    public static void fill_dp(int cost, int num){
        // dp 초기화
        for(int i=1; i <= num; i++){
            dp[i] = Math.min(dp[i], cost);
        }

        int idx = 2;
        while (num*idx <= C){
            dp[num*idx] = Math.min(dp[num*idx], cost*idx);

            // 이전 인덱스부터 현재 자신의 인덱스까지 dp값 갱신
            for(int i=num*(idx-1); i <= num*idx; i++){
                dp[i] = Math.min(dp[i], cost*idx);
            }

            idx++;
        }
    }
}
