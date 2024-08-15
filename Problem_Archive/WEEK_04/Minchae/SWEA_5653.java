/**
 * 5653. 줄기세포배양
 * 
 * @author minchae
 * @date 2024. 8. 13.
 */

import java.util.*;
import java.io.*;

public class SWEA_5653 {

	static class Node {
		int x;
		int y;
		int life; // 생명력 수치
		int time; // 활성화 되기까지의 시간 + 죽기까지의 시간
		int status; // 0 비활성화, 1 활성화, 2 죽음

		public Node(int x, int y, int life) {
			this.x = x;
			this.y = y;
			this.life = life;
		}
	}

	// 상하좌우
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	static int N, M, K;

	static int[][] map; // 생명력 수치 저장
	static boolean[][] isIn; // 줄기세포가 있는지 확인
	static Queue<Node> q; // 줄기세포 정보 저장

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			map = new int[N + K + 1][M + K + 1];
			isIn = new boolean[N + K + 1][M + K + 1];
			q = new LinkedList<>();

			for (int i = K / 2 + 1; i < N + K / 2 + 1; i++) {
				st = new StringTokenizer(br.readLine());

				for (int j = K / 2 + 1; j < M + K / 2 + 1; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());

					// 줄기 세포가 있는 경우
					if (map[i][j] > 0) {
						isIn[i][j] = true;
						q.add(new Node(i, j, map[i][j]));
					}
				}
			}
			
			culture(K); // K시간동안 줄기세포배양

			// K시간 후에 남아있는 줄기세포 수 출력
			System.out.println("#" + t + " " + q.size());
		}

	}

	// 줄기세포배양
	private static void culture(int time) {
		while (time-- > 0) {
			int size = q.size();

			// 세포가 동시에 번식하기 때문에 번식하기 전에 미리 배열에 어떤 값이 들어갈지 정해줌
			setLifeTime();

			// 현재 큐의 크기만큼 반복
			// 1시간동안 처음 활성화된 모든 줄기세포가 배양되기 때문에 for을 사용해 큐의 사이즈만큼 반복
			for (int i = 0; i < size; i++) {
				Node cur = q.poll();

				// 활성화 상태이고, 활성화 되고 처음인 경우에만 번식 가능
				if (cur.status == 1 && cur.time == cur.life) {
					// 상하좌우 4방향으로 번식
					for (int d = 0; d < 4; d++) {
						int nx = cur.x + dx[d];
						int ny = cur.y + dy[d];

						// 이미 줄기세포가 있는 곳인 경우 넘어감
						if (isIn[nx][ny]) {
							continue;
						}
						
						// 여기서 줄기세포 번식 여부를 체크하지 않는 이유
						// 줄기세포가 여러 곳에 있는데 동시에 번식하기 때문에 어떤 줄기세포가 해당 칸에 배양될지 모름
						// 그래서 번식할 때 확인하지 않고 미리 확인하고 값을 저장해둠

						// 큐에 넣고 줄기세포 표시 해줌
						q.add(new Node(nx, ny, map[nx][ny]));
						isIn[nx][ny] = true;
					}
				}

				// 시간 증가, 세포 상태 변경
				// 비활성화 상태 && 증가한 시간이 생명력 수치과 같은 경우 활성화 상태로 변경
				// 활성화 상태 && 활성화가 되고 life만큼 시간이 흐른 경우 죽은 상태로 변경
				if (cur.status == 0 && ++cur.time == cur.life) {
					cur.status = 1;
				} else if (cur.status == 1 && ++cur.time == cur.life * 2) {
					cur.status = 2;
				}

				// 이미 죽은 줄기세포인 경우 큐에 넣지 않고 넘어감
				if (cur.status == 2) {
					continue;
				}

				q.add(cur); // 활성화 되어있으면 큐에 계속 추가
			}
		}
	}

	// 줄기세포가 번식했을 때의 생명력 수치 저장
	private static void setLifeTime() {
		for (Node cur : q) {
			// 활성화 상태 && 처음 번식하는 경우
			if (cur.status == 1 && cur.time == cur.life) {
				for (int d = 0; d < 4; d++) {
					int nx = cur.x + dx[d];
					int ny = cur.y + dy[d];

					// 이미 줄기세포가 있는 경우 번식 불가
					if (isIn[nx][ny]) {
						continue;
					}

					// 번식하려는 곳의 생명력 수치보다 큰 경우 해당 줄기세포 생명력 수치 저장
					if (cur.life > map[nx][ny]) {
						map[nx][ny] = cur.life;
					}
				}
			}
		}
	}

//	private static void print() {
//		for (int i = 0; i < N + K + 2; i++) {
//			for (int j = 0; j < N + K + 2; j++) {
//				System.out.print(map[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println("-------------------");
//	}

}