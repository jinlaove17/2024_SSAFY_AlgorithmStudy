/**
 * 2307 도로검문
 * https://www.acmicpc.net/problem/2307
 * 
 * @author minchae
 * @date 2025. 1. 2.
 *
 * 문제 풀이
 *  - 다익스트라 이용 -> 최단 거리로 갈 수 있는 경로 저장
 *  - 최단 경로에서 간선들을 하나씩 막아보면서 최대 지연시간 구하기
 *  - 간선 삭제하다가 도착시 못빠져나가면 -1 출력하는거랑 지연효과 없는 걸 고려하지 않아서 틀렸다...
 *
 * 시간 복잡도
 * O(N * ElogV)
 *
 * 실행 시간
 * 820 ms
 **/

import java.util.*;
import java.io.*;

public class Main {
	
	static class Node implements Comparable<Node> {
		int e;
		int t;
		
		public Node(int e, int t) {
			this.e = e;
			this.t = t;
		}

		@Override
		public int compareTo(Main.Node o) {
			return Integer.compare(this.t, o.t);
		}
	}

	static int N, M;
	
	static ArrayList<Node>[] list;
	static int[] path;
	
	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        list = new ArrayList[N + 1];
        path = new int[N + 1];
        
        for (int i = 1; i <= N; i++) {
        	list[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < M; i++) {
        	st = new StringTokenizer(br.readLine());
        	
        	int a = Integer.parseInt(st.nextToken());
        	int b = Integer.parseInt(st.nextToken());
        	int t = Integer.parseInt(st.nextToken());
        	
        	list[a].add(new Node(b, t));
        	list[b].add(new Node(a, t));
        }

        int result = dijkstra(0, 0, 0); // 최단 경로 구함
        
        int answer = 0;
        
        // 최단 경로에서 간선 하나씩 삭제하면서 지연 시간 구함
        for (int i = 1; i <= N; i++) {
        	int time = dijkstra(1, path[i], i);
        	
        	// 도착시를 못빠져나가면 -1 출력
            if (time == Integer.MAX_VALUE) {
            	System.out.println(-1);
            	return;
            }
        	
        	int diff = time - result;
        	
        	answer = Math.max(answer, diff < 0 ? 0 : diff); // 최대 지연 시간 구함
        }
        
        System.out.println(answer);
	}
	
	// type : 경로 삭제되는지, start/end : 삭제되는 간선
	private static int dijkstra(int type, int start, int end) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		int[] dist = new int[N + 1];
		
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		pq.add(new Node(1, 0));
		dist[1] = 0;
		
		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if (dist[cur.e] < cur.t) {
				continue;
			}
			
			for (Node next : list[cur.e]) {
				// 경로가 삭제되는 경우 && 현재 간선이 삭제되는 간선일 경우 다음으로 넘어감
				if (type == 1 && (cur.e == start && next.e == end)) {
					continue;
				}
				
				if (dist[next.e] > dist[cur.e] + next.t) {
					dist[next.e] = dist[cur.e] + next.t;
					pq.add(new Node(next.e, dist[next.e]));
					
					// 최단 경로를 구할 때만 경로 저장
					if (type == 0) {
						path[next.e] = cur.e;
					}
				}
			}
		}
		
		return dist[N]; // 도착지까지의 최단 거리 반환
	}

}
