/**
 * 20165 인내의 도미노 장인 호석
 * https://www.acmicpc.net/problem/20165
 *
 * @author minchae
 * @date 2025. 2. 12.
 *
 * 문제 풀이
 * - 문제에 있는 그대로를 구현하면 되는 문제
 * - 도미노가 넘어진 경우를 구분하기 위해 boolean 2차원 배열 이용
 *
 * 시간 복잡도
 * O(N * M * R)
 *
 * 실행 시간
 * 344 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	// 상하좌우
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int N, M, R;
	static int[][] map;
	
	static int answer;
	static char[][] result;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		result = new char[N][M];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				result[i][j] = 'S';
			}
		}
		
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			
			int X = Integer.parseInt(st.nextToken()) - 1;
			int Y = Integer.parseInt(st.nextToken()) - 1;
			int D = getDir(st.nextToken().charAt(0));
			
			attack(X, Y, D); // 공격
			
			st = new StringTokenizer(br.readLine());
			
			X = Integer.parseInt(st.nextToken()) - 1;
			Y = Integer.parseInt(st.nextToken()) - 1;
			
			defense(X, Y); // 수비
		}
		
		System.out.println(answer);
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				System.out.print(result[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	private static int getDir(char dir) {
		if (dir == 'E') {
			return 3;
		} else if (dir == 'W') {
			return 2;
		} else if (dir == 'S') {
			return 1;
		} else {
			return 0;
		}
	}
	
	private static void attack(int x, int y, int d) {
		int num = map[x][y];
		
		while (num > 0) {
			// S일 때만 F로 바꾸고 더 가야하는 칸의 개수를 갱신시킴
			// 그래서 이미 넘어진 칸을 공격하는 경우에는 아무 일도 일어나지 않게 됨
			if (result[x][y] == 'S') {
				result[x][y] = 'F';
				answer++;
				
				// 둘 중에서 긴 도미노의 길이를 저장 (아직 더 가야할 칸의 개수)
				num = Math.max(num, map[x][y]);
			}
			
			num--; // 현재 칸을 F가 됐으니까 감소시킴
			
			x += dx[d];
			y += dy[d];
			
			// 범위를 벗어나면 종료
			if (!isRange(x, y)) {
				break;
			}
		}
	}
	
	private static void defense(int x, int y) {
		result[x][y] = 'S';
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}

}
