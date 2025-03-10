/**
 * 지게차와 크레인
 * https://school.programmers.co.kr/learn/courses/30/lessons/388353
 *
 * @author minchae
 * @date 2025. 3. 11.
 *
 * 문제 풀이
 * - 단순하게 request가 1일 경우에만 배열 탐색하면서 4면 중 1면이 외부와 연결되어 있는지 확인
 *   -> 외부와 연결된 컨테이너만 제거
 *   -> 먼저 외곽에서 시작함 -> 다음 칸이 없앨 컨테이너라면 제거, 그런데 만약 이지 제거된 위치라면 그 다음 칸을 탐색
 * - 2일 경우에는 해당 알파벳을 가진 컨테이너 모두 제거
 *
 * 시간 복잡도
 * O(N^2 * M^2)
 *
 * 실행 시간
 * 28.53 ms
 */

import java.util.*;

public class ForkliftAndCrane {

	public static void main(String[] args) {
		String[] storage = {"AZWQY", "CAABX", "BBDDA", "ACACA"};
		String[] requests = {"A", "BB", "A"};

		System.out.println(solution(storage, requests));
	}
	
	static class Pair {
		int x;
		int y;
		
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	// 상하좌우
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int N, M;
	static char[][] map;
	static boolean[][] visited;
	
	public static int solution(String[] storage, String[] requests) {
		N = storage.length;
		M = storage[0].length();
		
		map = new char[N][M];
		
		for (int i = 0; i < N; i++) {
			map[i] = storage[i].toCharArray();
		}
		
		for (String request : requests) {
			char target = request.charAt(0);
			
			if (request.length() == 1) {
				forklift(target);
			} else {
				crane(target);
			}
		}
		
        int answer = 0;
        
        for (int i = 0; i < N; i++) {
        	for (int j = 0; j < M; j++) {
        		if (map[i][j] != '.') {
        			answer++;
        		}
        	}
        }
        
        return answer;
    }
	
	// 지게차 사용
	private static void forklift(char target) {
		visited = new boolean[N][M];
		
		// 외부와 연결되어 있는 컨테이너만 삭제 -> 지점 외곽에서 시작해야 함 (0, N - 1, M - 1)
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				// 외곽이고 아직 방문하지 않은 경우
				if (isOuter(i, j) && !visited[i][j]) {
					bfs(i, j, target);
				}
			}
		}
	}
	
	private static void bfs(int x, int y, char target) {
		Queue<Pair> q = new LinkedList<>();
		
		q.add(new Pair(x, y));
		visited[x][y] = true;
		
		while (!q.isEmpty()) {
			Pair cur = q.poll();
			
			// 찾고자 하는 컨테이너인 경우
			if (map[cur.x][cur.y] == target) {
				map[cur.x][cur.y] = '.';
				continue;
			}
			
			// 빈 공간이 아니고 다른 컨테이너가 있는 경우 다음으로 넘어감 (큐에 넣을 필요 없기 때문)
			if (map[cur.x][cur.y] != '.') {
				continue;
			}
			
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				if (!isRange(nx, ny) || visited[nx][ny]) {
					continue;
				}
				
				visited[nx][ny] = true;
				q.add(new Pair(nx, ny));
			}
		}
	}
	
	// 크레인 사용 -> 알파벳에 해당하는 모든 컨테이너 삭제
	private static void crane(char target) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				// 빈 공간으로 만듦
				if (map[i][j] == target) {
					map[i][j] = '.';
				}
			}
		}
	}
	
	private static boolean isOuter(int x, int y) {
		return x == 0 || x == N - 1 || y == 0 || y == M - 1;
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < N && y >= 0 && y < M;
	}

}
