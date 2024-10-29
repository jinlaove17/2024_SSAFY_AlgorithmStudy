/**
 * 2887 행성터널
 * https://www.acmicpc.net/problem/2887
 * 
 * @author minchae
 * @date 2024. 10. 29.
 * 
 * 문제 풀이
 * 	- 최소 비용을 구하는 것 -> MST 라고 생각
 *  - 정점 최대 개수 : 10만 => 간선 최대 개수 : 10만(10만-1)/2
 *  	-> 약 500억 정도이기 때문에 간선 다 저장한다면 시간 초과 발생
 *  - 행성 간의 거리 : min(|xA-xB|, |yA-yB|, |zA-zB|
 *  	-> x, y, z 좌표 별로 각각 오름차순 정렬해서 저장하면 저장 개수 줄일 수 있음 -> 거리 작은 간선부터 탐색
 *  	-> (N - 1) * 3 -> 최대 30만
 * 
 * 시간 복잡도
 * 정렬 3번 : O(3 * NlogN)
 * 큐 삽입 : O(3 * N)
 * 크루스칼 : 간선 개수 M일 때 O(MlogM)
 * 
 * 실행 시간
 * 1668 ms
 * */

import java.io.*;
import java.util.*;

public class Main {
	
	static class Planet {
		int n;
		int x;
		int y;
		int z;
		
		public Planet(int n, int x, int y, int z) {
			this.n = n;
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	static class Edge implements Comparable<Edge> {
		int s;
		int e;
		int w;
		
		public Edge(int s, int e, int w) {
			this.s = s;
			this.e = e;
			this.w = w;
		}

		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.w, o.w);
		}
	}
	
	static int N;
	
	static Planet[] planets;
	static int[] parent;
	static PriorityQueue<Edge> pq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		planets = new Planet[N];
		pq = new PriorityQueue<>();
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			
			planets[i] = new Planet(i, x, y, z);
		}
		
		// x 좌표 정렬 (오름차순)
		Arrays.sort(planets, (o1, o2) -> Integer.compare(o1.x, o2.x));
		
		for (int i = 0; i < N - 1; i++) {
			int w = Math.abs(planets[i].x - planets[i + 1].x);
			pq.add(new Edge(planets[i].n, planets[i + 1].n, w));
		}
		
		// y 좌표 정렬
		Arrays.sort(planets, (o1, o2) -> Integer.compare(o1.y, o2.y));
		for (int i = 0; i < N - 1; i++) {
			int w = Math.abs(planets[i].y - planets[i + 1].y);
			pq.add(new Edge(planets[i].n, planets[i + 1].n, w));
		}
		
		// z 좌표 정렬
		Arrays.sort(planets, (o1, o2) -> Integer.compare(o1.z, o2.z));
		for (int i = 0; i < N - 1; i++) {
			int w = Math.abs(planets[i].z - planets[i + 1].z);
			pq.add(new Edge(planets[i].n, planets[i + 1].n, w));
		}
		
		make();
		
		int answer = 0;
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			
			// 두 행성이 연결될 때만 비용 더해줌
			if (union(cur.s, cur.e)) {
				answer += cur.w;
			}
		}

		System.out.println(answer);
	}
	
	// 초기화
	private static void make() {
		parent = new int[N];
		
		for (int i = 0; i < N; i++) {
			parent[i] = i;
		}
	}
	
	// 최상위 노드 찾음
	private static int find(int x) {
		if (parent[x] == x) {
			return x;
		}
		
		return parent[x] = find(parent[x]);
	}
	
	private static boolean union(int x, int y) {
		int rootX = find(x);
		int rootY = find(y);
		
		if (rootX == rootY) {
			return false;
		}
		
		if (rootX < rootY) {
			parent[rootY] = rootX;
		} else {
			parent[rootX] = rootY;
		}
		
		return true;
	}

}