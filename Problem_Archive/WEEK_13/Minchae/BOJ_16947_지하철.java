/**
 * 16947 서울 지하철 2호선
 * https://www.acmicpc.net/problem/16947
 * 
 * @author minchae
 * @date 2024. 10. 15.
 * 
 * 문제 풀이
 * 	- 1. N개의 역이 순환선에 포함되어 있는지 확인 : dfs
 * 		- cycle 배열에 사이클 발견할 경우 true 저장
 * 		- 사이클 발견 시에 이전에 방문했던 노드를 체크하지 않는다면 다음 노드가 이전 노드가 되고 또 그게 다음 노드가 되고 반복됨
 * 		- 처음에 출발점만 확인했다가 답 이상하게 나옴 -> 조건을 추가해야 답이 올바르게 나옴
 * 	- 2. N개의 역 확인하면서 거리 계산 : bfs
 * 		- visited 안되어 있는 경우 거리 증가
 * 		- 사이클이 생겼던 노드일 경우 거리 반환
 * 
 * 시간 복잡도
 * N개의 노드 M개의 간선
 * dfs, bfs 둘 다 O(N + M)
 * 
 * 실행 시간
 * 720 ms
 * */

import java.io.*;
import java.util.*;

public class Main {
	
	static class Node {
		int num;
		int dist;
		
		public Node(int num, int dist) {
			this.num = num;
			this.dist = dist;
		}
	}
	
	static int N;
	static ArrayList<Integer>[] list;
	static boolean[] cycle;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		list = new ArrayList[N + 1];
		cycle = new boolean[N + 1];
		
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			list[a].add(b);
			list[b].add(a);
		}
		
		for (int i = 1; i <= N; i++) {
			// 사이클을 발견하면 종료
			if (checkCycle(i, i, i)) {
				break;
			}
		}
		
		int[] answer = new int[N + 1];
		
		for (int i = 1; i <= N; i++) {
			// 순환선이 아닐 때만 bfs로 거리 찾음
			if (!cycle[i]) {
				answer[i] = bfs(i);
			}
		}

		for (int i = 1; i <= N; i++) {
			System.out.print(answer[i] + " ");
		}
	}
	
	// 순환선이 되는지 확인
	private static boolean checkCycle(int cur, int start, int prev) {
		cycle[cur] = true;
		
		for (int next : list[cur]) {
			if (!cycle[next]) {
				// 사이클 체크하지 않은 경우 (순환선에 포함될 수 있음)
				if (checkCycle(next, start, cur)) {
					return true;
				}
			} else if (next == start && next != prev) {
				// 출발점으로 돌아온 경우 && 이전에 방문했던 노드가 아닌 경우
				return true;
			}
		}
		
		cycle[cur] = false; // 위의 조건들에 포함되지 않는 경우 현재 노드는 순환선이 될 수 없음
		
		return false;
	}
	
	// 순환선까지의 거리 찾기
	private static int bfs(int start) {
		Queue<Node> q = new LinkedList<>();
		boolean[] visited = new boolean[N + 1];
		
		q.add(new Node(start, 0));
		visited[start] = true;
		
		while (!q.isEmpty()) {
			Node cur = q.poll();
			
			// 순환선을 발견한 경우 거리 반환
			if (cycle[cur.num]) {
				return cur.dist;
			}
			
			for (int next : list[cur.num]) {
				if (!visited[next]) {
					q.add(new Node(next, cur.dist + 1));
					visited[next] = true;
				}
			}
		}
		
		return 0;
	}

}
