/**
 * 9370 미확인 도착지
 * https://www.acmicpc.net/problem/9370
 * 
 * @author minchae
 * @date 2024. 12. 23.
 *
 * 문제 풀이
 *  - 다익스트라 이용
 *  - 출발지 -> 도착지까지 가는데 특정 정점을 지나가는지 확인하면 됨
 *  - s -> g -> h -> x / s -> h -> g -> x / s -> x
 *  	-> 둘 중에서 최단 거리가 s -> x와 같으면 중간에 g, h를 지나가는 것
 *  - 근데 다익스트라를 너무 많이 실행해서 그런지 실행 시간이 너무 많이 나왔다..
 *
 * 시간 복잡도
 * O(T * t * (V + E)logV)
 *
 * 실행 시간
 * 3736 ms
 **/

import java.util.*;
import java.io.*;

public class Main {
	
	static class Node implements Comparable<Node> {
		int e;
		int cost;
		
		public Node(int e, int cost) {
			this.e = e;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	static int n, m, t;
	static int s, g, h;
	
	static ArrayList<Node>[] list;
	static int[] dist;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int T = Integer.parseInt(br.readLine());
        
        for (int test = 0; test < T; test++) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	
        	n = Integer.parseInt(st.nextToken()); // 교차로 개수
        	m = Integer.parseInt(st.nextToken()); // 도로 개수
        	t = Integer.parseInt(st.nextToken()); // 목적지 후보 개수
        	
        	list = new ArrayList[n + 1];
        	
        	for (int i = 1; i <= n; i++) {
        		list[i] = new ArrayList<>();
        	}
        	
        	st = new StringTokenizer(br.readLine());
        	
        	s = Integer.parseInt(st.nextToken()); // 출발지
        	g = Integer.parseInt(st.nextToken()); // g와 h 교차로 사이에 있는 도로를 지나갔다는 것
        	h = Integer.parseInt(st.nextToken());
        	
        	for (int i = 0; i < m; i++) {
        		st = new StringTokenizer(br.readLine());
        		
        		int a = Integer.parseInt(st.nextToken());
        		int b = Integer.parseInt(st.nextToken());
        		int d = Integer.parseInt(st.nextToken()); // 길이
        		
        		list[a].add(new Node(b, d));
        		list[b].add(new Node(a, d));
        	}
        	
        	PriorityQueue<Integer> answer = new PriorityQueue<>();
        	
        	for (int i = 0; i < t; i++) {
        		st = new StringTokenizer(br.readLine());
        		
        		int x = Integer.parseInt(st.nextToken());
        		
        		int route1 = dijkstra(s, g) + dijkstra(g, h) + dijkstra(h, x);
        		int route2 = dijkstra(s, h) + dijkstra(h, g) + dijkstra(g, x);
        		int route3 = dijkstra(s, x);
        		
        		if (Math.min(route1, route2) == route3) {
        			answer.add(x);
        		}
        	}
        	
        	StringBuilder sb = new StringBuilder();
        	
        	while (!answer.isEmpty()) {
        		sb.append(answer.poll()).append(" ");
        	}
        	
        	System.out.println(sb.toString());
        }
	}
	
	// start - end 최단 거리 반환
	private static int dijkstra(int start, int end) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		int[] dist = new int[n + 1];
		
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		pq.add(new Node(start, 0));
		dist[start] = 0;
		
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if (dist[cur.e] < cur.cost) {
				continue;
			}
			
			for (Node next : list[cur.e]) {
				if (dist[next.e] > dist[cur.e] + next.cost) {
					dist[next.e] = dist[cur.e] + next.cost;
					pq.add(new Node(next.e, dist[next.e]));
				}
			}
		}
		
		return dist[end];
	}

}
