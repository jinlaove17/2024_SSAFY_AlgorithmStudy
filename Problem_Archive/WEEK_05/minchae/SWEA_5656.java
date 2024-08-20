/**
 * 5656. 벽돌 깨기
 * 
 * @author minchae
 * @date 2024. 8. 20.
 */

import java.util.*;
import java.io.*;

public class Solution {
	
	// 상하좌우
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int N, W, H;
	
	static int[][] map;
	static int[][] copy;
	
	static int[] order;
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken()); // 열
			H = Integer.parseInt(st.nextToken()); // 행
			
			map = new int[H][W];
			order = new int[N];
			
			answer = W * H;
			
			for (int i = 0; i < H; i++) {
				st = new StringTokenizer(br.readLine());
				
				for (int j = 0; j < W; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			select(0);
			
			System.out.println("#" + t + " " + answer);
		}

	}
	
	// 중복 순열 -> 구슬을 떨어뜨릴 위치 구함
	private static void select(int depth) {
		if (depth == N) {
			game();
			return;
		}
		
		for (int i = 0; i < W; i++) {
			order[depth] = i;
			select(depth + 1);
		}
	}
	
	// 게임 시작
	private static void game() {
		copy = new int[H][W];
		
		for (int i = 0; i < H; i++) {
			copy[i] = Arrays.copyOf(map[i], W);
		}
		
		// 구슬 순서대로 떨어뜨림
		for (int num : order) {
			for (int i = 0; i < H; i++) {
				// 해당 칸에 벽돌이 있는 경우
				if (copy[i][num] > 0) {
					// 구슬 떨어뜨리고 다음 구슬을 떨어뜨리기 위해 break
					remove(i, num);
					down();
					
					break;
				}
			}
		}
		
		answer = Math.min(answer, getBlockCnt()); // 최솟값 갱신
	}
	
	// 벽돌 제거
	private static void remove(int x, int y) {
		int value = copy[x][y];
		copy[x][y] = 0;
		
		for (int d = 0; d < 4; d++) {
			int nx = x;
			int ny = y;
			
			for (int cnt = 1; cnt < value; cnt++) {
				nx += dx[d];
				ny += dy[d];
				
				if (!isRange(nx, ny)) {
					break;
				}
				
				if (copy[nx][ny] > 1) {
					remove(nx, ny); // 1이상의 벽돌이 있는 경우 해당 벽돌에서 또 퍼짐
				} else {
					copy[nx][ny] = 0;
				}
			}
		}
	}
	
	// 위에 있는 벽돌을 내림
	private static void down() {
		for (int j = 0; j < W; j++) { // 각 열마다 확인
			Stack<Integer> stack = new Stack<>();
			
			for (int i = 0; i < H; i++) {
				if (copy[i][j] > 0) {
					stack.push(copy[i][j]);
					copy[i][j] = 0;
				}
			}
			
			int idx = H - 1;
			
			// 마지막 행부터 스택에 저장된 벽돌 채움
			while (!stack.isEmpty()) {
				copy[idx--][j] = stack.pop();
			}
		}
	}
	
	// 맵에 있는 블록 개수 구하기
	private static int getBlockCnt() {
		int cnt = 0;
		
		for (int i = 0; i < H; i++) {
			for (int j= 0; j < W; j++) {
				if (copy[i][j] > 0) {
					cnt++;
				}
			}
		}
		
		return cnt;
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < H && y >= 0 && y < W;
	}

}
