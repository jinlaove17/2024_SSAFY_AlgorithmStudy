/**
 * 2306 유전자
 * https://www.acmicpc.net/problem/2306
 *
 * @author minchae
 * @date 2025. 4. 22.
 * 
 * 문제 풀이
 * - 유전자 1개의 양쪽이 KOI인지, 2개로 나눴을 때 KOI인지 확인 -> dp 사용해서 작은 것부터 확인하면 된다고 생각
 * 
 * - dp 2차원 배열 사용해서 특정 인덱스(시작, 끝 인덱스)까지 봤을 때 가장 긴 KOI 길이를 저장
 * 		1. 먼저 KOI인지 확인 후 맞다면 문자 개수 더함 -> 양끝 두개니까 2를 더한다.
 * 		2. 근데 여기서 양끝이 같지 않을 수 있고, 또 그걸 2개로 나누는 게 최대 길이가 될 수 있음 -> for문 사용
 * 
 * - KOI 유전자인지 확인해야 함 -> 양쪽 끝이 a/t, g/c로 끝나야 KOI
 * 
 * 시간 복잡도
 * O(n^3)
 * 
 * 실행 시간
 * 172 ms
 **/

import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        char[] word = br.readLine().toCharArray();
        int n = word.length;
        
        int[][] dp = new int[501][501];
        
        for (int cut = 1; cut < n; cut++) {
        	for (int i = 0; i + cut < n; i++) {
        		int end = i + cut;
        		
        		char s = word[i];
        		char e = word[end];
        		
        		// 1. 끝 문자가 KOI인지 확인
        		if ((s == 'a' && e == 't') || (s == 'g' && e == 'c')) {
        			dp[i][end] = dp[i + 1][end - 1] + 2; // 양끝 문자 개수 더하기
        		}
        		
        		// 2. 그냥 두는 것과 다시 두 개로 나눈 것을 비교
        		for (int j = i; j < end; j++) {
        			dp[i][end] = Math.max(dp[i][end], dp[i][j] + dp[j + 1][end]);
        		}
        	}
        }
        
        System.out.println(dp[0][n - 1]);
	}

}
