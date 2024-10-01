/**
 * 4014. 활주로 건설
 * 
 * @author Minchae
 * @date 2024. 10. 2.
 * 
 * 문제 접근 아이디어 및 알고리즘 판단 사유
 * 	- 각 행과 열을 확인하면서 경사로를 설치할 수 있는지 확인
 *  - 올라가는 경사로와 내려가는 경사로를 설치하는 경우를 나누어서 봄
 *  - 나머지는 문제에 나와있는대로 구현
 * 
 * 시간 복잡도
 * checkPath 호출 부분 : O(2*N)
 * checkPath : O(N)
 * 총 시간 복잡도 : 4번 반복하기 때문에 O(N^2)
 * 
 * 실행 시간
 * 128 ms
 */

import java.io.*;
import java.util.*;

public class Solution {
	
	static int N, X;
	static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine().trim());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			
			map = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			int answer = 0;
			
			for (int i = 0; i < N; i++) {
				// 행 확인
				if (checkPath(0, i, 0)) {
					answer++;
				}
				
				// 열 확인
				if (checkPath(1, 0, i)) {
					answer++;
				}
			}
			
			System.out.println("#" + t + " " + answer);
		}
	}

	// type이 0이면 행, 1이면 열
	private static boolean checkPath(int type, int x, int y) {
		int[] h = new int[N]; // 각 행 또는 열의 높이 저장
		
		for (int i = 0; i < N; i++) {
			h[i] = type == 0 ? map[x][i] : map[i][y];
		}
		
		boolean[] visited = new boolean[N]; // 경사로가 설치되었는지 확인
		
		for (int i = 0; i < N - 1; i++) {
			int diff = h[i] - h[i + 1];
			
			// 높이 차이가 1을 초과하는 경우 false 반환
			if (Math.abs(diff) > 1) {
				return false;
			}
			
			if (diff == 1) { // 내려가는 경우
				// h[i]가 h[i + 1]보다 더 커서 내려가는 경사로를 설치하는 것
				// X길이만큼 확인
				for (int j = i + 1; j <= i + X; j++) {
					// 범위 벗어남 || 경사로 설치되어 있음 || X만큼 연속으로 놓을 수 없는 경우
					if (j >= N || visited[j] || h[i + 1] != h[j]) {
						return false;
					}
					
					visited[j] = true;
				}
			} else if (diff == -1) { // 올라가는 경우
				// h[i + 1]이 h[i]보다 더 커서 올라가는 경사로를 설치하는 것
				// X길이만큼 확인
				for (int j = i; j > i - X; j--) {
					// 범위 벗어남 || 경사로 설치되어 있음 || X만큼 연속으로 놓을 수 없는 경우
					if (j < 0 || visited[j] || h[i] != h[j]) {
						return false;
					}
					
					visited[j] = true;
				}
			}
		}
		
		return true;
	}
	
}
