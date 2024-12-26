/**
 * 모두 0으로 만들기
 * https://school.programmers.co.kr/learn/courses/30/lessons/76503
 * 
 * @author minchae
 * @date 2024. 12. 26.
 *
 * 문제 풀이
 *  - 최종 순위를 먼저 풀고 이 문제를 봐서 그런지 뭔가 모르게 위상정렬 느낌으로 풀면 되지 않을까 하는 생각이 들었다.
 *  - 0으로 만들기 위해 진입 차수가 1인 정점을 큐에 삽입함
 *  - 무방향 그래프라서 방문처리를 위해 visited 배열 사용
 *  - 가중치가 계속 더해지기 때문에 long 배열을 따로 만들어서 사용
 *  - 마지막에 놓쳤던 부분 -> 맨 처음 가중치의 합이 0이 되지 않으면 바로 -1을 반환해야 함
 *
 * 시간 복잡도
 * O(n + m)
 *
 * 실행 시간
 * 255.13 ms
 **/

import java.util.*;

public class Make0 {
	
	static int n;
	
	static long[] copy;
	static ArrayList<Integer>[] list;
	static int[] indegree;
	
	static long answer;

	public static void main(String[] args) {
		int[] a = { -5, 0, 2, 1, 2 };
		int[][] edges = { { 0, 1 }, { 3, 4 }, { 2, 3 }, { 0, 3 } };

		System.out.println(solution(a, edges));
	}
	
	public static long solution(int[] a, int[][] edges) {
		n = a.length;
		
		copy = new long[n];
		list = new ArrayList[n];
		indegree = new int[n];
		
		for (int i = 0; i < n; i++) {
			list[i] = new ArrayList<>();
			
			copy[i] = a[i];
			answer += a[i];
		}
		
		if (answer != 0) {
			return -1;
		}
		
		for (int i = 0; i < edges.length; i++) {
			list[edges[i][0]].add(edges[i][1]);
			list[edges[i][1]].add(edges[i][0]);
			
			indegree[edges[i][0]]++;
			indegree[edges[i][1]]++;
		}
		
		find();
        
        return answer;
    }
	
	private static void find() {
		Queue<Integer> q = new LinkedList<>();
		boolean[] visited = new boolean[n];
		
		for (int i = 0; i < n; i++) {
			// 진입 차수가 1인 것만 큐에 삽입
			if (indegree[i] == 1) {
				q.add(i);
			}
		}
		
		while (!q.isEmpty()) {
			int cur = q.poll();
			visited[cur] = true;
			
			for (int next : list[cur]) {
				if (visited[next]) {
					continue;
				}
				
				answer += Math.abs(copy[cur]);
				
				copy[next] += copy[cur]; // 가중치 옮김
				copy[cur] = 0;
				
				indegree[next]--; // 진입 차수 감소
				
				if (indegree[next] == 1) {
					q.add(next);
				}
			}
		}
	}

}
