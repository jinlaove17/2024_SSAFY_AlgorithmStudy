/**
 * 1495 기타리스트
 * https://www.acmicpc.net/problem/1495
 *
 * @author minchae
 * @date 2024. 11. 19.
 *
 * 문제 풀이
 *  - dp 이용 -> 2차원 배열로 사용
 *	- 곡 번호, 볼륨을 저장
 *	- 해당 곡을 연주할 때 볼륨을 줄이거나 증가시켜서 가장 최댓값을 구함
 *  
 * 시간복잡도
 * O(N * M)
 *
 * 실행 시간
 * 108 ms
 * */

import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
       int N = Integer.parseInt(st.nextToken());
       int S = Integer.parseInt(st.nextToken());
       int M = Integer.parseInt(st.nextToken());
       
       int[] V = new int[N + 1];
       
       boolean[][] dp = new boolean[N + 1][M + 1];
       dp[0][S] = true;
       
       st = new StringTokenizer(br.readLine());
       
       for (int i = 1; i <= N; i++) {
    	   V[i] = Integer.parseInt(st.nextToken());
    	   
    	   for (int j = 0; j <= M; j++) {
    		   // (i - 1)번째 곡을 j볼륨으로 연주한 경우
    		   if (dp[i - 1][j]) {
    			   int increase = j + V[i]; // 증가
    			   int decrease = j - V[i]; // 감소
    			   
    			   // M이하인 경우 연주 가능한 것
    			   if (increase <= M) {
    				   dp[i][increase] = true;
    			   }
    			   
    			   // 0이상인 경우 연주 가능한 것
    			   if (decrease >= 0) {
    				   dp[i][decrease] = true;
    			   }
    		   }
    	   }
       }
       
       // 최댓값을 구하는 것이기 때문에 뒤에서부터 확인
       for (int i = M; i >= 0; i--) {
    	   if (dp[N][i]) {
    		   System.out.println(i);
    		   return;
    	   }
       }

       System.out.println(-1);
	}

}
