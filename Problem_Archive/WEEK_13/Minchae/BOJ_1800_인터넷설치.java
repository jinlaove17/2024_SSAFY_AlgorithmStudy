/**
 * 1800 인터넷 설치
 * https://www.acmicpc.net/problem/1800
 * 
 * @author minchae
 * @date 2024. 10. 16.
 * 
 * 문제 풀이
 * 	- 다익스트라 사용
 * 		- 원래는 dist 배열에 가중치를 더해서 최단 경로를 구하지만
 * 			여기에서는 cnt로 하고 특정 금액을 넘어가는 인터넷 선이 몇 개인지 저장
 * 		- cnt[N] <= K인 경우 해당 금액으로 N번이 인터넷 연결 가능한 것
 * 		- K개까지는 공짜로 연결 가능하기 때문에 특정 비용을 넘어가는 인터넷 선이 K개 이하인 경우를 봄
 * 
 * 	- 실수했던 부분
 * 	  반복문을 인터넷 최대 연결 비용까지 반복하면서 최소 비용을 찾음
 * 		-> 최악의 경우 max까지 도는 경우가 있음
 * 
 *  - 분류 보고 이분 탐색 써야 하는 걸 확인
 *    -> mid 값을 상한선으로 봄
 *    	 바꿨는데 96퍼에서 틀림 (answer 초기값을 -1로 해야 함 : N까지 연결하지 못한 경우 -1 출력해야 하기 때문)
 *    
 * 
 * 시간 복잡도
 * 이분 탐색 O(log(end))
 * 다익스트라 O((N + P)logV) : (노드 + 간선 개수), 우선순위큐 연산
 * => 이진 탐색할 때마다 다익스트라 호출 : O(log(end) * (N + P)logV)
 * 
 * 실행 시간
 * 268 ms
 * */

import java.io.*;
import java.util.*;

public class Main {
	
	static class Edge implements Comparable<Edge> {
		int e;
		int cost;
		
		public Edge(int e, int cost) {
			this.e = e;
			this.cost = cost;
		}

		@Override
		public int compareTo(Main.Edge o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	static int N, P, K;
	
	static ArrayList<Edge>[] list;
	static int[] cnt; // 정해진 비용(상한선)보다 넘어가는 인터넷 선 개수

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[N + 1];
		cnt = new int[N + 1];
		
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}
		
		int end = 0;
		
		for (int i = 0; i < P; i++) {
			st = new StringTokenizer(br.readLine());
			
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			list[a].add(new Edge(b, cost));
			list[b].add(new Edge(a, cost));
			
			end = Math.max(end, cost);
		}
		
		int answer = -1;
		int start = 0;
		
		while (start <= end) {
			int mid = (start + end) / 2;
			
			if (dijkstra(mid)) {
				answer = mid;
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}
		
		// 원래 코드
//		for (int i = 0; i <= end; i++) {
//			// 상한선 금액으로 N까지 연결 가능한 경우
//			if (dijkstra(i)) {
//				answer = i;
//				break; // 최소 금액을 구하는 것이기 때문에 금액을 발견한 경우 바로 break
//			}
//		}

		System.out.println(answer);
	}
	
	// 상한선을 넘어가는 인터넷 선 개수가 K개 이하인지 확인
	private static boolean dijkstra(int limit) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		Arrays.fill(cnt, N + 1);
		
		pq.add(new Edge(1, 0)); // cost에 상한선 초과한 인터넷 선 개수를 저장
		cnt[1] = 0;
		
		while (!pq.isEmpty()) {
			Edge cur = pq.poll();
			
			// 이미 저장된 개수보다 더 많은 경우 다음으로 넘어감
			if (cur.cost > cnt[cur.e]) {
				continue;
			}
			
			for (Edge next : list[cur.e]) {
				int newCnt = cur.cost + (next.cost > limit ? 1 : 0); // 상한선을 넘는 경우 개수 1 더해줌
				
				// newCnt가 더 작은 경우 값 저장하고 큐에 값 삽입
				if (cnt[next.e] > newCnt) {
					cnt[next.e] = newCnt;
					pq.add(new Edge(next.e, newCnt));
				}
			}
		}
		
		return cnt[N] <= K;
	}

}
