/**
 * 14391 종이 조각
 * https://www.acmicpc.net/problem/14391
 *
 * @author minchae
 * @date 2025. 6. 8.
 *
 * 문제 풀이
 * - 일단 종이를 가로로 자를지, 세로로 자를지 결정 -> dfs
 * - 자른 종이의 합 구하기
 *
 * 푼 시간
 * 40분
 *
 * 실행 시간
 * 148 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static int N, M;
	static int[][] map;
	
	static boolean[][] slice; // 가로 true, 세로 false
	static int answer;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        map = new int[N][M];
        slice = new boolean[N][M];
        
        for (int i = 0; i < N; i++) {
        	String input = br.readLine();
        	
        	for (int j = 0; j < M; j++) {
        		map[i][j] = input.charAt(j) - '0';
        	}
        }
        
        dfs(0, 0);

        System.out.println(answer);
	}
	
	private static void dfs(int x, int y) {
		if (x == N) {
			answer = Math.max(answer, calculate());
			return;
		}
		
		// 다음 행으로 이동
		if (y == M) {
			dfs(x + 1, 0);
			return;
		}
		
		slice[x][y] = true; // 가로 표시
		dfs(x, y + 1);
		
		slice[x][y] = false; // 세로 표시
		dfs(x, y + 1);
	}
	
	// 가로, 세로로 나눈 종이의 합 구하기
	private static int calculate() {
		int result = 0;
		
		for (int i = 0; i < N; i++) {
			int sum = 0;
			
			for (int j = 0; j < M; j++) {
				if (slice[i][j]) {
					sum = sum * 10 + map[i][j];
				} else {
					// 가로로 자른 경우가 아니면 바로 더함
					result += sum;
					sum = 0;
				}
			}
			
			result += sum;
		}
		
		for (int j = 0; j < M; j++) {
			int sum = 0;
			
			for (int i = 0; i < N; i++) {
				if (!slice[i][j]) {
					sum = sum * 10 + map[i][j];
				} else {
					// 세로로 자른 경우가 아니면 바로 더함
					result += sum;
					sum = 0;
				}
			}
			
			result += sum;
		}
		
		return result;
	}

}
