/**
 * 1595 북쪽나라의 도로
 * https://www.acmicpc.net/problem/1595
 * 
 * @author minchae
 * @date 2024. 12. 30.
 *
 * 문제 풀이
 *  - 거리가 가장 먼 두 도시 사이의 거리를 출력하는 것 -> 트리의 지름을 구하는 것과 같음
 *  - dfs나 bfs 탐색을 두 번 해주면 지름을 구할 수 있음
 *  - 입력이 전혀 없거나 빈 값이 들어오는 경우 0을 출력해야 함
 *
 * 시간 복잡도
 * O(N + E) -> 트리 특성이 (E = N - 1)이기 때문에 O(N)이라고 할 수 있음
 *
 * 실행 시간
 * 152 ms
 **/

import java.util.*;
import java.io.*;

public class Main {
	
	static class Node {
		int e;
		int dist;
		
		public Node(int e, int dist) {
			this.e = e;
			this.dist = dist;
		}
	}
	
	static ArrayList<Node>[] list;
	static boolean[] visited;
	
	static int max;
	static int maxNode;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        list = new ArrayList[10001];
        visited = new boolean[10001];
        
        for (int i = 1; i <= 10000; i++) {
        	list[i] = new ArrayList<>();
        }
        
        String input;
        boolean flag = false;
        
        while ((input = br.readLine()) != null) {
        	if (input.isEmpty()) {
        		break;
        	}
        	
        	flag = true;
        	
        	StringTokenizer st = new StringTokenizer(input);
        	
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	int c = Integer.parseInt(st.nextToken());
        	
        	list[a].add(new Node(b, c));
        	list[b].add(new Node(a, c));
        }
        
        // 입력이 전혀 없는 경우에도 0을 출력해야 해서 여기서 return
        if (!flag) {
        	System.out.println(0);
        	return;
        }

        dfs(1, 0);
        
        Arrays.fill(visited, false);
        
        dfs(maxNode, 0);
        
        System.out.println(max);
	}
	
	private static void dfs(int start, int dist) {
		visited[start] = true;
		
		// 최장 길이 찾음
		if (dist > max) {
			max = dist;
			maxNode = start;
		}
		
		for (Node next : list[start]) {
			if (!visited[next.e]) {
				dfs(next.e, dist + next.dist);
			}
		}
	}

}
