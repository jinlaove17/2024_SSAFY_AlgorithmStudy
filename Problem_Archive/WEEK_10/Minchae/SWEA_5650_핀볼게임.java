/**
 * 5650. 핀볼 게임
 * 
 * @author Minchae
 * @date 2024. 9. 24.
 * 
 * 문제 보자마자 특별한 알고리즘이 필요 없는 구현 문제라고 생각
 * 방향 전환에만 신경 써주면 됨
 * 
 * 블록 만나면 수평, 수직에 따라 반대 방향, 경사면은 직각 방향으로 꺾임
 * -> 블록 타입과 현재 방향을 고려해 어떤 방향으로 전환되는지 2차원 배열로 미리 저장
 * 
 * 벽을 만나면 반대 방향으로 돌림
 * 
 * 웜홀 만나면 같은 번호를 가진 웜홀로 나옴
 * 
 * 블랙홀 만나면 종료, 점수는 벽이나 블록에 부딪힌 횟수 (웜홀 통과는 포함 X)
 */

import java.io.*;
import java.util.*;

public class Solution {

	static class Pair {
		int x;
		int y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	// 상좌하우
	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, -1, 0, 1 };
	
	// 블록 1 ~ 5 타입에 따라 상좌하우로 들어왔을 때 어떤 방향으로 전환되는지
	static int[][] blockDir = {
			{2, 0, 3, 1},
			{3, 2, 0, 1},
			{1, 3, 0, 2},
			{2, 3, 1, 0},
			{2, 3, 0, 1}
	};

	static int N;
	static int[][] map; // 웜홀 6 ~ 10, 블랙홀 -1, 블록 1 ~ 5, 빈 칸 0

	static Pair[][] wormHole; // 웜홀 인덱스, 같은 번호를 가지는 두 개의 웜홀 저장

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine().trim());

		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine().trim());

			map = new int[N][N];
			wormHole = new Pair[11][2]; // 웜홀이 6 ~ 10이기 때문에 크기를 11로 해줌

			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");

				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());

					// 웜홀 위치 저장
					if (map[i][j] >= 6) {
						if (wormHole[map[i][j]][0] == null) {
							wormHole[map[i][j]][0] = new Pair(i, j);
						} else {
							wormHole[map[i][j]][1] = new Pair(i, j);
						}
					}
				}
			}

			int answer = 0;

			// 모든 칸 탐색
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// 빈 칸이 아닌 경우 출발점이 될 수 없음
					if (map[i][j] != 0) {
						continue;
					}

					// 해당 칸에서 4방향 탐색
					for (int d = 0; d < 4; d++) {
						answer = Math.max(answer, move(i, j, d));
					}
				}
			}

			System.out.println("#" + t + " " + answer);
		}
	}

	// 시작 위치, 시작 방향
	private static int move(int x, int y, int dir) {
		int score = 0;

		int nx = x;
		int ny = y;

		while (true) {
			nx += dx[dir];
			ny += dy[dir];

			// 범위 벗어나는 경우 방향 바꿈
			if (!isRange(nx, ny)) {
				score++; // 점수 증가

				dir = (dir + 2) % 4; // 반대 방향으로 바꿈
				
				continue;
			}

			// 시작점으로 돌아오거나 블랙홀을 만나는 경우 종료
			if (isSame(x, y, nx, ny) || map[nx][ny] == -1) {
				return score;
			}

			// 웜홀 만나는 경우
			if (map[nx][ny] >= 6) {
				// 어느 웜홀인지 확인
				Pair w1 = wormHole[map[nx][ny]][0];
				Pair w2 = wormHole[map[nx][ny]][1];
				
				// 쌍을 이루는 다른 웜홀로 나옴
				if (isSame(nx, ny, w1.x, w1.y)) {
					nx = w2.x;
					ny = w2.y;
				} else {
					nx = w1.x;
					ny = w1.y;
				}
				
				continue;
			}

			// 블록 만나는 경우 -> 어떤 블록인지 확인
			if (map[nx][ny] >= 1 && map[nx][ny] <= 5) {
				score++; // 점수 증가
		
				// blockDir의 인덱스가 0부터 시작하기 때문에 -1 해줌
				dir = blockDir[map[nx][ny] - 1][dir]; // 만난 블록과 현재 방향에 따라 다음 방향 구함
				
				continue;
			}
			
			// 빈 칸인 경우 넘어감
			if (map[nx][ny] == 0) {
				continue;
			}
		}
	}
	
	// 좌표가 같은지 확인
	private static boolean isSame(int x1, int y1, int x2, int y2) {
		return x1 == x2 && y1 == y2;
	}

	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}
}
