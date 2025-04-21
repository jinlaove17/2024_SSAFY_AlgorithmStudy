/**
 * 10423 전기가 부족해
 * https://www.acmicpc.net/problem/10423
 *
 * @author minchae
 * @date 2025. 4. 21.
 * 
 * 문제 풀이
 * - '6497 전력난' 문제를 풀고 풀어서 비슷한 문제라는 느낌이 왔다.
 * - 다른 점은 발전소 설치 지역은 경로에서 제외해도 된다는 것
 * - 그래서 parent 배열에 발전소 설치 지역은 -1을 저장했다.
 * - find에서는 그대로 -1을 반환하기
 * 
 * 시간 복잡도
 * 우선순위큐 O(NlogN)
 * 크루스칼 O(N * α(M))
 * 
 * 실행 시간
 * 444 ms
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
	
	static int N, M, K;
	static int total;
	
	static PriorityQueue<Node> pq;
	static int[] parent;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        pq = new PriorityQueue<>();
        parent = new int[N + 1];
        
        for (int i = 1; i <= N; i++) {
        	parent[i] = i;
        }
        
        // 발전소 설치 도시는 -1로 표시
        st = new StringTokenizer(br.readLine());
        
        for (int i = 0; i < K; i++) {
        	int num = Integer.parseInt(st.nextToken());
        	parent[num] = -1;
        }
        
        for (int i = 0; i < M; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	int u = Integer.parseInt(st.nextToken());
        	int v = Integer.parseInt(st.nextToken());
        	int w = Integer.parseInt(st.nextToken());
        	
        	pq.add(new Node(u, v, w));
        	
        	total += w;
        }

        int sum = 0;
        
        while (!pq.isEmpty()) {
        	Node cur = pq.poll();
        	
        	if (union(cur.s, cur.e)) {
        		sum += cur.d;
        	}
        }
        
        System.out.println(sum);
	}
	
	private static int find(int x) {
		// 발전소 설치 지역은 그대로 -1 반환
		if (parent[x] == -1) {
			return -1;
		}
		
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
