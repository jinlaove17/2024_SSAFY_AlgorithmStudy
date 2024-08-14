/**
 * 1248. 공통 조상
 * 
 * @author minchae
 * @date 2024. 8. 14.
 */

import java.util.*;
import java.io.*;

public class SWEA_1248 {

	static class Node {
		int num;
		int parent;
		int left;
		int right;
		
		public Node(int num) {
			this.num = num;
		}
	}
	
	static int V, E, v1, v2;
	
	static Node[] node;
	static boolean[] visited;
	
	static int common; // 공통 조상 노드 번호
	static int answer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			v1 = Integer.parseInt(st.nextToken());
			v2 = Integer.parseInt(st.nextToken());
			
			node = new Node[V + 1];
			visited = new boolean[V + 1];
			
			common = 0;
			answer = 0;
			
			for (int i = 1; i <= V; i++) {
				node[i] = new Node(i);
			}
			
			st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < E; i++) {
				int p = Integer.parseInt(st.nextToken()); // 부모
				int c = Integer.parseInt(st.nextToken()); // 자식
				
				node[c].parent = p; // 자식 노드에 부모 추가
				
				// 부모 노드에 자식 추가
				if (node[p].left == 0) {
					node[p].left = c;
				} else if (node[p].right == 0) {
					node[p].right = c;
				}
			}
			
			// 두 개의 노드의 공통 부모를 찾음
			findParent(v1);
			findParent(v2);
			
			// 찾은 부모 노드를 루트로 해서 자식 노드 개수 구함
			calculate(common);
			
			System.out.println("#" + t + " " + common + " " + answer);
		}
	}
	
	// 특정 노드의 부모 노드 찾기
	private static void findParent(int v) {
		int parent = node[v].parent; // 노드의 부모 구함
		
		// 루트 노드이거나 공통 조상이 이미 정해진 경우
		if (parent == 0 || common != 0) {
			return;
		}
		
		// 이미 방문한 경우 공통 조상을 발견한 것
		if (visited[parent]) {
			common = parent;
		}
		
		visited[parent] = true;
		
		findParent(parent); // 부모 노드로 거슬러 올라감
	}
	
	// 특정 노드의 자식 노드 개수 구하기
	private static void calculate(int v) {
		answer++; // 자식 노드 개수 증가
		
		int left = node[v].left;
		int right = node[v].right;
		
		if (left != 0 && right != 0) { // 왼쪽, 오른쪽 둘 다 자식이 있는 경우
			calculate(left);
			calculate(right);
		} else if (left != 0) { // 왼쪽에만 자식 노드가 있는 경우
			calculate(left);
		} else if (right != 0) { // 오른쪽에만 자식 노드가 있는 경우
			calculate(right);
		}
	}

}
