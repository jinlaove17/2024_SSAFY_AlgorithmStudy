/**
 * 미생물 연구
 * 2025 상반기 오후 1번
 *
 * @author minchae
 * @date 2025. 6. 14. 10:10 ~ 13:15 (3시간 5분)
 * */

import java.io.*;
import java.util.*;

public class Main {
	
	static class Micro {
		int x1;
		int y1;
		int x2;
		int y2;
		
		public Micro(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}
	
	static class MicroGroup implements Comparable<MicroGroup> {
		int id;
		int size;
		
		public MicroGroup(int id, int size) {
			this.id = id;
			this.size = size;
		}

		@Override
		public int compareTo(MicroGroup o) {
			if (this.size == o.size) {
				return Integer.compare(this.id, o.id);
			}
			
			return Integer.compare(o.size, this.size);
		}
	}
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int N, Q;
	
	static int[][] map, moveMap; // 기존 배양 용기, 새로운 배양 용기
	static boolean[][] visited;
	
	static Micro[] microPos; // 미생물 무리 위치
	static int[] microSize; // 미생물 번호에 해당하는 무리의 개수
	static int[] microCnt; // 미생물 무리가 나누어지는지 확인

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		moveMap = new int[N][N];
		visited = new boolean[N][N];
		
		microPos = new Micro[Q + 1];
		microSize = new int[Q + 1];
		microCnt = new int[Q + 1];
		
		for (int i = 1; i <= Q; i++) {
			st = new StringTokenizer(br.readLine());
			
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			
			addMicro(i, x1, y1, x2, y2);
			move(i);
			
			System.out.println(calculate());
		}
	}
	
	// 미생물 영역 계산
	private static void dfs(int x, int y, int id) {
		visited[x][y] = true;
		
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			// 범위 벗어나거나, 방문했거나, 다른 미생물 무리인 경우 넘어감
			if (!isRange(nx, ny) || visited[nx][ny] || map[nx][ny] != id) {
				continue;
			}
			
			dfs(nx, ny, id);
		}
	}
	
	// 미생물 투입 -> 투입하는 미생물 번호 넣기
	private static void addMicro(int id, int x1, int y1, int x2, int y2) {
		// 미생물 투입
		for (int i = x1; i < x2; i++) {
			for (int j = y1; j < y2; j++) {
				map[i][j] = id;
			}
		}
		
		// 투입 후에 미생물 영역 계산
		for (int i = 0; i < N; i++) {
			Arrays.fill(visited[i], false); // 초기화
		}
		
		for (int i = 1; i <= id; i++) {
			microCnt[i] = 0;
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0 || visited[i][j]) {
					continue;
				}
				
				microCnt[map[i][j]]++; // 무리 개수 증가
				
				dfs(i, j, map[i][j]);
			}
		}
		
		// 무리가 두 개 이상으로 나눠지는 경우 제거
		for (int i = 1; i <= id; i++) {
			if (microCnt[i] >= 2) {
				removeMicro(i);
			}
		}
	}
	
	// 용기에서 미생물 무리 제거
	private static void removeMicro(int id) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == id) {
					map[i][j] = 0;
				}
			}
		}
	}

	// 배양 용기 이동 -> 미생물 무리 계산 후 정렬
	private static void move(int id) {
		for (int i = 0; i < N; i++) {
			Arrays.fill(moveMap[i], 0);
		}
		
		for (int i = 1; i <= id; i++) {
			microSize[i] = 0;
			microPos[i] = new Micro(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);
		}
		
		// 미생물 무리의 최소, 최대 좌표, 개수 구하기
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0) {
					continue;
				}
				
				microSize[map[i][j]]++;
				
				Micro micro = microPos[map[i][j]];
				micro.x1 = Math.min(micro.x1, i);
				micro.y1 = Math.min(micro.y1, j);
				micro.x2 = Math.max(micro.x2, i);
				micro.y2 = Math.max(micro.y2, j);
			}
		}
		
		ArrayList<MicroGroup> list = new ArrayList<>();
		
		for (int i = 1; i <= id; i++) {
			if (microSize[i] == 0) {
				continue;
			}
			
			list.add(new MicroGroup(i, microSize[i]));
		}
		
		Collections.sort(list);
		
		// 새로운 배양 용기에 배치
		for (MicroGroup group : list) {
			int curId = group.id;
			Micro cur = microPos[curId];
			
			int xSize = cur.x2 - cur.x1 + 1;
			int ySize = cur.y2 - cur.y1 + 1;
			
			// x좌표가 작고, y좌표가 작은 곳부터 배치
			outer1: for (int i = 0; i <= N - xSize; i++) {
				for (int j = 0; j <= N - ySize; j++) {
					// 배치 가능한지 확인
					boolean move = true;
					
					outer2: for (int dx = 0; dx < xSize; dx++) {
						for (int dy = 0; dy < ySize; dy++) {
							if (map[cur.x1 + dx][cur.y1 + dy] != curId) {
								continue;
							}
							
							// 새로운 용기에 이미 다른 미생물이 있는 경우
							if (moveMap[i + dx][j + dy] != 0) {
								move = false;
								break outer2;
							}
						}
					}
					
					// 새로운 용기에 배치
					if (move) {
						for (int dx = 0; dx < xSize; dx++) {
							for (int dy = 0; dy < ySize; dy++) {
								if (map[cur.x1 + dx][cur.y1 + dy] != curId) {
									continue;
								}
								
								moveMap[i + dx][j + dy] = curId;
							}
						}
						
						break outer1; // 배치한 경우 다음 미생물 무리를 배치할 수 있게 종료
					}
				}
			}
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = moveMap[i][j];
			}
		}
	}
	
	// 실험 결과 기록 (성과 계산)
	private static int calculate() {
		HashSet<Integer> hs[] = new HashSet[Q + 1];
		
		for (int i = 1; i <= Q; i++) {
			hs[i] = new HashSet<>();
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0) {
					continue;
				}
				
				for (int d = 0; d < 4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					
					if (!isRange(nx, ny) || map[nx][ny] == 0) {
						continue;
					}
					
					// 다른 미생물 영역이고 다음 위치의 번호가 큰 경우(쌍은 한 번만 확인하기 때문)
					if (map[nx][ny] != map[i][j] && map[i][j] < map[nx][ny]) {
						hs[map[i][j]].add(map[nx][ny]);
					}
				}
			}
		}
		
		int result = 0;
		
		for (int i = 1; i <= Q; i++) {
			HashSet<Integer> ids = hs[i];
			
			for (int id : ids) {
				result += microSize[i] * microSize[id];
			}
		}
		
		return result;
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < N;
	}
	
}
