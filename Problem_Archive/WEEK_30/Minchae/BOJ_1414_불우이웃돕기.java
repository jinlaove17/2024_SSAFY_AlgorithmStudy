/**
 * 1414 불우이웃돕기
 * https://www.acmicpc.net/problem/1414
 *
 * @author minchae
 * @date 2025. 2. 12.
 *
 * 문제 풀이
 * - 랜선을 최소로 해서 모든 컴퓨터를 방문할 수 있는지 확인
 * - 최소스패닝트리 -> 프림 또는 크루스칼을 이용하면 된다.
 * - 입력 받으면서 간선 가중치를 다 저장해놓음
 * - 최소스패닝트리가 되는 비용을 구함 -> 전체 가중치에서 랜선 비용을 빼주면 답이 나옴
 * 
 * - 간선 개수가 많으면 프림, 적으면 크루스칼 -> 주어지는 입력이 i와 j만 연결되어 있으므로 간선이 적다 -> 크루스칼 이용
 *
 * 시간 복잡도
 * O(N^2 * log N)
 *
 * 실행 시간
 * 116 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static class Node implements Comparable<Node> {
		int x;
		int y;
		int cost;
		
		public Node(int x, int y, int cost) {
			this.x = x;
			this.y = y;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	static int[] parent;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		char[][] map = new char[N][N];
		parent = new int[N];
		
		for (int i = 0; i < N; i++) {
			map[i] = br.readLine().toCharArray();
			parent[i] = i;
		}
		
		int total = 0;
		PriorityQueue<Node> pq = new PriorityQueue<>();
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int num = getNum(map[i][j]);
				
				// 랜선이 있는 경우에만 저장
				if (num > 0) {
				    pq.add(new Node(i, j, num));
				    total += num;
				}
			}
		}
		
		int cnt = 1;
		int cost = 0;
		
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if (find(cur.x) != find(cur.y)) {
				union(cur.x, cur.y);
				
				cnt++;
				cost += cur.cost;
			}
		}
		
		System.out.println(cnt != N ? -1 : (total - cost));
	}
	
	private static int getNum(char c) {
		if (c == '0') {
			return 0;
		}
		
		if (Character.isLowerCase(c)) {
			return c - 'a' + 1;
		}
		
		return c - 'A' + 27;
	}
	
	private static int find(int x) {
		if (x == parent[x]) {
			return x;
		}
		
		return parent[x] = find(parent[x]);
	}
	
	private static void union(int x, int y) {
		x = find(x);
		y = find(y);
		
		// 더 작은 쪽을 부모로 설정
		if (x < y) {
			parent[y] = x;
		} else {
			parent[x] = y;
		}
	}

}
