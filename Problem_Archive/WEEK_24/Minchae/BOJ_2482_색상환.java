/**
 * 2482 색상환
 * https://www.acmicpc.net/problem/2482
 * 
 * @author minchae
 * @date 2025. 1. 1.
 *
 * 문제 풀이
 *  - dp 2차원 배열 사용
 *  
 *  - dp[i][j] -> i개의 색이 있을 때 j개의 색을 선택하는 경우의 수
 *    => i번째 색을 선택하는 경우 + i번째 색을 선택하지 않는 경우의 수를 구하면 됨
 *  - i번째 색을 선택하는 경우에는 (i - 1)번째는 선택하지 못하기 때문에 dp[i - 2][k - 1]
 *  - i번째 색을 선택하지 않는 경우에는 (i - 1)번째를 선택할 수 있기 때문에 dp[i - 1][k]
 *  
 *  - 마지막 답을 출력할 때 생각할 점 -> N번째 색을 고른다면 첫 번째 색을 고를 수 없기 때문에 dp[N - 3][K - 1]을 사용해야 함
 *
 * 시간 복잡도
 * O(N * K)
 *
 * 실행 시간
 * 124 ms
 **/

import java.util.*;
import java.io.*;

public class Main {
	
	static final int MOD = 1_000_000_003;
	
	static int N, K;
	static int[][] dp;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());
        
        dp = new int[N + 1][K + 1];
        
        for (int i = 1; i <= N; i++) {
        	dp[i][0] = 1;
        	dp[i][1] = i;
        }
        
        for (int i = 2; i <= N; i++) {
        	for (int j = 2; j <= K; j++) {
        		dp[i][j] = (dp[i - 2][j - 1] + dp[i - 1][j]) % MOD;
        	}
        }

        System.out.println((dp[N - 3][K - 1] + dp[N - 1][K]) % MOD);
	}

}
