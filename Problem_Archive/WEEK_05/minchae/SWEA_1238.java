/**
 * D4 1238. Contact
 * 20,248 kb  118 ms
 * 
 * @author minchae
 * @date 2024. 8. 16.
 */

import java.util.*;
import java.io.*;

public class Solution {
	
	static ArrayList<Integer>[] list;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (int t = 1; t <= 10; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int N = Integer.parseInt(st.nextToken());
			int start = Integer.parseInt(st.nextToken());
			
			list = new ArrayList[101];
			
			for (int i = 1; i <= 100; i++) {
				list[i] = new ArrayList<>();
			}
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < N / 2; i++) {
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				
				list[from].add(to);
			}
			
			int answer = bfs(start);
			
			System.out.println("#" + t + " " + answer);
		}
	}
	
	private static int bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		int[] visited = new int[101]; // 언제 방문했는지 저장
		
		q.add(start);
		visited[start] = 1;
		
		int maxLevel = 0; // 최대 레벨 저장
		
		while (!q.isEmpty()) {
			int cur = q.poll();
			
			for (int next : list[cur]) {
				// 이미 방문한 노드인 경우 넘어감
				if (visited[next] > 0) {
					continue;
				}
				
				q.add(next);
				visited[next] = visited[cur] + 1;
				
				maxLevel = Math.max(maxLevel, visited[next]);
			}
		}
		
		// 마지막 레벨에서의 최댓값이 정답 (뒤에 있는 노드부터 반복하면 숫자가 가장 큰 노드를 구할 수 있음)
		for (int i = 100; i >= 1; i--) {
			if (visited[i] == maxLevel) {
				return i;
			}
		}
		
		return 0;
	}

}
