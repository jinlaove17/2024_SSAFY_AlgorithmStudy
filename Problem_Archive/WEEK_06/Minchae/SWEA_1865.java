/**
 * D4 1865. 동철이의 일 분배
 * 23,200 kb  2,089 ms
 * 
 * @author minchae
 * @date 2024. 8. 27.
 * */

import java.util.*;
import java.io.*;

public class Solution {

	static int N;
	static int[][] map;
	
	static boolean[] visited;
	
	static double answer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			
			map = new int[N][N];
			
			visited = new boolean[N];
			
			answer = 0;
			
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			perm(0, 100);
			
			System.out.println("#" + t + " " + String.format("%.6f", answer));
		}
	}
	
	// 맡길 일을 선택 -> 순열 사용
	private static void perm(int depth, double cur) {
		if (cur <= answer) {
			return;
		}
		
		if (depth == N) {
			answer = Math.max(answer, cur);
			return;
		}
		
		for (int i = 0; i < N; i++) {
			if (!visited[i]) {
				visited[i] = true;
				perm(depth + 1, cur * (map[depth][i] / 100.0));
				visited[i] = false;
			}
		}
	}

}
