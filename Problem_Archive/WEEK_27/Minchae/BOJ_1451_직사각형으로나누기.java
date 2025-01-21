/**
 * 1451 직사각형으로 나누기
 * https://www.acmicpc.net/problem/1451
 *
 * @author minchae
 * @date 2025. 1. 21.
 *
 * 문제 풀이
 * - 직사각형을 3개로 나누어 각각의 작은 직사각형의 합의 곱을 최대로 하는 것
 * - 일단 먼저 가장 큰 직사각형의 값을 배열에 저장
 * - 배열에 저장된 값을 누적합 (2차원 배열 누적합)
 * 
 * - 3개로 나누어서 곱이 최대가 되는 지 확인 => 경우의 수 생각해서 다 나눠봄
 *   - 모두 가로/세로로 나누는 경우 -> 2가지 경우
 *   - 왼쪽 1개, 오른쪽 2개 ㅑ / 왼쪽 2개, 오른쪽 1개 ㅕ
 *   - 위쪽 1개, 아래쪽 2개 ㅠ / 위쪽 2개, 아래쪽 1개 ㅛ
 *
 * 시간 복잡도
 * 가로 : O(N^2)
 * 세로 : O(M^2)
 * 나머지 : O(N * M)
 * N^2과 M^2 중에서 더 큰 값이 시간 복잡도가 됨
 *
 * 실행 시간
 * 108 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static int N, M;
	static int[][] square;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        square = new int[N + 1][M + 1];
        
        // 입력 받으면서 2차원 배열 누적합 구하기
        for (int i = 1; i <= N; i++) {
        	String input = br.readLine();
        	
        	for (int j = 1; j <= M; j++) {
        		square[i][j] = input.charAt(j - 1) - '0' + 
        				square[i][j - 1] + square[i - 1][j] - square[i - 1][j - 1];
        	}
        }
        
        long answer = 0;
        
        // 가로로 나누기
        for (int i = 1; i <= N - 2; i++) {
        	for (int j = i + 1; j <= N - 1; j++) {
        		long sum1 = getSum(1, 1, i, M);
        		long sum2 = getSum(i + 1, 1, j, M);
        		long sum3 = getSum(j + 1, 1, N, M);
        		
        		answer = Math.max(answer, sum1 * sum2 * sum3);
        	}
        }
        
        // 세로로 나누기
        for (int i = 1; i <= M - 2; i++) {
        	for (int j = i + 1; j <= M - 1; j++) {
        		long sum1 = getSum(1, 1, N, i);
        		long sum2 = getSum(1, i + 1, N, j);
        		long sum3 = getSum(1, j + 1, N, M);
        		
        		answer = Math.max(answer, (long) sum1 * sum2 * sum3);
        	}
        }
        
        // 나머지 4가지 경우
        for (int i = 1; i <= N - 1; i++) {
        	for (int j = 1; j <= M - 1; j++) {
        		// 왼쪽 1개, 오른쪽 2개 ㅑ
        		long sum1 = getSum(1, 1, N, j);
        		long sum2 = getSum(1, j + 1, i, M);
        		long sum3 = getSum(i + 1, j + 1, N, M);
        		
        		answer = Math.max(answer, sum1 * sum2 * sum3);
        		
        		// 왼쪽 2개, 오른쪽 1개 ㅕ
        		sum1 = getSum(1, 1, i, j);
        		sum2 = getSum(i + 1, 1, N, j);
        		sum3 = getSum(1, j + 1, N, M);
        		
        		answer = Math.max(answer, sum1 * sum2 * sum3);
        		
        		// 위쪽 1개, 아래쪽 2개 ㅠ
        		sum1 = getSum(1, 1, i, M);
        		sum2 = getSum(i + 1, 1, N, j);
        		sum3 = getSum(i + 1, j + 1, N, M);
        		
        		answer = Math.max(answer, sum1 * sum2 * sum3);
        		
        		// 위쪽 2개, 아래쪽 1개 ㅛ
        		sum1 = getSum(1, 1, i, j);
        		sum2 = getSum(1, j + 1, i, M);
        		sum3 = getSum(i + 1, 1, N, M);
        		
        		answer = Math.max(answer, sum1 * sum2 * sum3);
        	}
        }
        
        System.out.println(answer);
	}
	
	// 특정 범위의 누적합 구하기
	private static int getSum(int x1, int y1, int x2, int y2) {
		return square[x2][y2] - (square[x1 - 1][y2] + square[x2][y1 - 1] - square[x1 - 1][y1 - 1]);
	}

}
