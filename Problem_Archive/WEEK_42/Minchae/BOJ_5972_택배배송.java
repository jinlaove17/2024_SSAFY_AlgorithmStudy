/**
 * 5972 택배 배송
 * https://www.acmicpc.net/problem/5972
 *
 * @author minchae
 * @date 2025. 5. 12.
 *
 * 문제 풀이
 * - 현서는 1에 있고, 찬홍이는 N에 있음 -> 최단 경로
 * - 길마다 가중치가 있으니까 다익스트라 쓰면 되나 생각 -> 1을 시작점으로 해서 dist 배열에 거리 저장하고 dist[N] 출력
 *
 * 시간 복잡도
 * O((N + M) log N)
 *
 * 실행 시간
 * 392 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static class Edge implements Comparable<Edge> {
		int e;
		int d;
		
		public Edge(int e, int d) {
			this.e = e;
			this.d = d;
		}
		
		@Override
		public int compareTo(Edge o) {
			return this.d - o.d;
		}
	}
	
	static int N, M;
	
	static ArrayList<Edge>[] list;
	static int[] dist;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        list = new ArrayList[N + 1];
        dist = new int[N + 1];
        
        for (int i = 1; i <= N; i++) {
        	list[i] = new ArrayList<>();
        	dist[i] = Integer.MAX_VALUE;
        }
        
        for (int i = 0; i < M; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	int A = Integer.parseInt(st.nextToken());
        	int B = Integer.parseInt(st.nextToken());
        	int C = Integer.parseInt(st.nextToken());
        	
        	list[A].add(new Edge(B, C));
        	list[B].add(new Edge(A, C));
        }

        find();
	}
	
	private static void find() {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		
		pq.add(new Edge(1, 0));
		dist[1] = 0;
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			
			for (Edge next: list[cur.e]) {
				if (dist[next.e] > dist[cur.e] + next.d) {
					dist[next.e] = dist[cur.e] + next.d;
					pq.add(new Edge(next.e, dist[next.e]));
				}
			}
		}
		
		System.out.println(dist[N]);
	}

}
