/**
 * 1981 배열에서 이동
 * https://www.acmicpc.net/problem/1981
 * 
 * @author minchae
 * @date 2024. 12. 18.
 *
 * 문제 풀이
 *  - BFS 이용
 *  - 최대, 최소 차이가 가장 작아지는 경우를 찾는 것을 어떻게 할지... 고민
 *    일단 배열 값 입력 받으면서 최대, 최소 찾음
 *    이분탐색으로 범위 차이를 최소로 만듦 -> 구한 범위로 bfs 탐색 가능한지 확인
 *    -> 해당 최소, 최대 값으로 도착점에 갈 수 있는지 확인하는 것
 *    
 *  - 이분 탐색 대상을 뭘로 할지가 또 고민..
 *    최댓값, 최솟값 차이를 이분 탐색 대상으로 하기 - 최대/최소 차이를 최소를 하는 것이기 때문
 *    -> low, high를 구하고 mid를 구함
 *       low : 최소 차이
 *       high : 최대 차이 (최댓값, 최솟값의 차이)
 *    	 high가 mid 이하인 경로로 갈 수 있는지 확인
 *  - [min, min + mid] 사이의 값으로 갈 수 있는지 확인하는 것 ()
 *
 * 시간 복잡도
 * 이분 탐색 : O(log(max−min))
 * bfs : O(n^2)
 * 총 시간 복잡도 : O(log(max−min)⋅n^2)
 * 
 *
 * 실행 시간
 * 480 ms
* */

import java.util.*;
import java.io.*;

public class Main {
	
	static class Pair {
		int x;
		int y;
		
		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int n;
	static int[][] map;
	
	static int min, max;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        n = Integer.parseInt(br.readLine());
        
        map = new int[n][n];
        
        for (int i = 0; i < n; i++) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	for (int j = 0; j < n; j++) {
        		map[i][j] = Integer.parseInt(st.nextToken());
        		
        		min = Math.min(min, map[i][j]);
        		max = Math.max(max, map[i][j]);
        	}
        }
        
        // 최댓값, 최솟값 차이를 이분 탐색 대상으로 해서 답 구하기
        int low = 0;
		int high = max - min;
		
		int answer = high;
		
		while (low <= high) {
			int mid = (low + high) / 2;
			
			if (check(mid)) {
				high = mid - 1;
				answer = mid; // 갱신
			} else {
				low = mid + 1; // 더 큰 차이를 탐색
			}
		}
		
		System.out.println(answer);
	}
	
	// [min, min + mid] 사이의 값으로 갈 수 있는지 확인하는 것
	private static boolean check(int mid) {
		// mid = 2인 경우
		// 범위는 [1, 3], [2, 4], [3, 5] 이런 식으로 여러 개가 가능하기 때문에 for문으로 확인
		for (int i = min; i + mid <= max; i++) {
			int start = i;
			int end = i + mid;
			
			if (bfs(start, end)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean bfs(int s, int e) {
		// 시작점이 범위를 벗어나는 경우
		if (map[0][0] < s || map[0][0] > e) {
			return false;
		}
		
		Queue<Pair> q = new LinkedList<>();
		boolean[][] visited = new boolean[n][n];
		
		q.add(new Pair(0, 0));
		visited[0][0] = true;
		
		while (!q.isEmpty()) {
			Pair cur = q.poll();
			
			// 도착점 도착
			if (cur.x == n - 1 && cur.y == n - 1) {
				return true;
			}
			
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				if (!isRange(nx, ny) || visited[nx][ny]) {
					continue;
				}
				
				// 현재 최소, 최대 범위를 벗어나면 해당 칸은 가지 못하는 것
				if (map[nx][ny] < s || map[nx][ny] > e) {
					continue;
				}
				
				q.add(new Pair(nx, ny));
				visited[nx][ny] = true;
			}
		}
		
		return false;
	}
	
	private static boolean isRange(int x, int y) {
		return x >= 0 && x < n && y >= 0 && y < n;
	}

}
