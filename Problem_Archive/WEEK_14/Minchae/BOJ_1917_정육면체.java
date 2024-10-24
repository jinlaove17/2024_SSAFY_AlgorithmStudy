/**
 * 1917 정육면체 전개도
 * https://www.acmicpc.net/problem/1917
 * 
 * @author minchae
 * @date 2024. 10. 24.
 * 
 * 문제 풀이
 * 	- 정육면체가 되는지 확인하기 위해 dice 배열 만듦
 *  - 맵에 1이 저장되어 있고 아직 방문하지 않았다면 탐색 시작
 *  	처음 탐색 시작하는 면을 아랫면이라 가정하고 시작
 *  	4방향 탐색하면서 주사위를 굴려봄 -> 돌릴 때마다 아랫면이 된다고 생각하고 dice[0]을 true로 바꿈
 *  	이때 계속 재귀 호출을 하는데 원복을 해줘야함 -> 다른 방향을 탐색해야 하기 때문
 * 
 * 시간 복잡도
 * 	맨 처음 for문 : O(3 * SIZE^2)
 *  dfs : O(SIZE^2)
 *  => O(SIZE^2)
 * 
 * 실행 시간
 * 108 ms
 * */

import java.util.*;
import java.io.*;

public class Main {
	
	static final int SIZE = 6;
	
	// 동남서북(우하좌상) -> 좌표 계산하기 쉽게 동남서북으로 설정
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};
	
	static int[][] map;
	static boolean[][] visited;
	static boolean[] dice; // 정육면체가 되는지 확인

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		map = new int[SIZE * 3][SIZE];
		visited = new boolean[SIZE * 3][SIZE];
		dice = new boolean[SIZE];
		
		for (int i = 0; i < SIZE * 3; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for (int j = 0; j < SIZE; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for (int t = 0; t < 3; t++) {
			for (int i = t * SIZE; i < (t + 1) * SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					// 아직 방문하지 않았고, 정사각형인 경우
					if (!visited[i][j] && map[i][j] == 1) {
						visited[i][j] = true;
						dice[0] = true;
						
						dfs(i, j, t);
					}
				}
			}
			
			System.out.println(check() ? "yes" : "no");
			
			Arrays.fill(dice, false);
		}
	}
	
	// dfs를 통해 정육면체가 만들어지는지 확인
	private static void dfs(int x, int y, int t) {
		// 4방향 탐색
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if (!isRange(nx, ny, t) || visited[nx][ny] || map[nx][ny] == 0) {
				continue;
			}
			
			visited[nx][ny] = true;
			
			roll(i);
			dfs(nx, ny, t);
			roll((i + 2) % 4); // 다시 원래 위치로 돌리기 : 다른 방향도 탐색해야 하기 때문
		}
	}
	
	// 주사위 아랫면이 정사각형임을 확인
	private static void roll(int d) {
		boolean[] temp = dice.clone();

        // 주사위 굴리기 : 아랫면 0, 뒷면 1, 오른쪽면 2, 왼쪽면 3, 앞면 4, 윗면 5
        if (d == 0) { // 동
        	dice[0] = temp[3];
            dice[2] = temp[0];
            dice[3] = temp[5];
            dice[5] = temp[2];
        } else if (d == 1) { // 남
        	dice[0] = temp[1];
			dice[1] = temp[5];
			dice[4] = temp[0];
			dice[5] = temp[4];
        } else if (d == 2) { // 서
        	dice[0] = temp[2];
            dice[2] = temp[5];
            dice[3] = temp[0];
            dice[5] = temp[3];
        } else { // 북
        	dice[0] = temp[4];
            dice[1] = temp[0];
            dice[4] = temp[5];
            dice[5] = temp[1];
        }
        
        dice[0] = true; // 해당 면을 방문했음을 확인
	}
	
	// 정육면체가 되는지 확인
	private static boolean check() {
		for (boolean value : dice) {
			if (!value) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean isRange(int x, int y, int t) {
		return x >= t * SIZE && x < (t + 1) * SIZE && y >= 0 && y < SIZE;
	}

}
