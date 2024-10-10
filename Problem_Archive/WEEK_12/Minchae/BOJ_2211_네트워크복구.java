/**
 * 2211 네트워크 복구
 * https://www.acmicpc.net/problem/2211
 * 
 * @author minchae
 * @date 2024. 10. 10.
 * 
 * 문제 접근 아이디어 및 알고리즘 판단 사유
 * 	- 다익스트라
 *  - 더 짧은 거리를 발견할 경우 방문 체크 -> 방문 체크된 노드가 복구하는 네트워크
 * 		- 방문 체크 배열에 연결된 정점을 저장
 * 
 * 시간 복잡도
 * O(NlogN)
 * 
 * 실행 시간
 * 500 ms
 * */

import java.util.*;
import java.io.*;

public class BOJ_2211 {
	
	static class Node implements Comparable<Node> {
		int e;
		int w;
		
		public Node(int e, int w) {
			this.e = e;
			this.w = w;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.w, o.w);
		}
	}
	
	static final int INF = 987654321;
	
	static int N, M;
	
	static ArrayList<Node>[] list;
	static int[] dist;
	static int[] visited;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[N + 1];
		dist = new int[N + 1];
		visited = new int[N + 1];
		
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
			dist[i] = INF;
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			
			list[A].add(new Node(B, C));
			list[B].add(new Node(A, C));
		}
		
		solve();
		
		int answer = 0;
		StringBuilder sb = new StringBuilder();
		
		for (int i = 1; i <= N; i++) {
			if (visited[i] > 0) {
				answer++;
				sb.append("\n").append(i).append(" ").append(visited[i]);
			}
		}
		
		sb.insert(0, answer);
		
		System.out.println(sb.toString());
	}
	
	private static void solve() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		
		pq.add(new Node(1, 0));
		dist[1] = 0;
		
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			
			for (Node next : list[cur.e]) {
				if (dist[next.e] > dist[cur.e] + next.w) {
					dist[next.e] = dist[cur.e] + next.w;
					pq.add(new Node(next.e, dist[next.e]));
					
					visited[next.e] = cur.e;
				}
			}
		}
	}

}
