/**
 * 6497 전력난
 * https://www.acmicpc.net/problem/6497
 *
 * @author minchae
 * @date 2025. 4. 21.
 * 
 * 문제 풀이
 * - 최소 중에 최대 구하기 느낌
 * - 일단 가로등이 있어야 길을 건널 수 있는데 이게 경로가 가장 짧아야 함
 * - MST 구하면 됨 -> 크루스칼 사용
 * - 답은 총 경로의 합에서 MST 경로 합 빼주면 답 (불을 키지 않아서 절약하는 액수)
 * 
 * 시간 복잡도
 * 우선순위큐 O(nlogn)
 * 크루스칼 O(nα(m))
 * 
 * 실행 시간
 * 844 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static class Node implements Comparable<Node> {
		int s;
		int e;
		int d;
		
		public Node(int s, int e, int d) {
			this.s = s;
			this.e = e;
			this.d = d;
		}

		@Override
		public int compareTo(Main.Node o) {
			return this.d - o.d;
		}
	}
	
	static int m, n;
	static int total;
	
	static PriorityQueue<Node> pq;
	static int[] parent;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        while (true) {
        	st = new StringTokenizer(br.readLine());
        	
        	m = Integer.parseInt(st.nextToken());
        	n = Integer.parseInt(st.nextToken());
        	
        	if (m == 0 && n == 0) {
        		break;
        	}
        	
        	total = 0;
        	
        	pq = new PriorityQueue<>();
        	parent = new int[m];
        	
        	for (int i = 0; i < m; i++) {
        		parent[i] = i;
        	}
        	
        	for (int i = 0; i < n; i++) {
        		st = new StringTokenizer(br.readLine());
        		
        		int x = Integer.parseInt(st.nextToken());
        		int y = Integer.parseInt(st.nextToken());
        		int z = Integer.parseInt(st.nextToken());
        		
        		pq.add(new Node(x, y, z));
        		
        		total += z;
        	}
        	
        	int sum = 0;
        	
        	while (!pq.isEmpty()) {
        		Node cur = pq.poll();
        		
        		// 합쳐질 경우에만 더하기
        		if (union(cur.s, cur.e)) {
        			sum += cur.d;
        		}
        	}
        	
        	System.out.println(total - sum);
        }

	}
	
	private static int find(int x) {
		if (x == parent[x]) {
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
