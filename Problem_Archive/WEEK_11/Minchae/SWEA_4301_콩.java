/**
 * 4301. 콩 많이 심기
 * 
 * @author minchae
 * @date 2024. 10. 3.
 * 
 * 문제 접근 아이디어 및 알고리즘 판단 사유
 * 	- 콩들 사이의 거리가 2가 되지 않아야 하기 때문에 dx, dy를 -2로 설정
 *  - 완전 탐색하면서 콩을 2인 위치에 콩이 없는 경우에만 콩 심기
 * 
 * 시간 복잡도
 * O(M*N)
 * 
 * 실행 시간
 * 181 ms
 */

import java.io.*;
import java.util.*;

public class Solution {
	
	// 상하좌우
	static int[] dx = {-2, 2, 0, 0};
	static int[] dy = {0, 0, -2, 2};
	
	static int N, M;
	static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine().trim());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			map = new int[M][N];
			
			int answer = 0;
			
			for (int i = 0; i < M; i++) {
				for (int j = 0; j < N; j++) {
					// 일단 콩을 심어봄
					map[i][j] = 1;
					answer++;
					
					for (int d = 0; d < 4; d++) {
						int nx = i + dx[d];
						int ny = j + dy[d];
						
						if (!isRange(nx, ny)) {
							continue;
						}
						
						// 이미 2인 거리에 콩이 심어져 있는 경우 원복 후 탐색 중지
						// 2인 위치에 콩이 없는 경우에만 심음
						if (map[nx][ny] == 1) {
							map[i][j] = 0;
							answer--;
							
							break;
						}
					}
				}
			}
			
			System.out.println("#" + t + " " + answer);
		}
	}

	private static boolean isRange(int x, int y) {
		return x >= 0 && x < M && y >= 0 && y < N;
	}
}
