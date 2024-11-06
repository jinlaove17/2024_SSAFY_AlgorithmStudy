/**
 * 5214 환승
 * https://www.acmicpc.net/problem/5214
 *
 * @author minchae
 * @date 2024. 11. 5.
 *
 * 문제 풀이
 *  - 보자마자 bfs 사용해야 겠다고 생각
 *  - 역만 노드로 보는 것이 아니라 이를 잇는 하이퍼튜브도 노드로 보는 것이 중요!
 *  	-> 한 번 풀어봤던 문제라 이전 기억이 남아있어 생각하기 쉬웠음
 *  - bfs 탐색을 통해 1번을 출발지로 해서 N번까지 연결 가능한지 확인
 *  	-> visited 배열을 통해 N번을 방문했는지 확인하면 됨
 *  - 마지막에 거리를 출력할 때는 지금까지 구한 거리를 2로 나눠서 출력
 *  	-> 하이퍼튜브도 노드로 봐서 2로 나눠줘야 실제 역의 개수를 구할 수 있음
 *
 * 시간 복잡도
 * bfs 탐색 O(N + M * K) : 노드 수(N + M), 간선 수(M * K)
 *
 * 실행 시간
 * 812 ms
 * */

import java.util.*;
import java.io.*;

public class Main2 {
	
	static int N, K, M;
	static ArrayList<Integer>[] list;
	
	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        list = new ArrayList[N + M + 1];
        
        for (int i = 1; i <= N + M; i++) {
        	list[i] = new ArrayList<>();
        }
        
        for (int i = 1; i <= M; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	for (int j = 0; j < K; j++) {
        		int num = Integer.parseInt(st.nextToken()); // 역 번호
        		
        		list[num].add(N + i); // 역에 연결된 하이퍼튜브 추가
        		list[N + i].add(num); // 하이퍼튜브에도 역 추가
        	}
        }
        
        System.out.println(bfs());
	}
	
	private static int bfs() {
		Queue<Integer> q = new LinkedList<>();
		boolean[] visited = new boolean[N + M + 1];
		int[] dist = new int[N + M + 1];
		
		q.add(1);
		visited[1] = true;
		dist[1] = 1;
		
		while (!q.isEmpty()) {
			int cur = q.poll();
			
			for (int next : list[cur]) {
				if (visited[next]) {
					continue;
				}
				
				q.add(next);
				visited[next] = true;
				dist[next] = dist[cur] + 1; // 거리 증가
			}
		}
		
		// 실제 역의 개수를 구하기 위해 2로 나눠줌 -> 하이퍼튜브를 거쳐가기 때문에 해주는 것
		return visited[N] ? dist[N] / 2 + 1 : -1; // 출발점 포함하기 때문에 +1 해줌
	}

}
