/**
 * 18352 특정 거리의 도시 찾기
 * https://www.acmicpc.net/problem/18352
 *
 * @author minchae
 * @date 2025. 5. 12.
 *
 * 문제 풀이
 * - 시작점부터 최단 거리가 K인 모든 도시의 번호를 출력 -> 오름차순
 * - 우선순위큐 이용 (번호가 작은 걸 기준으로 정렬)
 * - 시작점부터 연결된 거 탐색하다가 거리가 K가 되면 도시번호를 정답 리스트에 추가
 * - 탐색 완료 후에 리스트가 비어이ㅆ으면 -1, 아니면 번호 출력
 *
 * 시간 복잡도
 * 탐색 O(N + M)
 * 정렬 O(NlogN)
 *
 * 실행 시간
 * 1088 ms
 **/

import java.io.*;
import java.util.*;

public class Main {
	
	static class Node {
		int num;
		int cnt;
		
		public Node(int num, int cnt) {
			this.num = num;
			this.cnt = cnt;
		}
	}
	
	static int N, M, K, X;
	static ArrayList<Integer>[] list;

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        
        list = new ArrayList[N + 1];
        
        for (int i = 1; i <= N; i++) {
        	list[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < M; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	int A = Integer.parseInt(st.nextToken());
        	int B = Integer.parseInt(st.nextToken());
        	
        	list[A].add(B);
        }
        
        ArrayList<Integer> result = bfs();

        if (result.isEmpty()) {
        	System.out.println(-1);
        } else {
        	Collections.sort(result);
        	
        	for (int num : result) {
        		System.out.println(num);
        	}
        }
	}
	
	private static ArrayList<Integer> bfs() {
		Queue<Node> q = new LinkedList<>();
	    boolean[] visited = new boolean[N + 1];

	    q.add(new Node(X, 0));
	    visited[X] = true;

	    ArrayList<Integer>result = new ArrayList<>();

	    while (!q.isEmpty()) {
	        Node cur = q.poll();

	        if (cur.cnt == K) {
	            result.add(cur.num);
	        }

	        for (int next : list[cur.num]) {
	            if (!visited[next]) {
	                visited[next] = true;
	                q.add(new Node(next, cur.cnt + 1));
	            }
	        }
	    }
	    
	    return result;
	}

}
